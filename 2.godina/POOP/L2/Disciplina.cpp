#include "Disciplina.h"

void Disciplina::dodajGodinu(int godina)
{
	godine.insert(godina);
}

bool Disciplina::imaLiGodine(int g) const
{
	if (godine.find(g) != godine.end()) return true;
	else return false;
}

bool operator<(const Disciplina& d1, const Disciplina& d2)
{
	return d1.dohvNaziv() < d2.dohvNaziv();
}
bool operator>(const Disciplina& d1, const Disciplina& d2)
{
	return d1.dohvNaziv() > d2.dohvNaziv();
}
bool operator==(const Disciplina& d1, const Disciplina& d2)
{
	return d1.dohvNaziv() == d2.dohvNaziv();
}
bool operator!=(const Disciplina& d1, const Disciplina& d2)
{
	return d1.dohvNaziv() != d2.dohvNaziv();
}
bool operator<=(const Disciplina& d1, const Disciplina& d2)
{
	return d1.dohvNaziv() <= d2.dohvNaziv();
}