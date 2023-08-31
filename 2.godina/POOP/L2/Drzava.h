#pragma once
#include <string>
#include <set>
#include <map>
#include <vector>
#include "Medalja.h"
#include "Disciplina.h"
#include "Tim.h"
#include "greske.h"
using namespace std;

class Drzava
{
private:
	struct uporediT {
		bool operator()(const Tim* t1, const Tim* t2) const {
			if (t1->brojIgraca() > t2->brojIgraca()) return true;
			else if (t1->brojIgraca() < t2->brojIgraca()) return false;
			else if ((t1->dohvDisc())->dohvNaziv() < (t2->dohvDisc())->dohvNaziv()) return true;
			return false;
		}
	};

	map < Sport, vector<Medalja >> medalje;
	map <string, set<Tim*, uporediT>> timovi;

	string naziv;

public:
	Drzava(string n) :naziv(n) {}

	string dohvNaziv() const { return naziv; }

	void dodajMedalju(const Medalja& m, const Sport& s);
	void dodajTim(Tim* t, int god, string doba);

	int sportoviSaMedaljom() const { return medalje.size(); }
	void timoviSaOlimpijade(string kada) const;

	friend bool operator<(const Drzava& d1, const Drzava& d2);
	friend bool operator>(const Drzava& d1, const Drzava& d2);
	friend bool operator==(const Drzava& d1, const Drzava& d2);
	friend bool operator!=(const Drzava& d1, const Drzava& d2);

};

