package CohortScoring;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import CohortDataClasses.Course;
import CohortDataClasses.FileReader;
import CohortDataClasses.Section;

public class FileReaderTest throws EcryptedDocumentException, InvalidFormatException, IOException {
	@Test
	public void fileReader_Test1(){
		FileReader fr = new FileReader();
		List<Section> list = new ArrayList<Section>();  
		
		
		list = fr.readCourseExcel("TestFiles/testCourseOfferings.xlsx"); 
		
		assertTrue(list.size()>0);
	}
	
	@Test
	public void fileReader_Test2() throws EcryptedDocumentException, InvalidFormatException, IOException {
		FileReader fr = new FileReader();
		List<Section> list = new ArrayList<Section>(); 
		List<Course> courseList = new ArrayList<Course>(); 
		
		list = fr.readCourseExcel("TestFiles/testCourseOfferings.xlsx"); 
		
		courseList = fr.separateSectionsIntoCourses(list);  
		
		assertTrue(courseList.size()>0);
		
	} 
	
	
	
}
