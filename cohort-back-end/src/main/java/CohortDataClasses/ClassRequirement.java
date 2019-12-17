package CohortDataClasses;

public class ClassRequirement {

	private String className;
	private String sectionsAllowed;
	private Integer seatsNeeded;
	private String cohortName;
	private boolean hasLab;
	private String sectionType;
	
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

	public boolean isHasLab() {
		return hasLab;
	}

	public void setHasLab(boolean hasLab) {
		this.hasLab = hasLab;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

}
