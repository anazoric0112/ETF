#pragma once
#include "Disciplina.h"
#include "Sportista.h"

class Tim
{
private:
	map <int, Sportista*> sportisti;
	Disciplina* disciplina;
	bool tim;
public:
	Tim(Disciplina* d, bool t) :disciplina(d), tim(t) {}

	bool jeLiTim() const { return tim; }
	Disciplina* dohvDisc() const { return disciplina; }
	int brojIgraca() const { return sportisti.size(); }

	bool imaLiSportista(int id) const;
	void dodajSportistu(Sportista* sp);

};

