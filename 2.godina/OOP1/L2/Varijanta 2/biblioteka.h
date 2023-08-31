#ifndef _biblioteka_h_
#define _biblioteka_h_

#include "knjiga.h"
#include <string>
using namespace std;

class Biblioteka {
	string naziv;
	int brojKnjiga=0;
	int maxKnjiga;
	Knjiga** data;
	void copy(const Biblioteka& k);
	void move(Biblioteka& k);
	void dlt();

public:
	Biblioteka(string n="", int mbr=0) :naziv(n), maxKnjiga(mbr) {
		data = new Knjiga* [maxKnjiga];
	}
	Biblioteka(const Biblioteka&);
	Biblioteka(Biblioteka&&);
	~Biblioteka();

	string Naziv()const{ return naziv; }
	int brojK()const { return brojKnjiga; }
	int maxK()const { return maxKnjiga; }

	Knjiga* dohvatiKnjigu(int idk) const;

	Biblioteka& operator+=(const Knjiga& k);
	friend ostream& operator<<(ostream& os, const Biblioteka& b);
	Biblioteka& operator=(const Biblioteka& b);
	Biblioteka& operator=(Biblioteka&& b);

};

#endif