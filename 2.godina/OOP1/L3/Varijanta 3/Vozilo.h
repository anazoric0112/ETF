#ifndef _vozilo_h_
#define _vozilo_h_

#include <string>
#include "Put.h"
using namespace std;

class Vozilo{
	string nazivModela;
public:
	Vozilo(string n) : nazivModela(n) {}
	virtual ~Vozilo() = default;
	virtual double startnaCena() const = 0;
	double cena(const Put& p) const;

	friend ostream& operator<<(ostream& os, const  Vozilo& v);
};

#endif
