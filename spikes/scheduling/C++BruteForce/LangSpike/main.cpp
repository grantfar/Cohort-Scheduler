#include <iostream>
#include <cstdlib>
#include <string>
#include <fstream>
#include <ios>
#include <vector>
#include <sstream>
#include "courseSection.h"
#include "allsections.h"
#include "cohorts.h"
#include "cohortrequirements.h"
#include "permutation.h"
#include "schedule.h"
#include "enrollment.h"
#include "getwd.h"
using namespace std;

allSections readInAllSections(){
  ifstream classes;
  classes.open("/home/grant/Programming/Spikes/C++Spike/LanguageSpike/courseList.csv");
  if(!classes.is_open()){
      cerr << "broken" << endl;
    }
  char line[1024];
  stringstream formatting;
  classes.getline(line,1024);
  classes.getline(line,1024,',');
  formatting.clear();
  while(!classes.eof())
  {
    formatting << line << ' ';
    classes.getline(line,1024,',');
  }
  classes.close();
  int start;
  int end;
  int classSize;
  string course = "";
  string section;
  string days;
  string campus;

  allSections all;

  formatting >> course;
  while (course.length()!=0)
  {


     formatting >>  section >> days >> start >> end >> campus >> classSize;
     courseSection temp(course,section,days,start,end,campus,classSize);
     all.add(temp);
     course = "";
     formatting >> course;
  }
  return all;
}

cohorts readInAllCohorts()
{
  char line[1024];
  stringstream formatting;
  ifstream classes;
  classes.open("/home/grant/Programming/Spikes/C++Spike/LanguageSpike/cohortReqs.csv");
  classes.getline(line,1024);
  classes.getline(line,1024,',');
  formatting.clear();
  while(!classes.eof())
  {
    formatting << line << ' ';
    classes.getline(line,1024,',');
  }
  cohorts Cohorts;
  string name;
  formatting >> name;
  requirement re;
  while (name.length()!=0) {
      formatting >> re.Course >> re.seats;
      Cohorts.addRequirement(name,re);
      name = "";
      formatting >> name;
    }
  return Cohorts;
}

int main(int argc, char* argv[])
{
  allSections sections = readInAllSections();
  sections.sectionsSort();
  cohorts Cohorts = readInAllCohorts();
  Cohorts.generateSortedReqs();

  vector<course> coursesRequired = vector<course>();
  for(int i= 0; i< Cohorts.getCoursesRequired().size() && Cohorts.getCoursesRequired()[i].neededby.size()!=0; i++)
    coursesRequired.push_back(Cohorts.getCoursesRequired()[i]);
  vector<Schedule> validSchedules;
  vector<vector<courseSection>> sectionsAvailable = vector<vector<courseSection>>();
  for(int i = 0; i < coursesRequired.size(); i++){
      sectionsAvailable.push_back(sections.findSections(coursesRequired[i].name));
    }
  vector<vector<int>> fit;
  vector<vector<enrollment>> fitSets;
  for(int i = 0; i< sectionsAvailable.size(); i++)
    {
      for(int j = 0; j < sectionsAvailable[i].size(); j++)
        {
          fit = generateCombosFit(coursesRequired[i],sectionsAvailable[i][j].getSize());
          fitSets.push_back(enrollment::genEnrollments(&sectionsAvailable[i][j],fit));
          fit.clear();
        }
      coursesRequired[i].trialIndexes = generateWorkingCombos(fitSets, coursesRequired[i].neededby.size());
      fitSets.clear();
    }
  vector<vector<int>> fullScheduleIndexes;
  vector<int> lengths;
  for(int i = 0; i < coursesRequired.size() ;i++)
    {
      lengths.push_back(coursesRequired[i].trialIndexes.size());
    }
  fullScheduleIndexes = indexCombos(lengths);

 ofstream fileout;
 fileout.open("results.txt");

 cerr << getexepath() << fileout.is_open();
  for(int i = 0; i < fullScheduleIndexes.size();i++){
      Schedule s;
      for(int j = 0; j < fullScheduleIndexes[0].size(); j++)
        {
           s.addRequirements(coursesRequired[j].trialIndexes[fullScheduleIndexes[i][j]],coursesRequired[j].neededby);
        }

          fileout << s.toString() << endl << endl;

      if(i % 1000 == 0)
        {
          cout << i << "/" << fullScheduleIndexes.size() << endl;
        }
    }
  fileout.close();
}
