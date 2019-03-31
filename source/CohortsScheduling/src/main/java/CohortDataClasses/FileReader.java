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
import org.apache.poi.ss.usermodel.CellType;
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
	 *             Excel reading code used from. Using a hash map, it is able to
	 *             read in information by column name rather than by column number.
	 *             https://ww.callicoder.com/java-read-excel-file-apache-poi/
	 */	
	public static List<Section> readCourseExcel(String fileName) throws Exception{
		Workbook workbook = null;
		try {

			workbook = WorkbookFactory.create(new File(fileName));
			Iterator<Sheet> sheetIterator = workbook.sheetIterator();

			
			List<Section> sections = new ArrayList<Section>();
			Sheet sheet = null;
			
			while(sheetIterator.hasNext()) {
				sheet = sheetIterator.next();
				
				Iterator<Row> rowIterator = sheet.rowIterator();

				Map<String, Integer> map = new HashMap<String, Integer>(); // Create map
				
				Row row = null; // Get first row
				
				while (!map.containsKey("Course ID") && rowIterator.hasNext()) {
					row = rowIterator.next();
					map = new HashMap<String, Integer>();
					short minColIx = row.getFirstCellNum(); // get the first column index for a row
					short maxColIx = row.getLastCellNum(); // get the last column index for a row
					for (short colIx = minColIx; colIx < maxColIx; colIx++) { // loop from first to last index
						Cell cell = row.getCell(colIx); // get the cell
						if(cell.getCellTypeEnum().equals(CellType.STRING)) {
							map.put(cell.getStringCellValue(), cell.getColumnIndex()); // add the cell contents (name of column)
						}														// and
																					// cell index to the map
					}
				}
				while(rowIterator.hasNext()) {
					try {
						Section newSect = parseRowData(rowIterator.next(), map);
						if(newSect != null && newSect.getName() != null && newSect.getName().length()>3 && newSect.getName().length()<11) {
							sections.add(newSect);
						}
					}catch(NumberFormatException e) {
					}
				}
			}
			
			return sections;
		} catch (Exception e) {
			workbook.close();
			throw e;
		}
	}
	
	public static Section parseRowData(Row dataRow, Map<String, Integer> map) throws NumberFormatException {
		Section section = new Section();
		DataFormatter dataFormatter = new DataFormatter();
		// 1-------------------------------------------------------
		
			Cell cell1 = dataRow.getCell(map.get("Course ID"));
			String cellValue1 = dataFormatter.formatCellValue(cell1);
			section.setName(cellValue1);
		
		// 2-------------------------------------------------------
			Cell cell2 = dataRow.getCell(map.get("CRN"));
			String cellValue2 = dataFormatter.formatCellValue(cell2);
			
			try{
				int i = Integer.parseInt(cellValue2);
			}catch(NumberFormatException e){
				return null;
			}
			section.setCrn(Integer.parseInt(cellValue2));
		// 3-------------------------------------------------------
			Cell cell3 = dataRow.getCell(map.get("Section"));
			String cellValue3 = dataFormatter.formatCellValue(cell3);
			section.setSectionId(cellValue3);
		
		// 5-------------------------------------------------------
			Cell cell5 = dataRow.getCell(map.get("Link"));
			String cellValue5 = dataFormatter.formatCellValue(cell5);
			section.setLink(cellValue5);
		// 6-------------------------------------------------------
			Cell cell6 = dataRow.getCell(map.get("Meeting Days"));
			String cellValue6 = dataFormatter.formatCellValue(cell6);
			section.setDaysOfWeek(cellValue6);
		
		// 7-------------------------------------------------------
		try {
			Cell cell7 = dataRow.getCell(map.get("Meeting Time"));
			String cellValue7 = dataFormatter.formatCellValue(cell7);
			setExcelTimes(cellValue7, section);
		} catch (Exception e) {
			section.setStartTime(null);
			section.setEndTime(null);
		}
		// 8-------------------------------------------------------
			Cell cell8 = dataRow.getCell(map.get("Building"));
			String cellValue8 = dataFormatter.formatCellValue(cell8);
			section.setBuilding(cellValue8);
		
		// 9-------------------------------------------------------
			Cell cell9 = dataRow.getCell(map.get("Room"));
			String cellValue9 = dataFormatter.formatCellValue(cell9);
			section.setRoom(cellValue9);
		// 10------------------------------------------------------
			Cell cell10 = dataRow.getCell(map.get("Cap"));
			String cellValue10 = dataFormatter.formatCellValue(cell10);
			section.setSeats(Integer.parseInt(cellValue10));
		
		// 11
			Cell cell11 = dataRow.getCell(map.get("Title"));
			String cellValue11 = dataFormatter.formatCellValue(cell11);
			section.setTitle(cellValue11);
		
		return section;

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
