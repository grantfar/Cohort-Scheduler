/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#include "cohortrequirements.h"
#include <string>
#include <vector>
#include <algorithm>
using namespace std;


  cohortRequirements::cohortRequirements(){}


  cohortRequirements::cohortRequirements(string cohortName)
  {
    this->CohortName = cohortName;
    this->Courses = vector<requirement>();
  }

  void cohortRequirements::addCourse(requirement course)
  {
    this->Courses.push_back(course);
  }

  string cohortRequirements::getName(){
    return this->CohortName;
  }

  bool cohortRequirements::operator ==(cohortRequirements toComp){
    return this->CohortName.compare(toComp.getName()) == 0;
  }

  vector<requirement> cohortRequirements::getCourses()
  {
    return this->Courses;
  }
