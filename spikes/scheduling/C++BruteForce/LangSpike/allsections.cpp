/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/
#include "allsections.h"
#include <algorithm>
using namespace std;




  allSections::allSections() {}
  allSections::allSections(vector<courseSection> Sections)
  {
    this->Sections = Sections;
  }

  int allSections::add(courseSection s)
  {
    Sections.push_back(s);
    return 0;
  }

  vector<courseSection> allSections::findSections(string name){
    vector<courseSection> toRet = vector<courseSection>();
    for(int i = 0; i<this->Sections.size() && this->Sections[i].getCourse().compare(name) <= 0; i++)
      {
        if(this->Sections[i].getCourse().compare(name) == 0)
          {
            toRet.push_back(Sections[i]);
          }
      }
    return toRet;
  }


  void allSections::sectionsSort()
  {
    sort(this->Sections.begin(),this->Sections.end());
  }




