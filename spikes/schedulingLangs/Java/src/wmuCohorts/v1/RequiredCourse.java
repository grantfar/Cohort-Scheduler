package wmuCohorts.v1;
/**
 * This class is for the required course object. The object was created to store the name 
 * of the course a cohort needs, and also the number o seats they need on that class. 
 * @author user
 *
 */
public class RequiredCourse {
private String courseName;
private int reqSeats;


public RequiredCourse(String pName, int pSeats) {
	this.courseName = pName;
	this.reqSeats = pSeats;
}
public RequiredCourse() {
	
}
public String getCourseName() {
	return courseName;
}


public void setCourseName(String courseName) {
	this.courseName = courseName;
}


public int getReqSeats() {
	return reqSeats;
}


public void setReqSeats(int reqSeats) {
	this.reqSeats = reqSeats;
}




}
