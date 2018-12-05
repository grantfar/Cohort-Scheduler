package wmuCohorts.v1;
/**
 * This class reads from Csv files, and contains methods for returning an array of Course objects and an 
 * array of Cohort objects
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
private Course[] classList;
private int numberofCohorts;
private RequiredCourse[] frequencyList;	


	public FileReader(Course[] pClassList) {
		this.classList = pClassList;
	} 
	
	public FileReader() {
		
	}
	
	public Course[] readClassFile(String fileName) throws FileNotFoundException { 
		File file = new File(fileName);
		Scanner scan = new Scanner(file);  
		int index = 0; 
		int size = 0; 

		String line;
		String[] field = new String[7];
		
		//get number of lines
		//skip header line
	    scan.nextLine();
		while(scan.hasNext()) {
			line = scan.nextLine();
			size++; 

		}
		scan.close();  
		
		this.classList = new Course[size];
		scan = new Scanner(file);
		scan.nextLine();

		while(scan.hasNext()) {
			line = scan.nextLine();
			field = line.split(",");  
			
			
			
			classList[index] = new Course(field[0], field[1], field[2], Integer.parseInt(field[3]), 
					Integer.parseInt(field[4]), field[5], Integer.parseInt(field[6]));  
			
			
			
			index++;
		}
		
		scan.close();
		return classList;
	}
	
	
	public ArrayList<Cohort> readCohortFile(String fileName) throws FileNotFoundException { 
		File file = new File(fileName);
		Scanner scan = new Scanner(file);  
		int index = 0; 
		int index2 = 0;
		int size = 0;
		String line;
		String cohort;
		String cohortTemp = "";
		String[] field = new String[3];
		
		//get number of lines
		//skip header line
	    scan.nextLine();
		while(scan.hasNext()) {
			line = scan.nextLine();
			field = line.split(",");
			
			cohort = field[0];

			if(cohort.compareTo(cohortTemp)!=0) {
				
				size++; 
			
			}
			
			
		}
		scan.close();  
		
		ArrayList<Cohort> cohortList = new ArrayList<Cohort>();  
		this.frequencyList = new RequiredCourse[size];
		scan = new Scanner(file);
		
		//skip header file
		scan.nextLine();

		while(scan.hasNext()) {
			line = scan.nextLine();
			field = line.split(",");
			cohort = field[0]; 
			
		//keep track of frequency of each course to sort course list
			this.frequencyList[index2] = new RequiredCourse();
			this.frequencyList[index2].setCourseName(field[1]);
			
			
			if(cohort.compareTo(cohortTemp)!=0) {
				cohortList.add(new Cohort());
				cohortList.get(index).setCohortName(field[0]); 
				cohortList.get(index).addRequiredCourses(field[1], Integer.parseInt(field[2])); 
				cohortList.get(index).setCohortSize(Integer.parseInt(field[2]));
				this.numberofCohorts++;
				index++;
			}
			
			
			if(cohort.compareTo(cohortTemp)==0) {	
				cohortList.get(index-1).addRequiredCourses(field[1], Integer.parseInt(field[2])); 
			}
			
			
			cohortTemp = cohortList.get(index-1).getCohortName();
			
			
			index2++;			
		}
		
		scan.close();
		
		return cohortList;
	}
	public int getNumberOfCohorts() {
		return this.numberofCohorts;
	} 

	public Course[] getClassList() {
		return classList;
	}

	public void setClassList(Course[] classList) {
		this.classList = classList;
	}

	public int getNumberofCohorts() {
		return numberofCohorts;
	}

	public void setNumberofCohorts(int numberofCohorts) {
		this.numberofCohorts = numberofCohorts;
	}

	public RequiredCourse[] getFrequencyList() {
		return frequencyList;
	}

	public void setFrequencyList(RequiredCourse[] frequencyList) {
		this.frequencyList = frequencyList;
	}

}
