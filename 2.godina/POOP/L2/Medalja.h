#pragma once
#include <string>
using namespace std;

class Medalja
{
private:
	int mesto;
	bool tim;
public:
	Medalja(string m, bool t);

	int dohvMesto() const { return mesto; }
	bool jelTim() const { return tim; }

	friend bool operator<(const Medalja& m1, const Medalja& m2);
	friend bool operator>(const Medalja& m1, const Medalja& m2);
	friend bool operator==(const Medalja& m1, const Medalja& m2);
	friend bool operator!=(const Medalja& m1, const Medalja& m2);
};

