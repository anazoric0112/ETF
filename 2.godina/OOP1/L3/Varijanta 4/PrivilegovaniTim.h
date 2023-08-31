#pragma once

#include "Tim.h"
#include <iostream>
using namespace std;

class PrivilegovaniTim: public Tim
{
	double minVr;
	void ispisi(ostream& os) const override;
public:
	PrivilegovaniTim(double mn, int mx, string n) :Tim(mx, n), minVr(mn) {}
	void prikljuci(int i, const Igrac& igr) override;

	friend ostream& operator<<(ostream& os, const PrivilegovaniTim& pt);
};

