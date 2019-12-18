package optaplanner.CohortScoring;
import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import CohortDataClasses.Course;
import CohortDataClasses.Section;

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
	
	@Test
	public void classConflictsForCohort() {
		List<Section> sects = new ArrayList<>();
		Section s1 = new Section();
		s1.setDaysOfWeek("MTW");
		s1.setStartTime(LocalTime.of(1, 0, 0, 0));
		s1.setEndTime(LocalTime.of(2, 0,0,0));
		sects.add(s1);
		Section s2 = new Section();
		s2.setDaysOfWeek("MTW");
		s2.setStartTime(LocalTime.of(1, 0, 0, 0));
		s2.setEndTime(LocalTime.of(2, 0,0,0));
		sects.add(s2);
		Section s3 = new Section();
		s3.setDaysOfWeek("RF");
		s3.setStartTime(LocalTime.of(1, 0, 0, 0));
		s3.setEndTime(LocalTime.of(2, 0,0,0));
		sects.add(s3);
		assertTrue(-1==HardScoringFunctions.classConflictsForCohort((Section[])sects.toArray(new Section[0])));
	}
	
	@Test
	public void sameDay() {
		assertTrue(HardScoringFunctions.sameDay("F","MTRF"));
		assertTrue(HardScoringFunctions.sameDay("M","MTRF"));
		assertTrue(HardScoringFunctions.sameDay("MTRF","F"));
		assertTrue(HardScoringFunctions.sameDay("MTRF","M"));
		assertFalse(HardScoringFunctions.sameDay("MTRF","W"));
	}
}
