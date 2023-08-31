#include "Prodavac.h"

void Prodavac::prosiriKatalog(const Artikal& art, double mar, int brd)
{	
	Trojka t = Trojka(art, mar, brd);
	katalog += t;
}


void Prodavac::obradi(Posiljka& p) const {
	for (int i = 0; i < katalog.brPodataka(); i++) {
		if (katalog[i].artikal == p.artikal) {
			p.detalji.daniCekanja += katalog[i].brDana;
			p.detalji.cena += katalog[i].marza * p.artikal.dohvCenu();
		}
	}
}
