package CohortScoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.*;
import java.util.Calendar;
import java.util.Comparator;

import CohortDataClasses.Cohort;
import CohortDataClasses.CohortSectionAssignment;
import CohortDataClasses.Section;
import CohortsSolverData.CohortSolution;

public class SoftScoringFunctions {
    public static int scoreSchedule(CohortSolution s){
        int score = 0;
        List<Cohort> cohorts = putAssignmentsInCohorts(s);
        score += assignmentsPastSeven(s);
        score += dayScores(cohorts);
        score += moreThanThreeInADay(cohorts);
        return score;
    }
    
    private static int dayScores(List<Cohort> cohorts) {
    	int score = 0;
    	List<List<Section>> days = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			days.add(new ArrayList<Section>());
		}
		for(Cohort c: cohorts) {
			for(List<Section> s: days) {
				s.clear();
			}
			for(Section s: c.getClassAssignments()) {
				if(s.onDay(0))
					days.get(0).add(s);
				if(s.onDay(1))
					days.get(1).add(s);
				if(s.onDay(2))
					days.get(2).add(s);
				if(s.onDay(3))
					days.get(3).add(s);
				if(s.onDay(4))
					days.get(4).add(s);
			}
			for(List<Section> s: days) {
				
				s.sort(new Section.compareSections());
			}
			score += tooMuchWaitTime(days) + backToBackToBack(days);
		}
		return score;
    }
    
    private static int tooMuchWaitTime(List<List<Section>> days) {
		int score = 0;
    	for(List<Section> L : days)
			for(int i = 1; i<L.size() ;i++) 
				if(L.get(i).getStartTime().isAfter(L.get(i-1).getEndTime().plusMinutes(90)))
					score--;
		
		return score * score * -1;
	}

	private static int moreThanThreeInADay(List<Cohort> cohorts) {
		int score = 0;
		for(Cohort c: cohorts) {
			int[] dayCounts = new int[5];
			for(Section s: c.getClassAssignments()) {
				if(s.onDay(0))
					dayCounts[0]++;
				if(s.onDay(1))
					dayCounts[0]++;
				if(s.onDay(2))
					dayCounts[0]++;
				if(s.onDay(3))
					dayCounts[0]++;
				if(s.onDay(4))
					dayCounts[0]++;
			}
			int tmpScore = 0;
			for(int i = 0; i < 5; i++) {
				if(dayCounts[i] > 3)
					tmpScore += dayCounts[i]-3;
				if(dayCounts[i] > 5) 
					tmpScore += dayCounts[i]-5;
			}
			score += (int)Math.pow(tmpScore, 1.75);
		}
		return score*-1;
	}
	
	

	private static int backToBackToBack(List<List<Section>> days) {
		int score = 0;
			for(List<Section> L: days) {
				for(int i = 1; i < L.size(); i++) {
					if(L.get(i).getStartTime().isBefore(L.get(i-1).getEndTime().minusMinutes(20)))
							score ++;
				}
				score*=score*-1;
			}
		return score;
	}

	private static int assignmentsPastSeven(CohortSolution s) {
		int count = 0;
		
		for(CohortSectionAssignment c : s.getAssignments()) {
			if(c.getAssignment().getStartTime()==null||c.getAssignment().getStartTime().getHour()>=19)
				count--;
		}
		return count;
	}

	private static List<Cohort> putAssignmentsInCohorts(CohortSolution solution) {
		Map<String,List<Section>> sectMap = new HashMap<>();
		for(CohortSectionAssignment csa: solution.getAssignments()) {
			if(sectMap.containsKey(csa.getMyCohort().getName())) {
				List<Section> temp = sectMap.get(csa.getMyCohort().getName());
				temp.add(csa.getAssignment());
				sectMap.put(csa.getMyCohort().getName(),temp);
			}else {
				List<Section> temp = new ArrayList<>();
				temp.add(csa.getAssignment());
				sectMap.put(csa.getMyCohort().getName(), temp);
			}
		}
		List<String> cohortNames = new ArrayList<String>(sectMap.keySet());
		List<Cohort> cohorts = new ArrayList<>();
		for(String name:cohortNames) {
			Cohort coh = new Cohort();
			coh.setName(name);
			coh.setClassAssignments(sectMap.get(name));
			cohorts.add(coh);
		}
		return cohorts;
	}
}
