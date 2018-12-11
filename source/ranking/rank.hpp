#include <string>
#include <vector>
#include "schedule.hpp"
#ifndef RANK
#define RANK
typedef struct rankFunction{
    std::string name;
    int (*function)(schedule*);
} rankFunction;

class rank
{
    public:
    static std::vector<rankFunction> Functions;
    static int rankSchedule(schedule *);
    static void initRank();
};

#endif