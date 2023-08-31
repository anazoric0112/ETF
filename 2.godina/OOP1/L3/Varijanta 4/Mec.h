#pragma once

#include "par.h"
#include "PrivilegovaniTim.h"

class Mec
{
public:
	enum Ishod { POBEDA_DOMACIN, NERESENO, POBEDA_GOST };
private:
	Par <Tim, Tim> domgost;
	Ishod ishod;
	bool odigran = 0;
public:
	Mec(Tim& d, Tim& g) :domgost(&d, &g) {}

	Par <Tim, Tim> dohvTimove() const { return domgost; }
	void odigraj();
	bool jeLiOdigran() const { return odigran; }
	Par <int, int> poeni() const;

	friend ostream& operator<<(ostream& os, const Mec& m);
};

