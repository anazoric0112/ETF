#pragma once
#include "Olimpijada.h"
#include <map>
#include <regex>
#include <fstream>
#include <functional>

class Olimpijade
{
private:
	map <string, Drzava*> drzave;
	map <int, Sportista*> sportisti;
	set <string> gradovi;
	map <string, Olimpijada*> olimpijade;
	map <string, string> olimpijadeGde; //god+doba - grad
	map <string, Disciplina*> discipline;
public:

	Olimpijade(){}
	~Olimpijade();

	bool imaLiDrzava(string d) const;
	bool imaLiSportista(int id) const;
	bool imaLiOlimpijada(string doba, int godina) const;
	bool imaLiOlimpijada(string kada) const;
	bool imaLiDisciplina(string d) const;

	void dodajDrzavu(string d);
	void dodajSportistu(int i, int p, Sport& s, Drzava* dr);
	void postaviDetaljeSportisti(int id, string v, string t, string g, char p, string i);
	void dodajGrad(string g);
	void dodajOlimpijadu(string doba, int godina, string grad);
	void dodajDisciplinu(string d, bool t, Sport sport);

	void dodajGodinuDisciplini(int godina, string disc);
	void dodajGodinuSportisti(int godina, int id);
	void dodajMedaljuSportisti(int godina, Medalja* m,int id);
	void dodajMedaljuDrzavi(Sport sport, string drzava, Medalja* m);
	void dodajTimDrzavi(Tim* tim, string drzava, int godina, string doba);
	void dodajDisciplinuOlimpijadi(string doba, int godina, string disciplina);
	void dodajSportistuOlimpijadi(string kada, int id);
	void dodajDrzavuOlimpijadi(string kada, string drzava);
	void dodajMedaljuOlimpijadi(string kada, string drzava, Medalja* m);

	vector<function<bool(Sportista*)>> filtrirajIspisSportista(bool u[4],
		Sport* sport=nullptr, Drzava* drzava=nullptr, int godina=0, Medalja* medalja=nullptr) const ;
	vector <function<bool(Disciplina*)>> filtrirajIspisDisciplina(bool u[3], bool tim,
		Sport* sport = nullptr, int godina = 0) const;

	int brojUcesnika(bool u[4],
		Sport* sport = nullptr, Drzava* drzava = nullptr, int godina = 0, Medalja* medalja = nullptr) const;
	double prosecnaTezina(bool u[4],
		Sport* sport = nullptr, Drzava* drzava = nullptr, int godina = 0, Medalja* medalja = nullptr) const;
	double prosecnaVisina(bool u[4],
		Sport* sport = nullptr, Drzava* drzava = nullptr, int godina = 0, Medalja* medalja = nullptr) const;
	int brojDisciplina(bool u[3], bool tim,
		Sport* sport = nullptr, int godina = 0) const;

	Olimpijada* dohvatiOlimpijadu(string doba, int godina) const;
	Olimpijada* dohvatiOlimpijadu(string kada) const;
	Drzava* dohvatiDrzavu(string d) const;
	void ispisiSveGradove() const;
	void presekSportista2Ol(string kada1, string kada2) const;
	void drzaveSaNajboljimUspehom() const;
	void najmladjiOsvajaci() const;
	void paroviDrzavaSportista() const;

	void ucitajFajlove(string fajl1, string fajl2, int zadataGodina=0);
};

