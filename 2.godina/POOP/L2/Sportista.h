#pragma once
//#include "Drzava.h"
#include "Medalja.h"
#include "Sport.h"
#include <set>
#include <map>
#include <vector>
#include <iostream>
class Drzava;

class Sportista
{
private:

	double visina = -1, tezina = -1;
	int god = -1, id, prvaGod;
	string ime;
	char pol=' ';
	bool postavljeno=false;

	Drzava* drzava;
	set <string> drzave;
	Sport sport;
	set <string> sportovi;
	set <int> godine;
	//true - tim, false - individualni
	//<godina, <medalja,timski>>
	map <int, vector<Medalja>> medalje;

public:
	Sportista(int i, int p, Sport& s, Drzava* dr) :id(i), sport(s), drzava(dr), prvaGod(p) { dodajDrzavu(dr); dodajSport(s); }

	double dohvVisinu() const { return visina; }
	double dohvTezinu() const { return tezina; }
	int dohvGodine() const { return god; }
	int dohvId() const { return id; }
	int dohvPrvuGod() const { return prvaGod; }
	string dohvIme() const { return ime; }
	char dohvPol() const { return pol; }
	bool detaljiPostavljeni() const { return postavljeno; }
	Drzava dohvDrzavu() const;
	Sport dohvSport() const { return sport; }
	bool imaLiSport(string s) const { return sportovi.find(s) != sportovi.end(); }

	bool ucestvovaoGodine(int g) const;
	bool osvojioMedalju(const Medalja& m) const;

	void postaviDetalje(string v, string t, string g, char p, string i);
	void azurirajPrvuGodinu(int g);
	void dodajGodinu(int g);
	void dodajMedalju(Medalja* m, int g);
	void dodajDrzavu(Drzava* drzava);
	void dodajSport(Sport& sport);
	int kolikoGodina(int gOl) const;
	bool prvoUcesce() const;
	bool timIndiv() const;

	auto pocetakDr() const { return drzave.begin(); }
	auto krajDr() const { return drzave.end(); }

	friend bool operator<(const Sportista& s1, const Sportista& s2);
	friend bool operator>(const Sportista& s1, const Sportista& s2);
	friend bool operator==(const Sportista& s1, const Sportista& s2);
	friend bool operator!=(const Sportista& s1, const Sportista& s2);

	friend ostream& operator<< (ostream& os, const Sportista& sp);
};

