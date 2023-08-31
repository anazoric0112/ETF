#include "Posiljka.h"

int Posiljka::id = 0;

int Posiljka::dohvDaneCekanja() const {
	if (!izr) throw GNisuIzracunatiDetalji();
	return detalji.daniCekanja;
}

double Posiljka::dohvCenu() const {
	if (!izr) throw GNisuIzracunatiDetalji();
	return detalji.cena;
}

Posiljka::Detalji Posiljka::dohvDetalje() const {
	if (!izr) throw GNisuIzracunatiDetalji();
	return detalji;
}

Posiljka& Posiljka::operator+=(const Rukovalac& r)
{
	if (izr) throw GDetaljiIzracunati();
	listaR += &(const_cast<Rukovalac&>(r));
}

void Posiljka::izracunajDetalje()
{	
	izr = true;
	for (int i = 0; i < listaR.brPodataka(); i++) {
		(*listaR[i]).obradi(*this);
	}
}

ostream& operator<<(ostream& os, const Posiljka& p)
{
	return os << "Posiljka[" << p.ident << ", " << p.artikal.dohvNaziv() << "]";
}
