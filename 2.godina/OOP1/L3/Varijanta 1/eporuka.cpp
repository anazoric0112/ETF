#include "eporuka.h"

//EPoruka* EPoruka::kopija() const {
//	return new EPoruka(*this);
//}
//void EPoruka::posalji() {
//	stanje = POSLATA;
//}

void EPoruka::ispisi(ostream& os) const {
	os << "Naslov: " << naslov << endl;
	os << "Posiljalac: " << *posiljalac << endl;
	os << "Primalac: " << *primalac;
}

ostream& operator<<(ostream& os, const EPoruka& ep) {
	ep.ispisi(os);
	return os;
}