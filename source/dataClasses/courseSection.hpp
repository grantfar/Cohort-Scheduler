#ifndef COURSESECTION
#define COURSESECTION
#include <string>
class courseSection{
    private:
    bool days[5];
    std::string daysString;
    int startTime;
    int endTime;
    int seats;
public:
    bool OnDay(int dayNum);
    int getStartTime();
    int getEndTime();
    courseSection(bool days[5], std::string daysString, int startTime, int endTime, int seats);
    static bool sortingCriteria(courseSection* a, courseSection* b);
};

#endif