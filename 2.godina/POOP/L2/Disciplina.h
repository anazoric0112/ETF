#pragma once

#include "Sport.h"
#include <set>

class Disciplina
{
private:
	Sport sport;
	string naziv;
	set <int> godine;
	bool timska;
	bool indiv;
public:

	Disciplina(Sport s, string n, bool t, bool i) :sport(s), naziv(n), timska(t), indiv(i) {}

	Sport dohvSport() const { return sport; }
	string dohvNaziv() const { return naziv; }
	bool jelTimska() const { return timska; }
	bool jelIndiv() const { return indiv; }

	void dodajGodinu(int godina);
	void postaviT() { timska = true; }
	void postaviI() { indiv = true; }

	auto begin() const { return godine.begin(); }
	auto end() const { return godine.end(); }
	auto nadjiGodinu(int g) const { return godine.find(g); }
	bool imaLiGodine(int g) const;

	friend bool operator<(const Disciplina& d1, const Disciplina& d2);
	friend bool operator>(const Disciplina& d1, const Disciplina& d2);
	friend bool operator==(const Disciplina& d1, const Disciplina& d2);
	friend bool operator!=(const Disciplina& d1, const Disciplina& d2);
	friend bool operator<=(const Disciplina& d1, const Disciplina& d2);
};

