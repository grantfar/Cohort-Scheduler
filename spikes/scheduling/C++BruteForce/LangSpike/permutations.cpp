/**
 * MIT License
 * Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth
 **/

#include <vector>
#include "permutation.h"
#include "cohorts.h"
#include <list>
#include <array>
using namespace std;

vector<vector<int>> permutation(int indexCount, int length){
    vector<vector<int>> combos = comboIndexes(indexCount,length);
    vector<vector<int>> ret = vector<vector<int>>();
    while(combos.size()!=0){
        heapsAlgorithm(combos.back(),length,&ret);
        combos.pop_back();
    }
    return ret;
}


vector<vector<int>> generateCombosFit(course potental, int maxSize)
{
  vector<vector<int>> ret;
  vector<int> indexes;
  list<vector<int>> toExpore = list<vector<int>>();
  list<int> sums;
  for(int i = 0; i < potental.neededby.size(); i++){
      if(potental.neededby[i].getCourses()[potental.index[i]].seats <= maxSize)
        {
          indexes.push_back(i);
          sums.push_back(potental.neededby[i].getCourses()[potental.index[i]].seats);
          vector<int> tmp = vector<int>();
          tmp.push_back(indexes.size()-1);
          toExpore.push_back(tmp);
        }
    }

  while (!toExpore.empty()) {
      vector<int> tmp = vector<int>(toExpore.front());
      for(int i=0; i< tmp.size(); i++){
          tmp[i] = indexes[tmp[i]];
        }
      ret.push_back(tmp);
      tmp = toExpore.front();
      toExpore.pop_front();
      int start = tmp.back();
      int sum = sums.front();
      sums.pop_front();
      for(int i = start+1; i<indexes.size(); i++){
          if(sum + potental.neededby[indexes[i]].getCourses()[potental.index[indexes[i]]].seats <= maxSize){
              vector<int> add = vector<int>(tmp);
              add.push_back(i);
              sums.push_back(sum + potental.neededby[indexes[i]].getCourses()[potental.index[indexes[i]]].seats);
              toExpore.push_back(add);
            }
        }
    }
  return ret;
}

/**
 * @brief heapsAlgorithm
 * @param indexes
 * @param len
 * @param toAddTo
 */
void heapsAlgorithm(vector<int> indexes, int len, vector<vector<int>>* toAddTo)
{
    
    if(len == 1){
        toAddTo->push_back(indexes);
    }

    for (int i=0; i<len; i++) 
    {
        heapsAlgorithm(indexes, len-1, toAddTo);

        if (len%2==1){
            int tmp = indexes[0];
            indexes[0] = indexes[len-1];
            indexes[len-1] = tmp;
        }

        else{
            int tmp = indexes[i];
            indexes[i] = indexes[len-1];
            indexes[len-1] = tmp;
        }
    }
}

vector<vector<enrollment>> generateWorkingCombos(vector<vector<enrollment>> posibilities, int cohorts)
{
  vector<vector<enrollment>> ret;
  for(int i = 0; i<posibilities.size(); i++)
    {
      for(int j = 0; j < posibilities[i].size(); j++)
        {
          if(posibilities[i][j].cohorts.size()==cohorts)
            {
              vector<enrollment> tmp;
              tmp.push_back(posibilities[i][j]);
              ret.push_back(tmp);
            }
        }
    }
  for(int i = 2; i<= posibilities.size(); i++){
      vector<vector<int>> indexes = comboIndexes(posibilities.size(),i);
      for(int j = 0; j< indexes.size(); j++){
          vector<int> tmp;
          vector<vector<int>> trial;
          for(int k = 0; k<i; k++){
              tmp.push_back(posibilities[indexes[j][k]].size());
            }
          trial = indexCombos(tmp);
          vector<enrollment> trialVec;
          int sum;
          for(int k = 0; k<trial.size(); k++)
            {
              sum = 0;
              trialVec.clear();

              for(int l = 0; l<i; l++){
                  sum += posibilities[indexes[j][l]][trial[k][l]].cohorts.size();
                  trialVec.push_back(posibilities[indexes[j][l]][trial[k][l]]);

                }

              if(sum == cohorts && enrollment::testEnrolement(trialVec,cohorts))
                ret.push_back(trialVec);

            }
        }
    }
  return ret;
}

/**
 * @brief indexCombos
 * @param lengths
 * @return vector of int vectors
 */
vector<vector<int>> indexCombos(vector<int> lengths)
{
  vector<vector<int>> ret;
  for(int i = 0; i<lengths[0]; i++)
    {
      vector<int> tmp;
      tmp.push_back(i);
      ret.push_back(tmp);
    }
  for(int i = 1; i<lengths.size(); i++)
    {
      int beforeLen = ret.size();
      for(int j = 0; j< beforeLen; j++){
          for(int k = 0; k<lengths[i]-1; k++)
            {
              vector<int> tmp = vector<int>(ret[j]);
              tmp.push_back(k);
              ret.push_back(tmp);
            }
          ret[j].push_back(lengths[i]-1);
        }
    }
  return ret;
}

/**
 * @brief comboIndexes
 * @param end
 * @param len
 * @return
 */
vector<vector<int>> comboIndexes(int end, int len)
{
    vector<vector<int>> combos = vector<vector<int>>();
    for(int i = 0; i<=end-len; i++)
      {
        vector<int> tmp = vector<int>();
        tmp.push_back(i);
        combos.push_back(tmp);
      }
    int iter = 1;
    int combosPreSize;
    while(iter!=len){
        combosPreSize = combos.size();
        for(int i = 0; i<combosPreSize; i++){
            for(int j = combos[i].back()+1; j<end-len+iter; j++){
                vector<int> tmp = vector<int>(combos[i]);
                tmp.push_back(j);
                combos.push_back(tmp);
              }
            combos[i].push_back(end-len+iter);
          }
        iter++;
      }
    return combos;
} 
