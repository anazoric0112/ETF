#ifndef _put_h_
#define _put_h_

#include "Lista.h"
#include "Tacka.h"

class Put {
	Lista <Tacka> listaTacaka;
	double duz;
public:
	Put():duz(0),listaTacaka() {}

	Put& operator+=(const Tacka& t);
	double duzina() const { return duz; }
	friend ostream& operator<<(ostream& os, const Put& p);
};

#endif

