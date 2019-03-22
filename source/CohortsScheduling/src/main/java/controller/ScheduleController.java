package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	private static boolean cohortCalcRunning;
	private static ScheduleRunnable currentScheduler;
	private static Thread optThread;
	public static void init() {
		cohortCalcRunning = false;
		currentScheduler = null;
		optThread = null;
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
				for(Course course: courses) {
					if(req.getClassName().equals(course.getName())) {
						courseIndex = courses.indexOf(course);
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
				csa.add(toAdd);
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
	public static String start(StartRequest request, MultipartFile file) {
		if(optThread != null && optThread.isAlive()) {
			return "Already running";
		}
		
		//each course object should have a non empty list of sections and a name
		//each section object should have all fields initialized
		try {

		File temp = File.createTempFile(file.getOriginalFilename(),".xlsx");
		FileOutputStream fos = new FileOutputStream(temp);
		fos.write(file.getBytes());
		fos.close(); 

		List<Section> sectionList = FileReader.readCourseExcel(temp.getPath());
		List<Cohort> cohortList = createCohorts(request.getRequirements());
		List<Course> courseList= FileReader.separateSectionsIntoCourses(sectionList);

		temp.delete();

		for(Course c:courseList) {
			for(Section s:c.getSections())
				s.setDayBool();
		}
		
		//verifies that a course exists for each ClassRequirement
		verifyClassesExist(courseList, cohortList);
		//Alex Write init function
		CohortSolution solutions[] = initializeSolution(1, cohortList, courseList);
		//recordSolutions(solutions);
		
		currentScheduler = new ScheduleRunnable(solutions, request.getName());
		}
		catch(Exception e) {
			return "{ \"Status\" : \"Failed to start\", \"Error\" : \"" + e.getMessage() + "\"}";
		}
		optThread = new Thread(currentScheduler);
		optThread.start();
		return "{ \"Status\" : \"Started\" }";
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

}
