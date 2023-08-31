#ifndef _tacka_h_
#define _tacka_h_

#include <iostream>
using namespace std;

class Tacka{
	int x, y;
public:
	Tacka(int xx, int yy) :x(xx), y(yy) {}
	//Tacka() :x(0), y(0) {}
	double udaljenost(const Tacka& t) const;
	bool operator==(const Tacka& t) const;
	friend ostream& operator<<(ostream& os, const Tacka& t);
};

#endif
