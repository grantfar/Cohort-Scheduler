#include "rank.hpp"
int rank::rankSchedules(schedule* s)
{
    int rankVal = 0;
    for(int i = 0; i < rank::Functions.size(); i++)
    {
        rankVal += rank::Functions[i].function(s);
    }
    return rankVal;
}