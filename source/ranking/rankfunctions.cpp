/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#include "rank.hpp"
#include "rankFunctions.hpp"
#include "moreClassesInDay/stackRank.hpp"
#include "afterTime/afterTime.hpp"
#include "timeBetweenClasses/timeBetweenClasses.hpp"

void initRankFunctions()
{
    rank::Functions.push_back(
        rankFunction{
            "Too Many Classes In one day",*stackRank
        }
    );

    rank::Functions.push_back(
        rankFunction{
            "Classes Get Out After 7PM",*afterTime
        }
    );

    rank::Functions.push_back(
        rankFunction{
            "Too Many Classes In one day",*timeBetweenClasses
        }
    );

}