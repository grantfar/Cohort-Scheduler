#ifndef ENROLLMENT_H
#define ENROLLMENT_H
#include "courseSection.h"
#include <vector>
using namespace std;
class enrollment
{

public:
  enrollment(courseSection* enrolledIn, vector<int>cohorts);
  courseSection* enrolledIn;
  vector<int> cohorts;
  static bool testEnrolement(vector<enrollment>,int count);
  static vector<enrollment> genEnrollments(courseSection* enrolledIn ,vector<vector<int>> indexes);
};

#endif // ENROLLMENT_H
