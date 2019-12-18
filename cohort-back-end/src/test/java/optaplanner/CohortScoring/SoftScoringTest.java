package optaplanner.CohortScoring;
import org.junit.Test;

import CohortDataClasses.Cohort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SoftScoringTest {
	public void waitTimeTest() {
		List<Cohort> testCohorts = new ArrayList<>();
		testCohorts.add(new Cohort());
		Method method = null;
		try {
			method = SoftScoringFunctions.class.getDeclaredMethod("dayScores",new Class[] {List.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		method.setAccessible(true);
		try {
			method.invoke(testCohorts);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
