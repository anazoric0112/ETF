#ifndef _stablo_h_
#define _stablo_h_

#include <vector>
#include <string>
#include <math.h>
#include "stablo.h"
#include <iostream>
#include <fstream>
#include <queue>
#include <stack>
#include <set>
#include <map>
#include <algorithm>
#include <stdlib.h>
#include <iterator>

using namespace std;

class Stablo {
    typedef struct Cvor {
        int maxc,minc; //maxc max sinova, minc min sinova
        vector <string> reci;
        vector <Cvor*> sinovi;
        Cvor* otac;
    };
    
    int m, maxk, min;
    Cvor* koren;

    void copy(const Stablo&);
    void move(Stablo&);
    void dlt();

public:
    Stablo(int red) :m(red), koren(nullptr) {
        maxk = 2 * floor((2 * m - 2) / 3) + 1;
        min = ceil((2 * m - 1) / 3.);
    }
    Stablo(const Stablo&);
    Stablo(Stablo&&);
    ~Stablo();
    void op() { cout << m << " " << maxk << " " << min; }
    Stablo& operator=(const Stablo&);
    Stablo& operator=(Stablo&&);

    bool dodajRec(string rec);
    bool nadjiRec(string rec);
    bool obrisiRec(string rec);
    int reciIspred(string rec);
    friend ostream& operator<<(ostream& os, const Stablo& s);
    friend void assignV(Cvor* c, Cvor* otac, int mxc, int mnc);
};

#endif
