#include <string>
#include <vector>
#include "../defenitions/schedule.hpp"
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
    static int rankSchedules(schedule *);
    static void setRankFunctions(std::vector<rankFunction> f);
};

#endif