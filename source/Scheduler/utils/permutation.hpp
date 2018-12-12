#ifndef PERMUTATION_H
#define PERMUTATION_H
#include <vector>
#include "cohorts.h"
#include "enrollment.h"
using namespace std;
vector<vector<int>> permutation(int indexCount, int length);
void heapsAlgorithm(vector<int> indexes, int len, vector<vector<int>>* toAddTo);
vector<vector<int>> comboIndexes(int end, int len);
vector<vector<int>> generateCombosFit(course potental, int maxSize);
vector<vector<int>> indexCombos(vector<int> lengths);

vector<vector<enrollment>> generateWorkingCombos(vector<vector<enrollment>> posibilities, int cohorts);
#endif // PERMUTATION_H
