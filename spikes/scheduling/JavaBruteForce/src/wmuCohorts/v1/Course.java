package wmuCohorts.v1;
/**
 * This class is the the course object, which stores all characteristics of a course
 * 
 * @author user
 *
 */
public class Course {
	private String course;
	private String section; 
	private int    numSections=0;
	private String days;
	private int    startTime;
	private int    endTime;
	private String campus;
	private int    classSize; 
	
	
	public Course(String pCourse, String pSection, String pDays, int pStartTime, int pEndTime, String pCampus, int pClassSize) {
		this.course = pCourse;
		this.section = pSection;
		this.days = pDays;
		this.startTime = pStartTime;
		this.endTime = pEndTime;
		this.campus = pCampus;
		this.classSize = pClassSize;
	}
	
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public int getClassSize() {
		return classSize;
	}
	public void setClassSize(int classSize) {
		this.classSize = classSize;
	} 
	public int getNumSections() {
		return numSections;
	}

	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
}
