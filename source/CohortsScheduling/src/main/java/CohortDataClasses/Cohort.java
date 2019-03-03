package CohortDataClasses;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.ArrayList;
import java.util.List;

public class Cohort {
	
	private List<ClassRequirement> requirements;

    private String name;
    
    List<Section> classAssignments;

	public Cohort() {
		requirements = new ArrayList<>();
		classAssignments = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<ClassRequirement> requirements) {
		this.requirements = requirements;
	}

	public void addReq(ClassRequirement toAdd) {
		if(this.requirements != null) {
			this.requirements.add(toAdd);
		}else {
			this.requirements = new ArrayList<ClassRequirement>();
			this.requirements.add(toAdd);
		}
	}
	
	public boolean removeReqByIndex(int index) {
		if(index >= this.requirements.size()) {
			return false;
		}else if(index < 0) {
			return false;
		}else {
			this.requirements.remove(index);
			return true;
		}
	}

	public List<Section> getClassAssignments() {
		return classAssignments;
	}

	public void setClassAssignments(List<Section> classAssignments) {
		this.classAssignments = classAssignments;
	}
}
