#include "../../../test/C++ Testing/catch.hpp"
#include "afterTime.hpp"



int main(int argc, char const *argv[])
{
    bool testDays[5] = {true,false,true,false,false};
    cohort testCohort;
    schedule s = schedule(&testCohort,"testSchedule");
    courseSection section = courseSection(testDays,"MW",1130,1230,30);
    s.addSection(&section);
    section = courseSection(testDays,"MW",930,1030,30);
    s.addSection(&section);
    section = courseSection(testDays,"MW",1130,1930,30);
    s.addSection(&section);
    section = courseSection(testDays,"MW",1130,1900,30);
    s.addSection(&section);
    section = courseSection(testDays,"MW",1130,1230,30);
    s.addSection(&section);
    section = courseSection(testDays,"MW",1130,1230,30);
    s.addSection(&section);
    REQUIRE(afterTime(&s) == 2);
    return 0;
}
