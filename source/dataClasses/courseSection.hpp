#include <string>
class courseSection{
    private:
    bool days[5];
    std::string daysString;
    int startTime;
    int endTime;
    int seats;
public:
    bool OnDay(int dayNum);
    int getStartTime();
    int getEndTime();
    
    static bool sortingCriteria(courseSection* a, courseSection* b);
};