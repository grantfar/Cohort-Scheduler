package wmuCohorts.v1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//mock objects testing: Mockito, free, open source
//Look up JUnit and include library
//finish this asap and add testing

public class WMUCohortsMain {
	// sort classes by the cohorts that need them

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub 
		double start = System.currentTimeMillis(); 
		
			methodCaller(); 
			
		double end = System.currentTimeMillis();
		
		System.out.println("Total run time: " + (end - start));
	}
	
//method to print all output
	public static void methodCaller() throws IOException {
		FileReader fr = new FileReader();
		Course[] courseList;
		
		courseList = fr.readClassFile("courseList.csv");
		ArrayList<Cohort>cohortList = fr.readCohortFile("cohortReqs.csv");

		//cohortList.get(0).createSchedulesOnce(courseList, cohortList);
		cohortList.get(0).getPermutations(courseList, cohortList);
		
		//printSchedule(cohortList);

	}
	
	
public static void printSchedule(ArrayList<Cohort> cohortList) {
	for(int l = 0; l<cohortList.size(); l++) {
		System.out.printf("Cohort: %-5s, Schedule:\n",cohortList.get(l).getCohortName());
		for(int m = 0; m<cohortList.get(l).getSchedule().size(); m++) {
			System.out.printf("%s %s %4s %4d %4d %s %2d\n", cohortList.get(l).getSchedule().get(m).getCourse(), 
					cohortList.get(l).getSchedule().get(m).getSection(),
					cohortList.get(l).getSchedule().get(m).getDays(), cohortList.get(l).getSchedule().get(m).getStartTime(), 
					cohortList.get(l).getSchedule().get(m).getEndTime(),
					cohortList.get(l).getSchedule().get(m).getCampus(), cohortList.get(l).getSchedule().get(m).getClassSize());
		}
		System.out.println();
	}
}
public static void printFiles(Course[] courseList, ArrayList<Cohort> cohortList, FileReader fr) {
	System.out.println("Classes available: ");
	System.out.println();

	for (int i = 0; i < courseList.length; i++) {
		System.out.printf("%s %s %4s %4d %4d %s %2d\n", courseList[i].getCourse(), courseList[i].getSection(),
				courseList[i].getDays(), courseList[i].getStartTime(), courseList[i].getEndTime(),
				courseList[i].getCampus(), courseList[i].getClassSize());
	}

	System.out.println();
	System.out.println();
	System.out.println("Cohorts and required classes");
	
	for (int j = 0; j < fr.getNumberOfCohorts(); j++) {
		System.out.printf("Cohort: %-5s  ", cohortList.get(j).getCohortName());
		for (int k = 0; k < cohortList.get(j).getRequiredCourses().size(); k++) {
			System.out.printf("Course Name: %s, Required Seats %2d | ",
					cohortList.get(j).getRequiredCourses().get(k).getCourseName(),
					cohortList.get(j).getRequiredCourses().get(k).getReqSeats()); 
			
		}
		System.out.println();
	} 		System.out.println();
	

}
}
