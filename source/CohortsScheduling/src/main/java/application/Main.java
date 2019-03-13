package application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controller.ScheduleController;
import CohortsSolverData.CohortSolution; 
import CohortDataClasses.*;
import java.util.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Main {
	public static void main(String[] args) {
		ScheduleController.init();
		SpringApplication.run(Main.class,args);
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
	
	public static void addSolutionToDB(CohortSolution s) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			for(CohortSectionAssignment csa: s.getAssignments()) {
				HttpPost post = new HttpPost("localhost:3000/assignment");
				List<NameValuePair> params = new ArrayList<>();
				//add params to list
				CloseableHttpResponse response = httpclient.execute(post);
				assert(response.getStatusLine().getStatusCode()==(200));
			    
			}
			httpclient.close();
		}finally {
			httpclient.close();
		}
	}
}
