package application;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import runnable.ScheduleRunnable;

import controller.ScheduleController;
import dataModels.StartRequest;
@Controller
@RequestMapping(value="/api")
public class RestService {
	@ResponseBody
	@PostMapping("/start")
	public static String start(@RequestBody StartRequest request) {
		return ScheduleController.start(request);
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
