#ifndef _prodavac_h_
#define _prodavac_h_

#include "Artikal.h"
#include "Lista.h"
#include "Rukovalac.h"
#include "Posiljka.h"
#include <string>
using namespace std;

class Prodavac: public Rukovalac {
	typedef struct Trojka {
		Artikal artikal;
		double marza;
		int brDana;
		Trojka(const Artikal& a, double m, int b) {
			artikal = a; marza = m; brDana = b;
		}
	};
	Lista <Trojka> katalog;
	string naziv;
public:
	friend class Posiljka;
	Prodavac(string n) :naziv(n) {}

	void prosiriKatalog(const Artikal& art, double mar, int brd);

	string dohvNaziv()const { return naziv; }

	void obradi(Posiljka& p)const override;
};

#endif

