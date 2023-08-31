#include "eptekst.h"

void EPorukaTekst::ispisi(ostream& os) const  {
	EPoruka::ispisi(os);
	os << endl << tekst;
}

EPorukaTekst* EPorukaTekst::kopija() const
{
	return new EPorukaTekst(*this);
}

void EPorukaTekst::posalji()
{
	stanje = POSLATA;
}


ostream& operator<<(ostream& os, const EPorukaTekst& ept) {
	ept.ispisi(os);
	return os;
}