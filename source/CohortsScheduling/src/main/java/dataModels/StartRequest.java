package dataModels;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.*;
public class StartRequest {
	private MultipartFile file;
	private List<Requirement> requirements;
	private int count;
	private String name;
	
	public List<Requirement> getRequirements(){
		return requirements;
	}
	
	public int getCount() {
		return count;
	} 
	
	public void setRequirements(List<Requirement> requirements){
		this.requirements = requirements;
	}
	
	public void setCount(int count) {
		this.count = count;
	} 
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public StartRequest() {
		count = 0;
		requirements = null;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public StartRequest(List<Requirement> requirements,int count) {
		this.count = count;
//		this.file = file;
		this.requirements = requirements;
	}
	
}
