#include "Tim.h"

bool Tim::imaLiSportista(int id) const
{
    if (sportisti.find(id) != sportisti.end()) return true;
    else return false;
}

void Tim::dodajSportistu(Sportista* sp)
{
    if (!imaLiSportista(sp->dohvId())) {
        sportisti[sp->dohvId()] = sp;
    }
}
