#ifndef SCHEDULE_H
#define SCHEDULE_H
#include "cohortrequirements.h"
#include "courseSection.h"
#include <string>
#include <vector>

typedef struct cohortSchedule
{
  cohortRequirements cohort;
  vector<courseSection> schedule;
} cohortSchedule;

class Schedule
{
public:
  Schedule();
  vector<cohortSchedule> setOfSchedules;
  void addRequirement(cohortRequirements toAddTo, courseSection toAdd);
};

#endif // SCHEDULE_H
