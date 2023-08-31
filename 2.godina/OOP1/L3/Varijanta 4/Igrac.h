#pragma once

#include <string>
#include <iostream>
using namespace std;

class Igrac
{
	string ime;
	int vr;
public:
	Igrac(string i, int v=1000) :vr(v), ime(i) {}

	void povecajVr(double procenat);
	void smanjiVr(double procenat);

	int dohvVr() const { return vr; }
	bool operator==(const Igrac& i) const;

	friend ostream& operator<<(ostream& os, const Igrac& i);
};

