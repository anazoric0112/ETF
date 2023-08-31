#include "knjiga.h"

int Knjiga::brojac = 0;

Knjiga* Knjiga::operator!()const {
	
	Knjiga* k = new Knjiga(naziv,autor);
	return k;
}
ostream& operator<<(ostream& os, const Knjiga& k) {
	return os << "KNJIGA "<<k.Id() << " : " << k.Naziv() << " - " << k.Autor() << endl;
}