package CohortDataClasses;

import java.util.Comparator;
import java.time.LocalTime;;
public class Section {
	
	private String name;
	private int    seats; 
	private int    crn; 
	private int    enrolled;
	private String sectionId;
	private int section;
	private String link;
	//start time and end time must be on the same day for ALL section objects
	private LocalTime   startTime;
	private LocalTime   endTime;
	private String daysOfWeek;
	private boolean[] dayBools;
	private String instructor;
	private String instructor2;
	private String building;
	private String room; 

	private int    subSectionId;
	
	public Section() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean onDay(int day) {
		return dayBools[day];
	}
	
	public void setDayBool() {
		daysOfWeek = daysOfWeek.toUpperCase();
		char subString;
		dayBools = new boolean[5];
		for(int i = 0; i < daysOfWeek.length(); i++) {
			subString =  daysOfWeek.charAt(i);
			switch(subString) {
				case 'M':
					dayBools[0] = true;
					break;
				case 'T':
					dayBools[1] = true;
					break;
				case 'W':
					dayBools[2] = true;
					break;
				case 'R':
					dayBools[3] = true;
					break;
				case 'F':
					dayBools[4] = true;
					break;
			}
		}
	}
	
	public String getInstructor2() {
		return instructor2;
	}

	public void setInstructor2(String instructor2) {
		this.instructor2 = instructor2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getCrn() {
		return crn;
	}

	public void setCrn(int crn) {
		this.crn = crn;
	}

	public int getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(String daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public int getSubSectionId() {
		return subSectionId;
	}

	public void setSubSectionId(int subSectionId) {
		this.subSectionId = subSectionId;
	}
	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
	
	public static class compareSections implements Comparator<Section>{
		public compareSections() {}
		@Override
		public int compare(Section arg0, Section arg1) {
			return arg0.getStartTime().compareTo(arg1.getStartTime());
		}
		
	}
}


