#include "courseSection.h"
#include <string>

using namespace std;

  void courseSection::setDays(string days){
    Days[0] = false;
    Days[1] = false;
    Days[2] = false;
    Days[3] = false;
    Days[4] = false;

    for(int i = 0; i < days.length(); i++){
        Days[0] = Days[0] || days.at(i) == 'm' || days.at(i) == 'M';
        Days[1] = Days[1] || days.at(i) == 't' || days.at(i) == 'T';
        Days[2] = Days[2] || days.at(i) == 'w' || days.at(i) == 'W';
        Days[3] = Days[3] || days.at(i) == 'r' || days.at(i) == 'R';
        Days[4] = Days[4] || days.at(i) == 'f' || days.at(i) == 'F';
      }
    this->DaysString = days;
  }

  courseSection::courseSection(string Course, string Section, string Days, int Start, int End, string campus, int ClassSize)
  {
    this->Course = Course;
    this->Section = Section;
    this->ClassSize = ClassSize;
    this->Campus = campus.at(0);
    this->StartTime = Start;
    this->EndTime = End;

    setDays(Days);
  }

  bool courseSection::operator <(courseSection toCompare)
  {
    return getCompareString().compare(toCompare.getCompareString()) < 0;
  }
  bool courseSection::operator >(courseSection toCompare)
  {
    return getCompareString().compare(toCompare.getCompareString()) > 0;
  }
  bool courseSection::operator ==(courseSection toCompare)
  {
    return getCompareString().compare(toCompare.getCompareString()) == 0;
  }

  string courseSection::getCourse(){
    return this->Course;
  }

  int courseSection::getSize(){
    return this->ClassSize;
  }

  string courseSection::getCompareString(){
    string compareString = "";
    compareString += this->Course + this->Section;
    return compareString;
  }

