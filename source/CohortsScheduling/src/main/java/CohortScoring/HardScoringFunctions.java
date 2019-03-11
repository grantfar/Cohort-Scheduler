package CohortScoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CohortDataClasses.Cohort;
import CohortDataClasses.CohortSectionAssignment;
import CohortDataClasses.Course;
import CohortDataClasses.Section;
import CohortsSolverData.CohortSolution;
import CohortDataClasses.CohortSectionAssignment;
public class HardScoringFunctions {
    public static int scoreSchedule(CohortSolution solution){
        int score = 0;
        List<Cohort> cohorts = putAssignmentsInCohorts(solution);
        for(Cohort s:cohorts) {
        	Section[] sects = s.getClassAssignments().toArray(new Section[10]);
        	score += classConflictsForCohort(sects);
        }
        List<Course> courses = addEnrollmentsToCourses(solution);
        score+= calculateOverEnrolledScore(courses);
        return score;
    }

    private static int calculateOverEnrolledScore(List<Course> courses) {
		int score = 0;
		for(Course course:courses) {
			for(Section s : course.getSections()) {
				if(s.getEnrolled()>s.getSeats()) {
					score -= (s.getEnrolled() - s.getSeats())/5;
				}
			}
		}
		return score;
	}

	private static List<Course> addEnrollmentsToCourses(CohortSolution solution) {
		List<Section> allSectUsed = new ArrayList<>();
		for(CohortSectionAssignment csa : solution.getAssignments()) {
			int index = indexOfSect(allSectUsed,csa.getAssignment());
			if(index>=0) {
				allSectUsed.get(index).setEnrolled(allSectUsed.get(index).getEnrolled()+csa.getSeatsNeeded());
			}
			else {
				Section toAdd = new Section();
				toAdd.setBuilding(csa.getAssignment().getBuilding());
				toAdd.setCrn(csa.getAssignment().getCrn());
				toAdd.setDaysOfWeek(csa.getAssignment().getDaysOfWeek());
				toAdd.setEndTime(csa.getAssignment().getEndTime());
				toAdd.setEnrolled(csa.getAssignment().getEnrolled()+csa.getSeatsNeeded());
				toAdd.setInstructor(csa.getAssignment().getInstructor());
				toAdd.setLink(csa.getAssignment().getLink());
				toAdd.setName(csa.getAssignment().getName());
				toAdd.setRoom(csa.getAssignment().getRoom());
				toAdd.setSeats(csa.getAssignment().getSeats());
				toAdd.setSection(csa.getAssignment().getSection());
				toAdd.setSectionId(csa.getAssignment().getSectionId());
				toAdd.setStartTime(csa.getAssignment().getStartTime());
				toAdd.setSubSectionId(csa.getAssignment().getSubSectionId());
				allSectUsed.add(toAdd);
			}
		}
		List<Course> courses = new ArrayList<>();
		for(Section s: allSectUsed) {
			int index = indexOfCourse(courses,s.getName());
			if(index>=0) {
				List<Section> temp = courses.get(index).getSections();
				temp.add(s);
				courses.get(index).setSections(temp);
			}else {
				List<Section> temp = new ArrayList<>();
				temp.add(s);
				Course toAdd = new Course();
				toAdd.setName(s.getName());
				toAdd.setSections(temp);
				courses.add(toAdd);
			}
		}
		
		return courses;
	}
	
	public static int indexOfCourse(List<Course> courses, String name) {
		for(Course c : courses) {
			if(c.getName().equals(name)) {
				return courses.indexOf(c);
			}
		}
		return -1;
	}
	
	public static int indexOfSect(List<Section> list, Section sect) {
		for(Section s:list) {
			if(s.getName().equals(sect.getName())&&s.getCrn()==(sect.getCrn())) {
				return list.indexOf(s);
			}
		}
		return -1;
	}

	private static List<Cohort> putAssignmentsInCohorts(CohortSolution solution) {
		Map<String,List<Section>> sectMap = new HashMap<>();
		for(CohortSectionAssignment csa: solution.getAssignments()) {
			if(sectMap.containsKey(csa.getMyCohort().getName())) {
				List<Section> temp = sectMap.get(csa.getMyCohort().getName());
				temp.add(csa.getAssignment());
				sectMap.put(csa.getMyCohort().getName(),temp);
			}else {
				List<Section> temp = new ArrayList<>();
				temp.add(csa.getAssignment());
				sectMap.put(csa.getMyCohort().getName(), temp);
			}
		}
		List<String> cohortNames = new ArrayList<String>(sectMap.keySet());
		List<Cohort> cohorts = new ArrayList<>();
		for(String name:cohortNames) {
			Cohort coh = new Cohort();
			coh.setName(name);
			coh.setClassAssignments(sectMap.get(name));
			cohorts.add(coh);
		}
		return cohorts;
	}
	
	

	protected static int classConflictsForCohort(Section[] sects) {
    	int score = 0;
    	
    	for(int i = 0; i < sects.length;i++) {
    		for(int j = (1+i); j < sects.length;j++) {
    			if(sects[i] !=null && sects[j] !=null && sameDay(sects[i].getDaysOfWeek(),sects[j].getDaysOfWeek())) {
    				if(sects[i].getEndTime().isBefore(sects[j].getEndTime())&&sects[i].getEndTime().isAfter(sects[j].getStartTime())) {
    					score--;
    				}else if(sects[i].getEndTime().isBefore(sects[j].getEndTime())&&sects[i].getEndTime().isAfter(sects[j].getStartTime())) {
    					score--;
    				}else if(sects[i].getEndTime().compareTo(sects[j].getEndTime())==0 || sects[i].getEndTime().compareTo(sects[j].getStartTime())==0) {
    					score--;
    				}else if(sects[j].getEndTime().compareTo(sects[i].getEndTime())==0 || sects[j].getEndTime().compareTo(sects[i].getStartTime())==0) {
    					score--;
    				}
    			}
    		}
    	}
    	return score;
    }
    
    protected static boolean sameDay(String days1, String days2) {
    	boolean same = false;
    	for(int i = 0; i < days1.length(); i++) {
    		for(int j = 0; j < days2.length(); j++) {
    			if(days1.charAt(i) == days2.charAt(j)) {
    				same = true;
    			}
    		}
    	}
    	return same;
    }
    
    
}
