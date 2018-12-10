#ifndef COHORT
#define COHORT

#include <string>
#include <array>
#include "schedule.hpp"
#include "course.hpp"

class cohort{
    private:
    std::string name;
    std::array<course*,10> requiredClasses;
    std::array<int,10> seatsRequired;
    schedule* tentitiveSchedule;
};
#endif