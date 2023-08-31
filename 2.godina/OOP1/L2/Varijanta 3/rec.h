#ifndef _rec_h_
#define _rec_h_

#include "skup.h"
#include <string>
using namespace std;

class Rec {
	string rec;

public:
	Rec(string niska);
	Rec(const Rec&) = default;
	Rec(Rec&&) = default;
	~Rec() = default;

	int operator+(); //duzina reci
	int operator~(); //broj slogova
	int operator()(int n);

	friend bool operator^(Rec r1, Rec r2);
	friend istream& operator>>(istream& is, Rec& r);
	friend ostream& operator<<(ostream& os, const Rec& r);
	Rec& operator=(const Rec&) = default;
	Rec& operator=(Rec&&) = default;

};


#endif