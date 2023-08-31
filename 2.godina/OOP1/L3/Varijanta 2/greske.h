#ifndef _greske_h_
#define _greske_h_

#include <exception>
using namespace std;

class GNemaPodatka : public exception {
public:
	GNemaPodatka() :exception("Greska: Nema podatka na toj poziciji u listi\n") {}
};

class  GNisuIzracunatiDetalji : public exception {
public:
	GNisuIzracunatiDetalji() : exception("Greska: Detalji posiljke jos uvek nisu izracunati\n") {}
};

class GDetaljiIzracunati : public exception {
public:
	GDetaljiIzracunati() :exception("Greska: Detalji su vec izracunati\n") {}
};

#endif