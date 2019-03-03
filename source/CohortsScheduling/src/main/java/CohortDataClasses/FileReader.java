package CohortDataClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileReader {

	public FileReader() {

	}
	/**
	 * 
	 * @param fileName
	 * @param courseList
	 * @throws FileNotFoundException
	 * @throws ParseException 
	 * This method reads the class .csv file and stores 
	 * its information in course objects for the solver to use
	 */
	public static void readClassFile(String fileName, List<Course> courseList)
			throws FileNotFoundException, ParseException {
		File file = new File(fileName);
		Scanner scan = new Scanner(file);
		String line;
		String[] field = new String[14];
		Course course = new Course();
		List<Section> sections = new ArrayList<Section>();
		Section section = new Section();

		// skip title line
		scan.nextLine();
		line = scan.nextLine();
		field = line.split(",");
		course.setName(field[1]);
		course.setCourseID(field[1]);
		course.setUnitSize(Integer.parseInt(field[13]));
		section.setCrn(Integer.parseInt(field[2]));
		section.setSectionId(field[3]);
		section.setName(field[1]);
		section.setLink(field[5]);
		section.setDaysOfWeek(field[8]);
		setTimes(field, section);
		section.setBuilding(field[10]);
		section.setRoom(field[11]);
		section.setInstructor(field[12]);
		section.setSeats(Integer.parseInt(field[13]));

		// add initialized section to sections list
		sections.add(section);

		while (scan.hasNext()) {
			line = scan.nextLine();
			field = line.split(",");

			// check to see if same course, if so add to sections list
			if (course.getName().compareTo(field[1]) == 0) {
				section = new Section();
				section.setSectionId(field[3]);
				section.setCrn(Integer.parseInt(field[2]));
				section.setName(field[1]);
				section.setLink(field[5]);
				section.setDaysOfWeek(field[8]);
				try {
					setTimes(field, section);
				} catch (Exception e) {
					section.setStartTime(null);
					section.setEndTime(null);
				}
				section.setBuilding(field[10]);
				section.setRoom(field[11]);
				section.setInstructor(field[12]);
				section.setSeats(Integer.parseInt(field[13]));
				// add initialized section to sections list

				sections.add(section);

			}
			// if not the same course, create new course and add finished course to course
			// list
			else {
				// finish and set course since we have arrived at a new course
				course.setSections(sections);

				// add finished course to list
				courseList.add(course);
				course = new Course();
				section = new Section();
				sections = new ArrayList<Section>();
				course.setName((field[1]));
				course.setUnitSize(Integer.parseInt(field[13]));
				section.setSectionId(field[3]);
				section.setCrn(Integer.parseInt(field[2]));
				section.setName(field[1]);
				section.setLink(field[5]);
				try {
					setTimes(field, section);
				} catch (Exception e) {
					section.setStartTime(null);
					section.setEndTime(null);
				}
				section.setDaysOfWeek(field[8]);
				section.setBuilding(field[10]);
				section.setRoom(field[11]);
				section.setInstructor(field[12]);
				section.setSeats(Integer.parseInt(field[13]));
				sections.add(section);

			}

		}
		scan.close();

	}
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException 
	 * 
	 * This method reads in a cohort.csv file and stores its 
	 * information in cohort objects for the solver to use
	 */
	
	public static List<Cohort> readCohortFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scan = new Scanner(file);
		String line;
		String[] field = new String[7];
		List<ClassRequirement> requirements = new ArrayList<ClassRequirement>();

		ClassRequirement requirement = new ClassRequirement();

		// skip title line
		scan.nextLine();

		line = scan.nextLine();
		field = line.split(",");
		requirement.setCohortName(field[0]);
		requirement.setClassName(field[1]);
		requirement.setSectionsAllowed(field[3]);
		requirement.setSeatsNeeded(Integer.parseInt(field[2]));

		requirements.add(requirement);

		while (scan.hasNext()) {
			ClassRequirement newReq = new ClassRequirement();
			line = scan.nextLine();
			field = line.split(",");
			newReq.setClassName(field[1]);
			newReq.setCohortName(field[0]);
			newReq.setSeatsNeeded(Integer.parseInt(field[2]));
			newReq.setSectionsAllowed(field[3]);
			requirements.add(newReq);
		}
		scan.close();
		return separateReqsIntoCohorts(requirements);
	}
	
	/**
	 * 
	 * @param requirements
	 * @return
	 */
	private static List<Cohort> separateReqsIntoCohorts(List<ClassRequirement> requirements) {
		List<Cohort> cohorts = new ArrayList<>();
		Map<String, List<ClassRequirement>> mapping = new HashMap<>();
		for (ClassRequirement req : requirements) {
			if (mapping.containsKey(req.getCohortName())) {
				List<ClassRequirement> temp = mapping.get(req.getCohortName());
				temp.add(req);
				mapping.put(req.getCohortName(), temp);
			} else {
				List<ClassRequirement> temp = new ArrayList<>();
				temp.add(req);
				mapping.put(req.getCohortName(), temp);
			}
		}
		for (String cName : mapping.keySet()) {
			Cohort cohort = new Cohort();
			cohort.setRequirements(mapping.get(cName));
			cohort.setName(cName);
			cohorts.add(cohort);
		}
		return cohorts;
	}

	/**
	 * 
	 * @param field
	 * @param section 
	 *  method to parse and initialize start and end times
	 */
	private static void setTimes(String[] field, Section section) {
		String[] tempTime = new String[2];
		String[] tempTime2 = new String[2];
		int hour;
		int minute;

		tempTime = field[9].split(" - ");
		tempTime2 = tempTime[0].split(":");
		hour = Integer.parseInt(tempTime2[0]);
		minute = Integer.parseInt(tempTime2[1]);
		section.setStartTime(LocalTime.of(hour, minute));
		tempTime2 = tempTime[1].split(":");
		hour = Integer.parseInt(tempTime2[0]);
		minute = Integer.parseInt(tempTime2[1]);
		section.setEndTime(LocalTime.of(hour, minute));
	}

	

	/**
	 * 
	 * @param fileName
	 * @param courseList
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * The following code will be for reading from a Microsoft Excel file, rather
	 * than reading from a .csv file like the code above
	 * Excel reading code used from
	 * https://ww.callicoder.com/java-read-excel-file-apache-poi/ 
	 */
	public static void readCourseExcel(String fileName, List<Course> courseList)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		Workbook workbook = WorkbookFactory.create(new File(fileName));
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		List<Section> sections = new ArrayList<Section>();
		Section section = new Section();

		int rowNum = 0;
		int colNum = 0;

		for (Row row : sheet) {

			rowNum++;

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				if (rowNum > 0) // to skip title row 
					
					switch (colNum) {

					case 0: // do nothing
						break;
					case 1: // course name + section name
						section.setName(cellValue);
						break;
					case 2: // crn
						section.setCrn(Integer.parseInt(cellValue));
						break;
					case 3: // sections
						section.setSectionId(cellValue);
						break;
					case 5: // link
						section.setLink(cellValue);
						break;
					case 8: // days of week
						section.setDaysOfWeek(cellValue);
						break;
					case 9: // time
						try {
							setExcelTimes(cellValue, section);
						} catch (Exception e) {
							section.setStartTime(null);
							section.setEndTime(null);
						}
						break;
					case 10: // building
						section.setBuilding(cellValue);
						break;
					case 11: // room
						section.setRoom(cellValue);
						break;
					case 12: // instructor
						section.setInstructor(cellValue);
						break;
					case 13: // unit size
						section.setSeats(Integer.parseInt(cellValue));
						
						break;
					

					}  
				
				sections.add(section);

				colNum++;
			}

		}

	} 
	
	public static List<Course> separateSectionsIntoCourses(List<Section> sections){
		List <Course>  courseList = new ArrayList<Course>();
		
		Map<String, List<Section>> mapping = new HashMap<>(); 
		
		for (Section section : sections) {
			if (mapping.containsKey(section.getName())) {
				List<Section> temp = mapping.get(section.getName());
				temp.add(section);
				mapping.put(section.getName(), temp);
			} else {
				List<Section> temp = new ArrayList<>();
				temp.add(section);
				mapping.put(section.getName(), temp);
			}
		}
		for (String cName : mapping.keySet()) {
			Course course = new Course();
			course.setSections(mapping.get(cName));
			course.setName(cName);
			courseList.add(course);
		}

		return courseList;
	}
	
	
	/**
	 * 
	 * @param cell
	 * @param section
	 * method to parse times from excel file, not .csv
	 */
	public static void setExcelTimes(String cell, Section section) {
		String[] tempTime = new String[2];
		String[] tempTime2 = new String[2];
		int hour;
		int minute;

		tempTime = cell.split(" - ");
		tempTime2 = tempTime[0].split(":");
		hour = Integer.parseInt(tempTime2[0]);
		minute = Integer.parseInt(tempTime2[1]);
		section.setStartTime(LocalTime.of(hour, minute));
		tempTime2 = tempTime[1].split(":");
		hour = Integer.parseInt(tempTime2[0]);
		minute = Integer.parseInt(tempTime2[1]);
		section.setEndTime(LocalTime.of(hour, minute));
	}

}
