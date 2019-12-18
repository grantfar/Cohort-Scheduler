package application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import controller.ScheduleController;
import optaplanner.CohortsSolverData.CohortSolution;
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
}
