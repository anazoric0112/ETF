#include "PrivilegovaniTim.h"
#include "greske.h"

void PrivilegovaniTim::ispisi(ostream& os) const
{
	Tim::ispisi(os);
	os << "(" << minVr << ")";
}

void PrivilegovaniTim::prikljuci(int i, const Igrac& igr)
{	
	if (igr.dohvVr() < minVr) throw GPremalaVrednost();
	Tim::prikljuci(i, igr);
}

ostream& operator<<(ostream& os, const PrivilegovaniTim& pt) {
	pt.ispisi(os);
	return os;
}