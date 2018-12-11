#include "schedule.hpp"
#include <algorithm>
using namespace std;

schedule::schedule(cohort* Cohort, string name)
{
    this->Cohort = Cohort;
    this->cohortSchedule.fill(NULL);
    this->ScheduleName = name;
    this->courseCount = 0;
}

/**
 * Clones S
 **/ 
schedule::schedule(schedule * s)
{
    this->cohortSchedule =  s->getEnrollments();
    this->ScheduleName = s->ScheduleName;
    this->courseCount = s->courseCount;
    this->Cohort = s->Cohort;
}

array<courseSection*, 10> schedule::getEnrollments()
{
    return this->cohortSchedule;    
}

void schedule::addSection(courseSection* toAdd)
{
    if(this->courseCount == 10)
    {
        throw "Cohort Schedule Full";
    }
    this->cohortSchedule[this->courseCount] = toAdd;
    this->courseCount++;
}

void schedule::clearSchedule()
{
    this->courseCount = 0;
    this->cohortSchedule.fill(NULL);
}

int schedule::getCourseCount(){
    return this->courseCount;
} 

void schedule::sortSchedule()
{
    sort(this->cohortSchedule.begin(), this->cohortSchedule.end(),courseSection::sortingCriteria);
}