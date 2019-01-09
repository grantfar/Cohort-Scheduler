#ifndef COURSE
#define COURSE

#include <string>
#include <vector>
#include "courseSection.hpp"


class cohort;

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