package CohortScoring;
import java.junit.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CohortDataClasses.Cohort;
import CohortDataClasses.CohortSectionAssignment;
import CohortDataClasses.Course;
import CohortDataClasses.Section;
import CohortsSolverData.CohortSolution;
import CohortDataClasses.CohortSectionAssignment;

public class HardScoringTest {

	HardScoringFunctions hsf = null;

	@Before
	public void init(){
		hsf = new HardScoringFunctions();
	}

	@Test
	public void calculateOverEnrolled_Test1(){
		List<Course> arg = new ArrayList<>();
		Course c = new Course();
		Section s = new Section();
		s.setSeats(10)
		s.setEnrolled(11);
		List<Section> sects = new ArrayList<>();
		sects.add(s);
		Section s1 = new Section();
		s1.setSeats(10)
		s1.setEnrolled(10);
		sects.add(s1);
		arg.setSections(sects);

	}
}
