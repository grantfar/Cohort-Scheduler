#ifndef ENROLLMENT_H
#define ENROLLMENT_H
#include "courseSection.h"
#include <vector>
using namespace std;
class enrollment
{

public:
  enrollment(courseSection* enrolledIn, vector<vector<int>>cohorts);
  courseSection* enrolledIn;
  vector<int> cohorts;
  static bool enrollment::testEnrolement(vector<enrollment>,int count);
};

#endif // ENROLLMENT_H
