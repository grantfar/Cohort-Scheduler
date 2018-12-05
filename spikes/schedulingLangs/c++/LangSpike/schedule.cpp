#include "schedule.h"

Schedule::Schedule()
{
  this->setOfSchedules = vector<cohortSchedule>();
}

void Schedule::addRequirement(cohortRequirements toAddTo, courseSection toAdd)
{
  for(int i = 0; i < this->setOfSchedules.size(); i++)
    {
      if( this->setOfSchedules[i].cohort == toAddTo){
          this->setOfSchedules[i].schedule.push_back(toAdd);
          return;
        }
    }
  cohortSchedule tmp;
  tmp.cohort = toAddTo;
  tmp.schedule = vector<courseSection>();
  tmp.schedule.push_back(toAdd);
}
