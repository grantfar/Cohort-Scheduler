package scheduleDAO;

import CohortsSolverData.CohortSolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import CohortDataClasses.CohortSectionAssignment;

public class ScheduleDAO {

	public ScheduleDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static int writeToDB(CohortSolution s, String scheduleName) throws IOException {
		int count = 0;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			for(CohortSectionAssignment csa: s.getAssignments()) {
				HttpPost post = new HttpPost("localhost:3000/api/assignment");
				List<NameValuePair> params = new ArrayList<>();
				NameValuePair schedule = new BasicNameValuePair("schedule", scheduleName);
				NameValuePair cohort = new BasicNameValuePair("cohort",csa.getMyCohort().getName());
				NameValuePair className = new BasicNameValuePair("class",csa.getMyCourse().getName());
				NameValuePair sect = new BasicNameValuePair("sect",csa.getAssignment().getSectionId());
				NameValuePair startTime = new BasicNameValuePair("startTime",csa.getAssignment().getStartTime().toString());
				NameValuePair endTime = new BasicNameValuePair("endTime",csa.getAssignment().getEndTime().toString());
				NameValuePair days = new BasicNameValuePair("days", csa.getAssignment().getDaysOfWeek());
				NameValuePair seats = new BasicNameValuePair("seats",csa.getSeatsNeeded().toString());
				params.add(schedule);
				params.add(cohort);
				params.add(className);
				params.add(sect);
				params.add(startTime);
				params.add(endTime);
				params.add(days);
				params.add(seats);
				post.setEntity(new UrlEncodedFormEntity(params));
				
				CloseableHttpResponse response = httpclient.execute(post);
				if(response.getStatusLine().getStatusCode()==(200)) {
					count++;
				}
			    
			}
			httpclient.close();
		}finally {
			httpclient.close();
		}
		return count;
	}

}
