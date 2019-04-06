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

import java.io.IOException;
import java.util.ArrayList;
@Controller
@CrossOrigin(origins = {"http://localhost:4200", "https://cohorts.cs.wmich.edu",allowCredentials="true")
@RequestMapping(value="/api")
public class RestService {
	@ResponseBody
	@PostMapping("/start")
	public static String start(@RequestBody StartRequest startrq) throws IOException {
		return ScheduleController.start(startrq);
		//starts scheduling
	}
	
	@ResponseBody
	@PostMapping("/upload")
	public static String upload(@RequestParam("file") MultipartFile file) {
		return ScheduleController.Upload(file);
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
