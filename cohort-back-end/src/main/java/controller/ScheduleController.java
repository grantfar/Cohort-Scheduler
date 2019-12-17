package controller;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;
import CohortDataClasses.ClassRequirement;
import CohortDataClasses.Cohort;
import CohortDataClasses.CohortSectionAssignment;
import CohortDataClasses.Course;
import CohortDataClasses.FileReader;
import CohortDataClasses.Section;
import CohortsSolverData.CohortSolution;
import dataModels.Requirement;
import dataModels.StartRequest;
import runnable.ScheduleRunnable;

public class ScheduleController {
	
	private static String filePath = "data.xlsx";
	private static boolean cohortCalcRunning;
	private static ScheduleRunnable currentScheduler;
	private static Thread optThread;
	private static Semaphore semaphore;
	public static void init() {
		cohortCalcRunning = false;
		currentScheduler = null;
		optThread = null;
		semaphore = new Semaphore(1);
	}
	
	/*
	 * Start Private helper methods
	 * Start Private helper methods
	 */
	/**
	 * Goes through each of the cohorts an ensures that there exists a course for every cohort requirement
	 * @param courseList
	 * @param cohortList
	 * @throws Exception
	 */
	private static void verifyClassesExist(List<Course> courseList, List<Cohort> cohortList) throws Exception {
		// TODO create SchedulingException class
		for(Cohort cohort : cohortList) {
			for(ClassRequirement req : cohort.getRequirements()) {
				boolean found = false;
				for(Course course : courseList) {
					if(course.getName().equals(req.getClassName())) {
						if(course.getSections()!=null && !course.getSections().isEmpty()) {
							found = true;
						}
					}
				}
				if(!found) {
					//TODO change from generic exception to program specific exception
					throw new Exception("No available sections found for class "+ req.getClassName());
				}
			}
		}
	}
	
	/**
	 * Creates a list of Cohorts from the web input
	 * @param requirements
	 * @return List<Cohorts>
	 */
	private static List<Cohort> createCohorts(List<Requirement> requirements){
		HashMap<String,Cohort> cohorts = new HashMap<String,Cohort>();
		Cohort c;
		for (Requirement r : requirements) {
			c = cohorts.get(r.getCohort());
			if(c==null) {
				c = new Cohort();
				c.setName(r.getCohort());
				cohorts.put(r.getCohort(),c);
			}
			ClassRequirement req = new ClassRequirement();
			req.setClassName(r.getCourse());
			req.setSeatsNeeded(r.getSeatsNeeded());
			req.setSectionsAllowed(r.getSectionsAllowed());
			req.setSectionType(r.getSectionType());
			c.addReq(req);
		}
		return new ArrayList<Cohort>(cohorts.values());
	}
	/**
	 * @param count
	 * @param cohorts
	 * @param courses
	 * @return An array of initialized OptiPlanner solutions
	 * @throws Exception
	 */
	private static CohortSolution[] initializeSolution(int count, List<Cohort> cohorts, List<Course> courses) throws Exception {
		CohortSolution[] problems = new CohortSolution[count];
		List<CohortSectionAssignment> csa = new ArrayList<>();
		for(Cohort coh:cohorts) {
			//Finds the course for every requirement
			for(ClassRequirement req: coh.getRequirements()) {
				int courseIndex = -1;
				int labIndex = -1;
				for(Course course: courses) {
					if(req.getClassName().equals(course.getName())) {
						if(course.isLab()) {
							labIndex = courses.indexOf(course);
						}else {
							courseIndex = courses.indexOf(course);
						}
					}
				}
				if(courseIndex<0) {
					throw new Exception("Missing Course Object for "+req.getClassName());
				}
				CohortSectionAssignment toAdd = new CohortSectionAssignment();
				toAdd.setMyCohort(coh);
				toAdd.setMyCourse(courses.get(courseIndex));
				toAdd.setSectionCode(req.getSectionsAllowed());
				toAdd.setSeatsNeeded(req.getSeatsNeeded());
				toAdd.setSectionType(req.getSectionType());
				csa.add(toAdd);
				if(labIndex >= 0) {
					CohortSectionAssignment labToAdd = new CohortSectionAssignment();
					labToAdd.setMyCohort(coh);
					labToAdd.setMyCourse(courses.get(labIndex));
					labToAdd.setSectionCode(req.getSectionsAllowed());
					labToAdd.setSeatsNeeded(req.getSeatsNeeded());
					labToAdd.setSectionType(req.getSectionType());
					csa.add(labToAdd);
				}
			}
		}
		for(int i = 0; i < count; i++) {
			problems[i] = initProblem(i,csa, courses);
			
		}
		return problems;
	}
	
