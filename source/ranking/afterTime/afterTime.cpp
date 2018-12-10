#include "afterTime.hpp"
int afterTime(schedule * s)
{
    int count = 0;
    for(int i = 0; i< s->getCourseCount(); i++)
    { 
        if(s->getEnrollments()[i]->getEndTime() > 1900)
            count++;
    }
    return count;
}