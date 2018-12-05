#ifndef SECTION_H
#define SECTION_H

#include <string>

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
};


#endif // SECTION_H
