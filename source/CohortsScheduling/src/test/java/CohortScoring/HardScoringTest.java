package CohortScoring;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.*;
import CohortDataClasses.Cohort;
import CohortDataClasses.CohortSectionAssignment;
import CohortDataClasses.Course;
import CohortDataClasses.Section;
import CohortsSolverData.CohortSolution;
import CohortDataClasses.CohortSectionAssignment;

public class HardScoringTest {

	@Test
	public void calculateOverEnrolled_Test1(){
		List<Course> arg = new ArrayList<>();
		Course c = new Course();
		Section s = new Section();
		s.setSeats(10);
		s.setEnrolled(11);
		List<Section> sects = new ArrayList<>();
		sects.add(s);
		Section s1 = new Section();
		s1.setSeats(10);
		s1.setEnrolled(10);
		sects.add(s1);
		c.setSections(sects);
		arg.add(c);
		int retVal = HardScoringFunctions.calculateOverEnrolledScore(arg);
		assertTrue(retVal==-1);
	}
	
	@Test
	public void calculateOverEnrolled_Test2(){
		List<Course> arg = new ArrayList<>();
		Course c = new Course();
		Section s = new Section();
		s.setSeats(10);
		s.setEnrolled(15);
		List<Section> sects = new ArrayList<>();
		sects.add(s);
		Section s1 = new Section();
		s1.setSeats(10);
		s1.setEnrolled(20);
		sects.add(s1);
		c.setSections(sects);
		arg.add(c);
		int retVal = HardScoringFunctions.calculateOverEnrolledScore(arg);
		assertTrue(retVal==-3);
	}
	
	@Test
	public void calculateOverEnrolled_Test3(){
		List<Course> arg = new ArrayList<>();
		Course c = new Course();
		Section s = new Section();
		s.setSeats(10);
		s.setEnrolled(0);
		List<Section> sects = new ArrayList<>();
		sects.add(s);
		Section s1 = new Section();
		s1.setSeats(10);
		s1.setEnrolled(9);
		sects.add(s1);
		c.setSections(sects);
		arg.add(c);
		int retVal = HardScoringFunctions.calculateOverEnrolledScore(arg);
		assertTrue(retVal==0);
	}
}
