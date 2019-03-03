package CohortDataClasses;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class CohortSectionAssignment {
	//this gets initialized before starting optaPlanner and does not change
	private Cohort myCohort;
	
	//this is what gets continuously changed by optaplanner
	private Section assignment;
	//this gets set before optaPlanner and does not change
	private Course myCourse;
	//this gets set before optaplanner and does not change
	//represents alpha part of section code i.e. if this is "HE" then "HE1 is an allowable section
	private String sectionCode;
	private Integer seatsNeeded;
	
	public CohortSectionAssignment(Course course, Section assignment, Cohort cohort) {
		this.myCohort = cohort;
		this.assignment = assignment;
		this.myCourse = course;
	}
	
	public CohortSectionAssignment() {
		
	}

	public Cohort getMyCohort() {
		return myCohort;
	}

	public void setMyCohort(Cohort myCohort) {
		this.myCohort = myCohort;
	}
	@PlanningVariable(valueRangeProviderRefs = {"courseSection"}, nullable = false)
	public Section getAssignment() {
		return assignment;
	}

	public void setAssignment(Section assignment) {
		this.assignment = assignment;
	}

	public Course getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(Course myCourse) {
		this.myCourse = myCourse;
	}
	//this is how optaPlanner knows what sections work for this assignment
	@ValueRangeProvider(id = "courseSection")
	public List<Section> possibleSections(){
		return this.myCourse.getSections();
	}
	
	public static boolean startsWith(String sect, String code) {
		char a = sect.charAt(0);
		int i = 1;
		while(Character.isLetter(a)) {
			if(code.length()<i||a!=code.charAt(i-1)) {
				return false;
			}
			a = sect.charAt(i);
			i++;
		}
		return true;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public Integer getSeatsNeeded() {
		return seatsNeeded;
	}

	public void setSeatsNeeded(Integer seatsNeeded) {
		this.seatsNeeded = seatsNeeded;
	}
}
