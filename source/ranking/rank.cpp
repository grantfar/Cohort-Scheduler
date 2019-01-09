/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#include "rank.hpp"
#include "rankFunctions.hpp"
int rank::rankSchedule(schedule* s)
{
    int rankVal = 0;
    for(int i = 0; i < rank::Functions.size(); i++)
    {
        rankVal += rank::Functions[i].function(s);
    }
    return rankVal;
}

void rank::initRank()
{
    initRankFunctions();
}