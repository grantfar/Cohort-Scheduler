/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#ifndef SECTION_H
#define SECTION_H

#include <string>
#include <vector>

class courseSection
{
private:
  std::string Course;
  std::string Section;
  std::string DaysString;
  char Campus;
  int StartTime;
  int EndTime;
  bool Days[5];
  int ClassSize;
  void setDays(std::string days);
public:

  courseSection(std::string Course, std::string Section, std::string Days, int Start, int End, std::string campus, int ClassSize);

  std::string getCompareString();
  std::string getCourse();
  int getSize();
  bool operator <(courseSection toCompare);
  bool operator >(courseSection toCompare);
  bool operator ==(courseSection toCompare);
  int  getStartTime();
  int  getEndTime();
  bool* getDays();
  bool sameDays(courseSection c);
  bool overlaps( courseSection c);
  std::string getDaysString();
  static std::vector<std::vector<courseSection*>> generateConflicts(std::vector<courseSection> *coursesNeeded);
  static bool compareTimes(courseSection a, courseSection b);
};


#endif // SECTION_H
