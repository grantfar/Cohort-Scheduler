#include "cohorts.h"
#include <string>
#include "cohortrequirements.h"
#include <algorithm>
using namespace std;
cohorts::cohorts()
{
  cohortsList = vector<cohortRequirements>();
}

void cohorts::addRequirement(string name, requirement Class){
  cohortRequirements* n = this->findCohort(name);
  if(n == NULL){
      this->cohortsList.push_back(cohortRequirements(name));
      n = &(this->cohortsList.back());
    }
  n->addCourse(Class);
}

cohortRequirements* cohorts::findCohort(string name){
  for(int i = 0; i < this->cohortsList.size();i++){
      if(this->cohortsList[i].getName().compare(name)==0){
          return &(this->cohortsList[i]);
        }
    }
  return NULL;
}

 bool cohorts::compareRequirements(course a, course b){
  return a.neededby.size() > b.neededby.size();
}

void cohorts::generateSortedReqs(){
  for(int i = 0; i < this->cohortsList.size(); i++)
    {
      vector<requirement> tmp = this->cohortsList[i].getCourses();
      for(int j = 0; j < tmp.size(); j++){
          this->findCourse(tmp[j].Course)->neededby.push_back(this->cohortsList[i]);
          this->findCourse(tmp[j].Course)->index.push_back(j);
        }
    }
  sort(this->coursesRequired.begin(),this->coursesRequired.end(), this->compareRequirements);
}

vector<course> cohorts::getCoursesRequired(){
  return this->coursesRequired;
}

course* cohorts::findCourse(string name){
  for(int i = 0; i< this->coursesRequired.size(); i++){
      if(this->coursesRequired[i].name.compare(name)==0){
          return &(this->coursesRequired[i]);
        }
    }
  course c;
  c.name = name;
  c.neededby = vector<cohortRequirements>();
  this ->coursesRequired.push_back(c);
  return &(this->coursesRequired.back());
}
