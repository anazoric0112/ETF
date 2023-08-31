#include "Tim.h"
#include "greske.h"

void Tim::kopiraj(const Tim& t)
{
	maxIgr = t.maxIgr;
	br = t.br;
	vr = t.vr;
	naziv = t.naziv;
	igraci = new Igrac*[maxIgr];
	for (int i = 0; i < br; i++) {
		igraci[i] = t.igraci[i];
	}
}

void Tim::premesti(Tim& t)
{
	maxIgr = t.maxIgr;
	br = t.br;
	vr = t.vr;
	naziv = t.naziv;
	igraci = t.igraci;
	t.igraci = nullptr;
}

void Tim::brisi()
{
	delete igraci;
}


Tim& Tim::operator=(const Tim& t)
{
	if (this != &t) {
		brisi(); kopiraj(t);
	} return *this;
}

Tim& Tim::operator=(Tim&& t)
{
	if (this != &t) {
		brisi(); premesti(t);
	} return *this;
}

void Tim::prikljuci(int i, const Igrac& igr)
{
	if (i<0 || i>=maxIgr) throw GIndeks();
	
	if (igraci[i] != nullptr) {
		vr=((vr * br) + igr.dohvVr() - (*igraci[i]).dohvVr()) / br;
	}
	else {
		br++;
		vr = ((vr * (br - 1)) + igr.dohvVr()) / br;
	}

	igraci[i] = &(const_cast <Igrac&>(igr));
}

Igrac& Tim::operator[](int i)
{	
	if (i<0 || i>=maxIgr) throw GIndeks();
	if (igraci[i] == nullptr) throw GNemaIgraca();
	return *igraci[i];
}

const Igrac& Tim::operator[](int i) const {
	return const_cast<Tim&>(*this)[i];
}


bool Tim::operator==(const Tim& t) const
{
	bool prvo = (maxIgr == t.maxIgr) && (br == t.br) && (vr == t.vr) && (naziv == t.naziv);
	if (!prvo) return prvo;
	for (int i = 0; i < t.maxIgr; i++) {
		if (igraci[i] && !t.igraci[i]) return false;
		else if (!igraci[i] && t.igraci[i]) return false;
		else if (!(*igraci[i] == *t.igraci[i])) return false;
	}
	return true;
}

ostream& operator<<(ostream& os, const Tim& t)
{
	t.ispisi(os);
	return os;
}

void Tim::ispisi(ostream& os) const
{
	os << naziv << "[";
	int b = 0;
	for (int i = 0; i < maxIgr; i++) {
		if (igraci[i]) {
			os << *igraci[i];
			b++;
			if (b < br) os << ",";
		}
	} os << "]";
}