#ifndef _posiljka_h_
#define _posiljka_h_

#include "Prodavac.h"

#include <iostream>
using namespace std;

class Posiljka {
	friend class Rukovalac;
	friend class  Prodavac;

	Artikal artikal;
	static int id;
	int ident;
	Lista <Rukovalac*> listaR;
	typedef struct Detalji {
		double cena;
		int daniCekanja;
		Detalji() :cena(0), daniCekanja(0) {}
	};
	
	Detalji detalji;
	bool izr = false;
public:
	Posiljka(const Artikal& a) : artikal(a),listaR() {
		ident = ++id;
	}

	const Artikal& dohvArtikal() const { return artikal; }
	int dohvId() const { return ident; }
	int dohvDaneCekanja() const;
	double dohvCenu() const;
	Detalji dohvDetalje() const;

	Posiljka& operator+=(const Rukovalac& r);

	void izracunajDetalje();

	friend ostream& operator<<(ostream& os, const Posiljka& p);
};

#endif