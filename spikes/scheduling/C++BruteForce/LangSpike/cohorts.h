#ifndef COHORTS_H
#define COHORTS_H
#include <string>
#include "cohortrequirements.h"
#include <vector>
#include "enrollment.h"
using namespace std;

typedef struct course
{
  string name;
  vector<cohortRequirements> neededby;
  vector<int> index;
  vector<vector<enrollment>> trialIndexes;
} course;

class cohorts
{
private:
  vector<cohortRequirements> cohortsList;
  vector<course> coursesRequired;
public:
  cohorts();
  cohortRequirements* findCohort(string name);
  void addRequirement(string name, requirement Class);
  void generateSortedReqs();
  static bool compareRequirements(course a, course b);
  course* findCourse(string name);
  vector<course> getCoursesRequired();
};

#endif // COHORTS_H
