#ifndef COURSE
#define COURSE

#include <string>
#include <vector>
#include "courseSection.hpp"
#include "cohort.hpp"
class course{
private:
    std::string name;
    std::vector<courseSection> sectionsAvalable;
    std::vector<cohort*> requiredBy;
public:
    course(std::string name);
    std::vector<courseSection> getSections();
};

#endif