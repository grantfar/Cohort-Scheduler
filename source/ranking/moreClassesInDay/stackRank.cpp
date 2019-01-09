/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#include "stackRank.hpp"
using namespace std;

int score(int toScore)
{
    if(toScore <=2)
        return 0;
    if(toScore == 3)
        return 2;
    if(toScore == 4)
        return 7;
    return 99;
}


int stackRank(schedule* s)
{
    int dayRanks[5];

    array<courseSection*,10> enrolledIn = s->getEnrollments();

    for(int i = 0; i<5; i++)
    {
        dayRanks[i] = 0;
    }

    for(int i = 0; s->getCourseCount(); i++)
    {
        for(int j = 0; j<5;j++)
        {
            if(enrolledIn[i]->OnDay(j))
            {
                dayRanks[j]++;
            }
        }
    }

    return score(dayRanks[0]) + score(dayRanks[1]) + score(dayRanks[2]) + score(dayRanks[3]) + score(dayRanks[4]);
}


