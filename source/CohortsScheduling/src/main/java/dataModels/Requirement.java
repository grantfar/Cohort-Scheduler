package dataModels;

public class Requirement {
	private String cohort;
	private String course;
	private String sectionsAllowed;
	private int seatsNeeded;
	private String sectionType;
	
	public Requirement(String cohort, String course, String sectionsAllowed, int seatsNeeded) {
		this.cohort = cohort;
		this.course = course;
		this.seatsNeeded = seatsNeeded;
		this.sectionsAllowed = sectionsAllowed;
	}
	
	public Requirement() {
		this.cohort = null;
		this.course = null;
		this.seatsNeeded = 0;
		this.sectionsAllowed = null;
	}
	
	public String getCohort() {
		return cohort;
	}
	public String getCourse() {
		return course;
	}
	public String getSectionsAllowed() {
		return sectionsAllowed;
	}
	
	public int getSeatsNeeded() {
		return seatsNeeded;
	}
	
	public void setCohort(String cohort) {
		this.cohort = cohort;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setSectionsAllowed(String sectionsAllowed) {
		this.sectionsAllowed = sectionsAllowed;
	}
	
	public void setSeatsNeeded(int seatsNeeded) {
		this.seatsNeeded = seatsNeeded;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
}
