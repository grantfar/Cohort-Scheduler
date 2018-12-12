#ifndef COHORTREQUIREMENTS_H
#define COHORTREQUIREMENTS_H

#include <string>
#include <vector>

using namespace std;

typedef struct requirement
{
  string Course;
  int seats;
} requirement;

class cohortRequirements
{
private:
  std::string CohortName;
  std::vector<requirement> Courses;

public:
  cohortRequirements(string name);
  cohortRequirements();
  string getName();
  void addCourse(requirement course);
  vector<requirement> getCourses();
  bool operator ==(cohortRequirements toComp);
};

#endif // COHORTREQUIREMENTS_H
