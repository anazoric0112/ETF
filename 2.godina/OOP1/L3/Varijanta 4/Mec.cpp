#include "Mec.h"
#include "greske.h"

void Mec::odigraj()
{	
	Tim& d = *domgost.dohvPod1();
	Tim& g = *domgost.dohvPod2();
	if (d.vrednost() > g.vrednost()) {
		ishod = POBEDA_DOMACIN;
		for (int i = 0; i < d.dohvMax(); i++) {
			if (d.imaNaPoz(i)) d[i].povecajVr(10);
		}
		for (int i = 0; i < g.dohvMax(); i++) {
			if (g.imaNaPoz(i)) g[i].smanjiVr(10);
		}
	}
	else if (d.vrednost() < g.vrednost()) {
		ishod = POBEDA_GOST;
		for (int i = 0; i < d.dohvMax(); i++) {
			if (d.imaNaPoz(i)) d[i].smanjiVr(10);
		}
		for (int i = 0; i < g.dohvMax(); i++) {
			if (g.imaNaPoz(i)) g[i].povecajVr(10);
		}
	}
	else {
		ishod = NERESENO;
	}
	odigran = true;
}

Par<int, int> Mec::poeni() const
{	
	if (!odigran) throw GMecNijeOdigran();
	int p0 = 0, p1 = 1, p3 = 3;
	if (ishod == NERESENO) {
		return Par<int, int>(&p1, &p1);
	}
	else if (ishod == POBEDA_DOMACIN) {
		return Par<int, int>(&p3, &p0);
	}
	else {
		return Par<int, int>(&p0, &p3);
	}
}

ostream& operator<<(ostream& os, const Mec& m)
{
	os << m.domgost << " ";
	if (m.jeLiOdigran()) {
		if (m.ishod == Mec::Ishod::NERESENO) os << "Ishod: NERESENO";
		if (m.ishod == Mec::Ishod::POBEDA_DOMACIN) os << "Ishod: POBEDA_DOMACIN";
		if (m.ishod == Mec::Ishod::POBEDA_GOST) os << "Ishod: POBEDA_GOST";
	} return os;
}
