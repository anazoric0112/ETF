#ifndef _cvecara_h_
#define _cvecara_h_

#include "buket.h"

class Cvecara {

	typedef struct Cv {
		Buket* b;
		Cv* next;
	};
	Cv* head;
	int zarada;

	void copy(const Cvecara& c);
	void move(Cvecara& c);
	void dlt();

public: 
	Cvecara(int z = 1000) :zarada(z), head(nullptr) {}
	Cvecara(const Cvecara&);
	Cvecara(Cvecara&&);
	~Cvecara();

	void dodajBuket(const Buket &b);
	void prodajBuket(int  poz);

	friend ostream& operator<<(ostream& os, const Cvecara& c);
	Cvecara& operator=(const Cvecara& c);
	Cvecara& operator=(Cvecara&& c);
};


#endif