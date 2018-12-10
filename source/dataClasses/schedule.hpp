#ifndef SCHEDULE
#define SCHEDULE

#include "cohort.hpp"
#include <array>
#include "courseSection.hpp"

class schedule
{
private:
    cohort* Cohort;
    std::string ScheduleName;
    std::array<courseSection*,10> cohortSchedule;
    int courseCount;

public:
    schedule(schedule * s);
    std::array<courseSection*, 10> getEnrollments();
    schedule(cohort* Cohort, std::string name);
    void addSection(courseSection* toAdd);
    void clearSchedule();
    void sortSchedule();
    int getCourseCount();
};

#endif