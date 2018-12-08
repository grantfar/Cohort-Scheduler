package wmuCohorts.v1;

import java.util.ArrayList;

public class Schedule {
private ArrayList<Course> schedule; 
private Cohort cohort;

public Schedule(ArrayList<Course> pSchedule, Cohort pCohort) {
	this.cohort = pCohort;
	this.schedule = pSchedule;
}
public Schedule() {
	
}
public ArrayList<Course> getSchedule() {
	return schedule;
}
public void setSchedule(ArrayList<Course> schedule) {
	this.schedule = schedule;
}
public Cohort getCohort() {
	return cohort;
}
public void setCohort(Cohort cohort) {
	this.cohort = cohort;
}
 
}
