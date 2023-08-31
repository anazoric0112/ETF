#ifndef _greske_h_
#define _greske_h_

#include <exception>
using namespace std;

class GTackaPostoji: public exception {
public:
	GTackaPostoji() : exception("Greska: Data tacka vec postoji u putu\n") {}
};

class GNemaPodatka : public exception {
public:
	GNemaPodatka() :exception("Greska: Nema podatka na toj poziciji u listi\n") {}
};

class GNevalidanIndeks :public exception {
public:
	GNevalidanIndeks() :exception("Greska: Nevalidan indeks\n") {}
};

#endif

