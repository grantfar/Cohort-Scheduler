package CohortDataClasses;

import static org.junit.Assert.*; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import CohortDataClasses.Course;
import CohortDataClasses.FileReader;
import CohortDataClasses.Section;

public class FileReaderTest {
	
    @Test
    public void fileReader_Test1() throws Exception {
       
        List<Section> list = new ArrayList<Section>();


        list = FileReader.readCourseExcel("TestFiles/testCourseOfferings.xlsx"); 

        assertTrue(list.size()>0);
    }

    @Test
    public void fileReader_Test2() throws  Exception {
     
        List<Section> list = new ArrayList<Section>(); 
        List<Course> courseList = new ArrayList<Course>(); 

        list = FileReader.readCourseExcel("TestFiles/testCourseOfferings.xlsx"); 

        courseList = FileReader.separateSectionsIntoCourses(list);

        assertTrue(courseList.size()>0);

    } 

    @Test
    public void fileReader_Test3() throws  Exception {
     
        List<Section> list = new ArrayList<Section>(); 
        List<Course> courseList = new ArrayList<Course>(); 

        list = FileReader.readCourseExcel("TestFiles/Fall2019.xlsx"); 

        courseList = FileReader.separateSectionsIntoCourses(list);
        
        assertTrue(courseList.size()>0);

    } 

    @Test
    public void fileReader_Test4() throws  Exception {
     
        List<Section> list = new ArrayList<Section>(); 
        List<Course> courseList = new ArrayList<Course>(); 

        list = FileReader.readCourseExcel("TestFiles/CAS-STEM_Course_Offerings_Fall_2018.xlsx"); 


        assertTrue(list.size()>0);

    }  
    
    @Test
    public void fileReader_Test5() throws  Exception {
     
        List<Section> list = new ArrayList<Section>(); 
        List<Course> courseList = new ArrayList<Course>(); 

        list = FileReader.readCourseExcel("TestFiles/CAS-STEM_Course_Offerings_Fall_2018.xlsx"); 

        courseList = FileReader.separateSectionsIntoCourses(list);

        assertTrue(courseList.size()>0);

    }  

}