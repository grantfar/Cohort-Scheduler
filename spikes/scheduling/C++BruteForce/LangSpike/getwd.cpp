#include "getwd.h"

#include <string>
#include <limits.h>
#include <unistd.h>
using namespace std;
std::string getexepath()
{
  char result[ PATH_MAX ];
  ssize_t count = readlink( "/proc/self/exe", result, PATH_MAX );
  return std::string( result, (count > 0) ? count : 0 );
}
