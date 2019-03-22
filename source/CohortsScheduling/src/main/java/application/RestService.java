package application;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import runnable.ScheduleRunnable;
import org.springframework.web.multipart.MultipartFile;
import controller.ScheduleController;
import dataModels.StartRequest;
import dataModels.Requirement;
import java.util.ArrayList;
@Controller
@RequestMapping(value="/api")
public class RestService {
	@ResponseBody
	@PostMapping("/start")
	public static String start(String name,
			 ArrayList<Requirement> requirements, 
			MultipartFile file) {
			StartRequest request = new StartRequest();
			request.setName(name);
			request.setRequirements(requirements);
		return ScheduleController.start(request,file);
		//starts scheduling
	}
	@GetMapping(value="/status")
	@ResponseBody
	public static String status() {
		return ScheduleController.status();
	
	}
	@ResponseBody
	@PostMapping("/cancel")
	public static String cancel() {
		return ScheduleController.cancel();
		
	}

}
