#include "schedule.h"
#include <algorithm>
Schedule::Schedule()
{
  this->setOfSchedules = vector<cohortSchedule>();
}

void Schedule::addRequirements(vector<enrollment> enrollments, vector<cohortRequirements> cohorts)
{
  for(int i = 0; i<enrollments.size(); i++)
    {
      for(int j = 0; j<enrollments[i].cohorts.size(); j++)
        {
          addRequirement(cohorts[enrollments[i].cohorts[j]],*(enrollments[i].enrolledIn));
        }
    }
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

  this->setOfSchedules.push_back(tmp);
}



string Schedule::toString()
{
  string ret = "";
  for(int i = 0; i< this->setOfSchedules.size(); i++){
      ret += this->setOfSchedules[i].cohort.getName() + ":\n";
      for(int j = 0; j<this->setOfSchedules[i].schedule.size(); j++)
        {
         ret += "  " + this->setOfSchedules[i].schedule[j].getCompareString() + "  " + this->setOfSchedules[i].schedule[j].getDaysString() +"  " + to_string(this->setOfSchedules[i].schedule[j].getStartTime())
             + " - " + to_string(this->setOfSchedules[i].schedule[j].getEndTime()) + "\n";
        }
    }
  return ret;
}
