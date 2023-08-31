#ifndef _eptekst_h_
#define _eptekst_h_

#include "eporuka.h"
#include "greske.h"

class EPorukaTekst: public EPoruka {
	string tekst="";
	void ispisi(ostream& os) const override;
public:
	EPorukaTekst(const Korisnik& pos, const Korisnik& prim, string nas) :
		EPoruka(pos, prim, nas) {}
	void postaviTekst(string t) { 
		if (stanje != U_PRIPREMI) throw GPorukaJePoslata();
		tekst = t; 
	}

	EPorukaTekst* kopija() const override;
	void posalji() override;
	friend ostream& operator<<(ostream& os, const EPorukaTekst& ept);
};


#endif