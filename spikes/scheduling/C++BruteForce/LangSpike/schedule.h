/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/
#ifndef SCHEDULE_H
#define SCHEDULE_H
#include "cohortrequirements.h"
#include "courseSection.h"
#include <string>
#include <vector>
#include "enrollment.h"
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
  void addRequirements(vector<enrollment> enrollments, vector<cohortRequirements> cohorts);
  void addRequirement(cohortRequirements toAddTo, courseSection toAdd);
  bool checkValid();
  string toString();
};

#endif // SCHEDULE_H