	/**
	 * Creates a initialized cohort solution for
	 * OptaPlanner
	 * @param i
	 * @param csa
	 * @param courses
	 * @return initialized solution
	 */
	private static CohortSolution initProblem(int i, List<CohortSectionAssignment> csa, List<Course> courses) {
		int j = i;
		for(CohortSectionAssignment c:csa) {
			c.setAssignment(c.possibleSections().get(j%c.possibleSections().size()));
			j++;
		}
		CohortSolution sol = new CohortSolution();
		sol.setAssignments(csa);
		sol.setCourses(courses);
		return sol;
	}
	
	/*
	 * End Private helper methods
	 * End Private helper methods
	 */
	public static String start(StartRequest request) throws IOException {
		File temp = null;
		
		try {
			temp = new File(filePath);
			
			if(optThread != null && optThread.isAlive()) {
				if(currentScheduler.isFinished()) {
					optThread.interrupt();
				}else {
					throw new Exception("Already running");
				}
			}
			if(!temp.exists())
				throw new Exception("Upload File");

		List<Section> sectionList = FileReader.readCourseExcel(temp.getAbsolutePath());
		List<Cohort> cohortList = createCohorts(request.getRequirements());
		List<Course> courseList= FileReader.separateSectionsIntoCourses(sectionList);
		courseList = splitLabs(courseList);
		labelLabReqs(cohortList, courseList);
		FileUtils.forceDelete(temp);
		System.out.println("deleted file");
		for(Course c:courseList) {
			for(Section s:c.getSections())
				s.setDayBool();
		}
		
		//verifies that a course exists for each ClassRequirement
		try {
			verifyClassesExist(courseList, cohortList);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "-1";
		}
		CohortSolution solutions[] = initializeSolution(1, cohortList, courseList);
		//recordSolutions(solutions);
		
		currentScheduler = new ScheduleRunnable(solutions, request.getName());
		}
		catch(Exception e) {
			e.printStackTrace();
			FileUtils.forceDelete(temp);
			return "0";
		}
		optThread = new Thread(currentScheduler);
		optThread.start();
		
		return "1";
	}
	
	public static String Upload(MultipartFile upload){
		try {
			File temp = new File(filePath);
			if(!semaphore.tryAcquire() || temp.exists())
				throw new Exception("Only need to start upload once");
			if(upload == null) {
				throw new Exception("File not recieved");
			}
			InputStream in = upload.getInputStream();
			File target = new File(filePath);
			FileUtils.copyInputStreamToFile(in, target);
		}
		catch(Exception e) {
			//e.printStackTrace();
			semaphore.release();
			return "\"Error\": \"" + e.getMessage() + "\"";
		}
		semaphore.release();
		return "\"Uploaded\"";
	}
	
	public static String status() {
		if(optThread != null && currentScheduler != null && 
				optThread.isAlive() && !currentScheduler.isFinished())
			return "{ \"status\": \"started\" }";
		else if(optThread != null && currentScheduler != null && 
				!optThread.isAlive() && currentScheduler.isFinished()) {
			return "{ \"status\": \"finished\" }";
		}
		else if(optThread != null && currentScheduler != null && 
				!optThread.isAlive() && !currentScheduler.isFinished()) {
			return "{ \"status\": \"canceled\" }";
		}
		else {
			return "{ \"status\": \"not started\" }";
		}		
	}
	
	public static String cancel() {
		if(optThread != null && optThread.isAlive()) {
			optThread.interrupt();
			return "{ \"Message\" : \"Scheduling will stop at next exit point\" }";
		}
		
		return "{ \"Message\" : \"No active scheduler\" }";
	}

	private static List<Course> splitLabs(List<Course> courseList){
		List<Course> newList = new ArrayList<>();
		for(Course course:courseList) {
			if(course.getSections().get(0).getLink()!=null) {
				Course lect = new Course();
				Course lab = new Course();
				lect.setCourseID(course.getCourseID());
				lect.setLab(false);
				lect.setName(course.getName());
				lect.setUnitSize(course.getUnitSize());
				lab.setCourseID(course.getCourseID());
				lab.setLab(true);
				lab.setName(course.getName());
				lab.setUnitSize(course.getUnitSize());
				List<Section> lects = new ArrayList<>();
				List<Section> labs = new ArrayList<>();
				for(Section s:course.getSections()) {
					if(s.getLink().startsWith("L") || StringUtils.isBlank(s.getLink())) {
						lects.add(s);
						
					}else {
						labs.add(s);
					}
				}
				if(!labs.isEmpty()) {
					lab.setSections(labs);
					newList.add(lab);
				}
				if(!lects.isEmpty()) {
					lect.setSections(lects);
					newList.add(lect);
				}
			}else {
				newList.add(course);
			}
		}
		return newList;
	}
	public static void labelLabReqs(List<Cohort> cohorts, List<Course> courses){
		for(Cohort c:cohorts) {
			for(ClassRequirement s: c.getRequirements()) {
				int i = 0;
				for(Course co:courses) {
					if(s.getClassName().equals(co.getName())) {
						i++;
					}
				}
				if(i>1) {
					s.setHasLab(true);
				}else {
					s.setHasLab(false);
				}
			}
			
		}
	}
}
