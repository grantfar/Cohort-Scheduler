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
import java.util.Iterator;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
	 *             This method reads the class .csv file and stores its information
	 *             in course objects for the solver to use
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
	 *             This method reads in a cohort.csv file and stores its information
	 *             in cohort objects for the solver to use
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
	 *            method to parse and initialize start and end times
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
	 *             The following code will be for reading from a Microsoft Excel
	 *             file, rather than reading from a .csv file like the code above
	 *             Excel reading code used from. Using a hash map, it is able to read in information 
	 *             by column name rather than by column number.
	 *             https://ww.callicoder.com/java-read-excel-file-apache-poi/
	 */
	public static List<Section> readCourseExcel(String fileName)throws Exception {
		Workbook workbook = null;
		try {

		workbook = WorkbookFactory.create(new File(fileName));
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();

		DataFormatter dataFormatter = new DataFormatter();
		List<Section> sections = new ArrayList<Section>();
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.rowIterator();
		Section section = new Section();

		Map<String, Integer> map = new HashMap<String, Integer>(); // Create map
		Row row = sheet.getRow(0); // Get first row

		
		while(!map.containsKey("Course ID")) {
			map = new HashMap<String, Integer>();
			short minColIx = row.getFirstCellNum(); // get the first column index for a row
			short maxColIx = row.getLastCellNum(); // get the last column index for a row
			for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
				Cell cell = row.getCell(colIx); // get the cell
				map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column) and
																			// cell index to the map
			}
			
			row = rowIterator.next();
		}
		//if the first line does not contain one of these labels, an error occurs
		//the loop should work unless there are only some fields missing
		int col1 = map.get("Course ID");
		int col2 = map.get("CRN");
		int col3 = map.get("Section");
		int col5 = map.get("Link");
		int col6 = map.get("Meeting Days");
		int col7 = map.get("Meeting Time");
		int col8 = map.get("Building");
		int col9 = map.get("Room");
		int col10 = map.get("Cap");

		while (sheetIterator.hasNext()) {
			while (rowIterator.hasNext()) {
				Row dataRow = rowIterator.next();

				Cell cell1 = dataRow.getCell(col1); // Get the cells for each of the indexes
				Cell cell2 = dataRow.getCell(col2);
				Cell cell3 = dataRow.getCell(col3);
				Cell cell5 = dataRow.getCell(col5);
				Cell cell6 = dataRow.getCell(col6);
				Cell cell7 = dataRow.getCell(col7);
				Cell cell8 = dataRow.getCell(col8);
				Cell cell9 = dataRow.getCell(col9);
				Cell cell10 = dataRow.getCell(col10);

				String cellValue1 = dataFormatter.formatCellValue(cell1);
				String cellValue2 = dataFormatter.formatCellValue(cell2);
				String cellValue3 = dataFormatter.formatCellValue(cell3);
				String cellValue5 = dataFormatter.formatCellValue(cell5);
				String cellValue6 = dataFormatter.formatCellValue(cell6);
				String cellValue7 = dataFormatter.formatCellValue(cell7);
				String cellValue8 = dataFormatter.formatCellValue(cell8);
				String cellValue9 = dataFormatter.formatCellValue(cell9);
				String cellValue10 = dataFormatter.formatCellValue(cell10);

				section.setName(cellValue1);
				//the below line causes errors when there's lines of data after the table
				//check that required fields are not empty befor assigning them to a section
				section.setCrn(Integer.parseInt(cellValue2));
				section.setSectionId(cellValue3);
				section.setLink(cellValue5);
				section.setDaysOfWeek(cellValue6);
				try {
					setExcelTimes(cellValue7, section);
				} catch (Exception e) {
					section.setStartTime(null);
					section.setEndTime(null);
				}
				section.setBuilding(cellValue8);
				section.setRoom(cellValue9);
				section.setSeats(Integer.parseInt(cellValue10));

				sections.add(section);
				section = new Section();

			}
			sheet = sheetIterator.next();
		}

		return sections;
		}catch(Exception e) {
			workbook.close();
			throw e;
		}
	}

	public static List<Course> separateSectionsIntoCourses(List<Section> sections) {
		List<Course> courseList = new ArrayList<Course>();

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
	 *            method to parse times from excel file, not .csv
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
