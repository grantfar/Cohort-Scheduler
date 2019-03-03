package CohortDataClasses;

import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Course implements Serializable {
	
	private List<Section> sections;
	private String name;
	private String courseID;
	private int    unitSize;
	
	public Course() {
		this.sections = new ArrayList<Section>();
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnitSize() {
		return unitSize;
	}

	public void setUnitSize(int unitSize) {
		this.unitSize = unitSize;
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}


}
