#include "enrollment.h"
using namespace std;
enrollment::enrollment(courseSection* enrolledIn, vector<int>cohorts)
{
  this->enrolledIn = enrolledIn;
  this->cohorts = cohorts;
}

bool enrollment::testEnrolement(vector<enrollment> test,int count)
{
  vector<bool> alreadyIn(count);
  for(int i = 0; i<count; i++)
    {
      alreadyIn[i] = false;
    }
  for(int i = 0; i<test.size(); i++){
      for(int j = 0; j< test[i].cohorts.size(); j++){
          if(alreadyIn[test[i].cohorts[j]])
            return false;
          alreadyIn[test[i].cohorts[j]] = true;
        }
    }
  return true;
}

vector<enrollment> enrollment::genEnrollments(courseSection* enrolledIn ,vector<vector<int>> indexes)
{
  vector<enrollment> ret;
  for(int i = 0; i< indexes.size(); i++)
    {
      ret.push_back(enrollment(enrolledIn,indexes[i]));
    }
  return ret;
}

