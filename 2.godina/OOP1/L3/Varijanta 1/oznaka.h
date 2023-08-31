#ifndef _oznaka_h_
#define _oznaka_h_

#include <iostream>
using namespace std;


class VrOznaka {
	int dan, mesec, godina, sat, minut;
public:
	VrOznaka(int d, int m, int g, int s, int min) {
		dan = d; mesec = m; godina = g; sat = s; minut = min;
	}
	friend ostream& operator<<(ostream& os, const VrOznaka&);
};

#endif