#include "courseSection.hpp"
bool courseSection::sortingCriteria(courseSection* a, courseSection* b)
{
    if(b == NULL)
        return true;
    if(a == NULL)
        return false;
    return a->getStartTime() < b->getStartTime();
}

bool courseSection::OnDay(int day)
{
    if(day < 0 || day > 4)
    {
        throw "Only 5 Days a Week";
        return false;
    }

    return this->days[day];
}

int courseSection::getStartTime()
{
    return this->startTime;
}
