#include "Vozilo.h"

double Vozilo::cena(const Put& p) const
{
    return p.duzina() * 0.1 + startnaCena();
}

ostream& operator<<(ostream& os, const Vozilo& v)
{
    return os << v.nazivModela;
}
