#ifndef _eporuka_h_
#define _eporuka_h_

#include "korisnik.h"
using namespace std;

class EPoruka {
public:
	enum Stanje { U_PRIPREMI, POSLATA, PRIMLJENA };
protected: 
	Korisnik* posiljalac;
	Korisnik* primalac;
	string naslov;
	Stanje stanje;

	virtual void ispisi(ostream& os) const;
public:
	EPoruka(const Korisnik& pos,const Korisnik& prim, string nasl) :stanje(U_PRIMPREMI){
		posiljalac = &(const_cast<Korisnik &>(pos));
		primalac = &(const_cast<Korisnik&>(prim));
		naslov = nasl;
	}
	
	virtual ~EPoruka() = default;

	const Korisnik& dohvPosiljaoca() const { return *posiljalac; }
	const Korisnik& dohvPrimaoca() const { return *primalac; }
	string dohvNaslov() const { return naslov; }
	Stanje dohvStanje() const { return stanje; }

	virtual EPoruka* kopija() const = 0;
	virtual void posalji() = 0;
	friend ostream& operator<<(ostream& os, const EPoruka& ep);
};


#endif
