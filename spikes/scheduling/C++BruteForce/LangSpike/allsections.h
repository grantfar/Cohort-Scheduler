#include "courseSection.h"
#include <vector>
#include <string>
#ifndef ALLSECTIONS_H
#define ALLSECTIONS_H


class allSections
{
private:
  std::vector<courseSection> Sections;
public:
  allSections();
  allSections(std::vector<courseSection> Sections);
  int add(courseSection s);
  std::vector<courseSection> findSections(std::string name);
  void sectionsSort();
};

#endif // ALLSECTIONS_H
