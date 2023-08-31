#include "Olimpijada.h"
#include <concurrent_priority_queue.h>
#include <queue>

void Olimpijada::dodajDisc(Disciplina* d)
{
	if (!d) throw GDisciplina();
	discipline.insert(d);
}

void Olimpijada::dodajDrz(Drzava* d)
{
	if (!d) throw GDrzava();
	drzave.insert(d);
}

void Olimpijada::dodajSp(Sportista* s)
{
	if (!s) throw GSportista();
	sportisti.insert(s);
}

void Olimpijada::dodajMed(Drzava* d, const Medalja& m)
{
	if (!d) throw GDrzava();
	if (!medalje[m][d]) medalje[m][d] = 1;
	else medalje[m][d]++;
	drzaveSaMedaljom.insert(d);
}

void Olimpijada::najboljeDrzave() const
{
	Drzava* d1=nullptr, * d2=nullptr, * d3=nullptr;
	int mz1 = 0, ms1 = 0, mb1 = 0; //medalje 1. drzave
	int mz2 = 0, ms2 = 0, mb2 = 0; //medalje 2. drzave
	int mz3 = 0, ms3 = 0, mb3 = 0; //medalje 3. drzave
	Medalja z = Medalja("Gold", false);
	Medalja s = Medalja("Silver", false);
	Medalja b = Medalja("Bronze", false);
	for (auto d : drzaveSaMedaljom) {
		int z0 = 0, s0 = 0, b0 = 0;
		if (medalje.find(z)->second.find(d)!= medalje.find(z)->second.end())
			z0 = (medalje.find(z)->second.find(d)->second);
		if (medalje.find(s)->second.find(d) != medalje.find(s)->second.end())
			s0 = (medalje.find(s)->second.find(d)->second);
		if (medalje.find(b)->second.find(d) != medalje.find(b)->second.end())
			b0 = (medalje.find(b)->second.find(d)->second);

		if (!d1) { d1 = d; mz1 = z0; ms1 = s0; mb1 = b0;  continue; }

		if ((z0 > mz1) || (z0==mz1 && s0>ms1) 
			|| (z0==mz1 && s0==ms1 && b0>mb1)) {
			mz3 = mz2; ms3 = ms2; mb3 = mb2;
			mz2 = mz1; ms2 = ms1; mb2 = mb1;
			mz1 = z0; ms1 = s0; mb1 = b0; 
			d3 = d2; d2 = d1; d1 = d;
		}
		else if ((z0 > mz2) || (z0 == mz2 && s0 > ms2)
			|| (z0 == mz2 && s0 == ms2 && b0 > mb2)
			|| (z0 == mz1 && s0 == ms1 && b0 == mb1)){
			mz3 = mz2; ms3 = ms2; mb3 = mb2;
			mz2 = z0; ms2 = s0; mb2 = b0;
			d3 = d2; d2 = d;
		} else if ((z0 > mz3) || (z0 == mz3 && s0 > ms3)
			|| (z0 == mz3 && s0 == ms3 && b0 > mb3)
			|| (z0 == mz2 && s0 == ms2 && b0 == mb2)) {
			mz3 = z0; ms3 = s0; mb3 = b0;
			d3 = d;
		}
	}

	if (d1) cout << "1. mesto: " << setw(30) << left << d1->dohvNaziv() << " " << mz1 << " " << ms1 << " " << mb1 << endl;
	if (d2) cout << "2. mesto: " << setw(30) << left << d2->dohvNaziv() << " " << mz2 << " " << ms2 << " " << mb2 << endl;
	if (d3) cout << "3. mesto: " << setw(30) << left << d3->dohvNaziv() << " " << mz3 << " " << ms3 << " " << mb3 << endl;
}

Drzava* Olimpijada::najboljaDrzava() const
{
	Drzava* d1 = nullptr;
	int mz1 = 0, ms1 = 0, mb1 = 0; //medalje 1. drzave
	Medalja z = Medalja("Gold", false);
	Medalja s = Medalja("Silver", false);
	Medalja b = Medalja("Bronze", false);
	for (auto d : drzaveSaMedaljom) {
		int z0 = -2, s0 = -2, b0 = -2;
		if (medalje.find(z)->second.find(d) != medalje.find(z)->second.end())
			z0 = (medalje.find(z)->second.find(d)->second);
		if (medalje.find(s)->second.find(d) != medalje.find(s)->second.end())
			s0 = (medalje.find(s)->second.find(d)->second);
		if (medalje.find(b)->second.find(d) != medalje.find(b)->second.end())
			b0 = (medalje.find(b)->second.find(d)->second);

		if (!d1) { d1 = d; mz1 = z0; ms1 = s0; mb1 = b0;  continue; }

		if ((z0 > mz1) || (z0 == mz1 && s0 > ms1)
			|| (z0 == mz1 && s0 == ms1 && b0 > mb1)) {
			mz1 = z0; ms1 = s0; mb1 = b0;
			d1 = d;
		}
	} return d1;
}

void Olimpijada::presek(Olimpijada* o) const
{
	//implementiraj iterator kroz ovo cudo sa sportistima
	auto it1 = this->begin();
	for (auto it2 = (*o).begin(); it2 != (*o).end() && it1!=this->end();) {
		if ((*it1)->dohvId() == (*it2)->dohvId()) {
			cout << **it1; ++it1; ++it2;
		}
		else if ((*it1)->dohvId() > (*it2)->dohvId()) ++it2;
		else if ((*it1)->dohvId() < (*it2)->dohvId()) ++it1;
	}
}
