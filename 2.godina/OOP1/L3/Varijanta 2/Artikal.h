#ifndef _artikal_h_
#define _artikal_h_

#include <string>
using namespace std;

class Artikal{
	int barkod;
	double nabavnaCena;
	string naziv;
public:
	Artikal(int kod, double c, string naz) : barkod(kod), nabavnaCena(c), naziv(naz) {}
	Artikal() {} //?
	int dohvBarKod() const { return barkod; }
	double dohvCenu() const { return nabavnaCena; }
	string dohvNaziv() const { return naziv; }

	bool operator==(const Artikal& a) const;
};

#endif