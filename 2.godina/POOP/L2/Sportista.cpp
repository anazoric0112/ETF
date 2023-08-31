#include "Sportista.h"
#include "Drzava.h"
#include <iomanip>

Drzava Sportista::dohvDrzavu() const
{
	 return *drzava; 
}

bool Sportista::ucestvovaoGodine(int g) const
{
	if (godine.find(g) != godine.end()) return true;
	else return false;
}

bool Sportista::osvojioMedalju(const Medalja& m) const
{
	for (auto g : medalje) {
		for (auto medalja : g.second) {
			if (medalja == m) return true;
		}
	}return false;
}

void Sportista::postaviDetalje(string v, string t, string g, char p, string i) {
	if (g == "NA") god = -1;
	else  god = stoi(g);

	if (v == "NA") visina = -1;
	else  visina = stod(v);

	if (t == "NA") tezina = -1;
	else  tezina = stod(t);

	pol = p; ime = i;
	postavljeno = true;
}

void Sportista::azurirajPrvuGodinu(int g)
{
	if (g < prvaGod) prvaGod = g;
}

void Sportista::dodajGodinu(int g)
{
	godine.insert(g);
}

void Sportista::dodajMedalju(Medalja* med, int g)
{
	medalje[g].push_back(*med);
}

void Sportista::dodajDrzavu(Drzava* drzava)
{
	drzave.insert(drzava->dohvNaziv());
}

void Sportista::dodajSport(Sport& sport)
{
	sportovi.insert(sport.dohvNaziv());
}

int Sportista::kolikoGodina(int gOl) const
{
	return gOl - prvaGod + god;
}

bool Sportista::prvoUcesce() const
{
	if (medalje.find(prvaGod) != medalje.end()) return true;
	else return false;
}

bool Sportista::timIndiv() const
{
	bool tim = false, indiv = false;
	for (auto g : medalje) {
		for (auto medalja : g.second) {
			if (medalja.jelTim()) tim = true;
			else indiv = true;
			if (indiv && tim) return true;
		}
	}
	if (indiv && tim) return true;
	else return false;
}

bool operator<(const Sportista& s1, const Sportista& s2)
{
	return s1.dohvId() < s2.dohvId();
}
bool operator>(const Sportista& s1, const Sportista& s2)
{
	return s1.dohvId() > s2.dohvId();
}
bool operator==(const Sportista& s1, const Sportista& s2)
{
	return s1.dohvId() == s2.dohvId();
}
bool operator!=(const Sportista& s1, const Sportista& s2)
{
	return s1.dohvId() != s2.dohvId();
}

ostream& operator<<(ostream& os, const Sportista& sp)
{
	os << right << setw(10) << sp.id;
	if (!sp.detaljiPostavljeni()) return os << endl;
	else os << "\t";

	os << left << setw(100) << sp.ime;
	os << setw(3) << sp.pol;
	os << right << setw(5);
	if (sp.god == -1) os << "/";
	else os << sp.god;

	os << setw(5);
	if (sp.visina == -1) os << "/";
	else os << (int)sp.visina;

	os << setw(5);
	if (sp.tezina == -1) os << "/";
	else os << (int)sp.tezina;

	return os << endl;
}
