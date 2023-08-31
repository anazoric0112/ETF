#pragma once
#include "Drzava.h"
#include <iostream>
#include <iomanip>
#include "greske.h"
#include <functional>

class Olimpijada
{
private:
	

	struct uporediDisc {
		bool operator() (const Disciplina* d1, const Disciplina* d2) const {
			return *d1 < *d2;
		}
	};
	struct uporediSp {
		bool operator() (const Sportista* s1, const Sportista* s2) const {
			return *s1 < *s2;
		}
	};
	struct uporediDr {
		bool operator() (const Drzava* d1, const Drzava* d2) const {
			return *d1 < *d2;
		}
	};

	int godina;
	string doba;
	set < Disciplina*, uporediDisc > discipline;
	set <Sportista*, uporediSp> sportisti;
	set <Drzava*, uporediDr> drzave;
	set <Drzava*, uporediDr> drzaveSaMedaljom;
	map <Medalja, map<Drzava*, int, uporediDr>> medalje;

public:
	Olimpijada(int g, string d) :godina(g), doba(d) {}

	int dohvGod() const { return godina; }
	string dohvDoba() const { return doba;}
	string dohvKada() const { return to_string(godina) + " " + doba; }

	void dodajDisc(Disciplina*);
	void dodajDrz(Drzava* d);
	void dodajSp(Sportista* s);
	void dodajMed(Drzava* d, const Medalja& m);
	auto begin() const { return sportisti.begin(); }
	auto end() const { return sportisti.end(); }

	void najboljeDrzave() const;
	Drzava* najboljaDrzava() const;
	void presek(Olimpijada* o) const;

	//bool operator()(Drzava* d1, Drzava* d2) const;
	//bool uporediM(Drzava* d1, Drzava* d2) const;
};

