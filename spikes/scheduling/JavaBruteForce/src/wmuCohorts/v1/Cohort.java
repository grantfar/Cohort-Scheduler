package wmuCohorts.v1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class is for the Cohort object, which stores all characteristics of a cohort.
 */
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

public class Cohort {
	private String cohortName;
	private ArrayList<RequiredCourse> requiredCourses = new ArrayList<RequiredCourse>();
	private ArrayList<Course> schedule;
	private int cohortSize;

	public Cohort() {

	}

	public Cohort(String pName, ArrayList<RequiredCourse> pRequired, ArrayList<Course> pSchedule) {
		this.requiredCourses = pRequired;
		this.schedule = pSchedule;
		this.cohortName = pName;
	}

	public ArrayList<RequiredCourse> getRequiredCourses() {
		return this.requiredCourses;
	}

	public void addRequiredCourses(String pRequiredCourses, int numSeats) {
		this.requiredCourses.add(new RequiredCourse(pRequiredCourses, numSeats));
	}

	public ArrayList<Course> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<Course> schedule) {
		this.schedule = schedule;
	}

	public String getCohortName() {
		return cohortName;
	}

	public void setCohortName(String cohortName) {
		this.cohortName = cohortName;
	}

	public void setCohortSize(int size) {
		this.cohortSize = size;
	}

	public int getCohortSize() {
		return this.cohortSize;
	}
	//
	// public void createSchedulesOnce(Course[] classList, ArrayList<Cohort>
	// cohortList) {
	// // go through cohort list and to compare their required classes
	// int issues = 0;
	// int points = 0;
	// int start = 0;
	//
	// for (int i = 0; i < cohortList.size(); i++) {
	//
	// ArrayList<RequiredCourse> requiredCourses =
	// cohortList.get(i).getRequiredCourses();
	// ArrayList<Course> tempSched = new ArrayList<Course>();
	//
	// // go through the cohort's required classes to compare to class list and
	// create
	// // schedule
	//
	// for (int j = 0; j < requiredCourses.size(); j++) {
	//
	// RequiredCourse rc = requiredCourses.get(j);
	//
	// // go through classList
	// start = 0;
	// for (int k = start; k < classList.length; k++) {
	//
	// if (rc.getCourseName().compareTo(classList[k].getCourse()) == 0) {
	// // check to see if any class in current schedule conflicts with class in
	// // classList
	//
	// if (!checkConflicts(classList[k], tempSched, rc)) {
	// if (classList[k].getClassSize() > rc.getReqSeats()) {
	// points++;
	// tempSched.add(classList[k]);
	// classList[k].setClassSize(classList[k].getClassSize()
	// - cohortList.get(i).getRequiredCourses().get(j).getReqSeats());
	// }
	// } else {
	//
	// issues++;
	// }
	//
	// }
	//
	// }
	//
	// }
	//
	// cohortList.get(i).setSchedule(tempSched);
	//
	// }
	// System.out.println("Points: " + points);
	// System.out.println("Issues: " + issues);
	// }

	private boolean checkConflicts(Course course, ArrayList<Course> schedule, Course required) {
		boolean doesConflict = false;

		for (int i = 0; i < schedule.size(); i++) {

			if (required.getCourse().compareTo(schedule.get(i).getCourse()) == 0) {
				doesConflict = true;
			}
			// check if days are same
			if (course.getDays().charAt(0) == schedule.get(i).getDays().charAt(0)
					|| course.getDays().charAt(1) == schedule.get(i).getDays().charAt(1)) {
				// if days are same check if times are same
				if (course.getStartTime() == schedule.get(i).getStartTime()) {
					doesConflict = true;
				}
				// check if course end time is too close to current schedule start time
				if (course.getEndTime() > schedule.get(i).getStartTime() - 50
						&& course.getEndTime() < schedule.get(i).getStartTime()) {
					doesConflict = true;
				}
				// check if course start time is too close to current schedule end time
				if (course.getStartTime() < schedule.get(i).getEndTime() + 50
						&& course.getStartTime() > schedule.get(i).getStartTime()) {
					doesConflict = true;
				}
				if (schedule.get(i).getStartTime() < course.getEndTime()
						&& schedule.get(i).getStartTime() > course.getStartTime()) {
					doesConflict = true;
				}

			}

		}

		return doesConflict;
	}

	public void getPermutations(Course[] classList, ArrayList<Cohort> cohortList) throws IOException {

		// cohort list is just to keep track of what each cohort is required to take
		// classList is used to create all possible permutations of each class
		// this method will create all possible permutations of each class
		// may need to create Schedule class with cohort has a field, as well as
		// schedule
		for (int l = 0; l < cohortList.size(); l++) {
			
			Cohort cohort = cohortList.get(l);
			ArrayList<RequiredCourse> rc = cohort.getRequiredCourses();
			ArrayList<Course> permArray = new ArrayList<>();
			System.out.println("Permutations for :" + cohort.getCohortName());
			for (int i = 0; i < rc.size(); i++) {
				
				for (int j = 0; j < classList.length; j++) {
					if (classList[j].getCourse().compareTo(rc.get(i).getCourseName()) == 0) {
						permArray.add(classList[j]);
						
						
					}
				}
			}
			heapPermutation(permArray, rc.size(), permArray.size());

		}

	}

	private void printArr(ArrayList<Course> a, int n) throws IOException {
		// File file = new File("output.txt");
		//
		// file.createNewFile();
		// FileWriter fw = new FileWriter(file);
		// BufferedWriter bw = new BufferedWriter(fw);
		for (int i = 0; i < n; i++) {
			System.out.println(a.get(i).getCourse() + " " + a.get(i).getSection() + " ");
		}
		System.out.println();

	}

	// Generating permutation using Heap Algorithm
	private void heapPermutation(ArrayList<Course> a, int size, int n) throws IOException {
		// if size becomes 1 then prints the obtained
		// permutation

		if (size == 1) {

			printArr(a, n);

		}

		for (int i = 0; i < size; i++) {

			heapPermutation(a, size - 1, n);

			heapPermutation(a, size - 1, n);

			// if size is odd, swap first and last
			// element

			// if (size % 2 == 1)
			// {

			int j = (n % 2 == 0) ? i : 0;

			Course temp = a.get(size - 1);
			a.set(size - 1, a.get(j));
			a.set(j, temp);

			// }

			// // If size is even, swap ith and last
			// // element
			// else
			// {
			// Course temp = a.get(i);
			// a.set(i,a.get(size-1));
			// a.set(size-1,temp);
			// }
		}

	}

}
