package CohortDataClasses;

public class ClassRequirement {

	private String className;
	private String sectionsAllowed;
	private Integer seatsNeeded;
	private String cohortName;
	
	public ClassRequirement() {
		// TODO Auto-generated constructor stub
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSectionsAllowed() {
		return sectionsAllowed;
	}

	public void setSectionsAllowed(String sectionsAllowed) {
		this.sectionsAllowed = sectionsAllowed;
	}

	public Integer getSeatsNeeded() {
		return seatsNeeded;
	}

	public void setSeatsNeeded(Integer seatsNeeded) {
		this.seatsNeeded = seatsNeeded;
	}

	public String getCohortName() {
		return cohortName;
	}

	public void setCohortName(String cohortName) {
		this.cohortName = cohortName;
	}

}
