#include "Put.h"

Put& Put::operator+=(const Tacka& t)
{   
    for (int i = 0; i < listaTacaka.brPodataka(); i++) {
        if (listaTacaka[i] == t) throw GTackaPostoji();
    }
    listaTacaka += t;
    if (listaTacaka.brPodataka() > 1) {
        int i = listaTacaka.brPodataka();
        duz += listaTacaka[i - 1].udaljenost(listaTacaka[i - 2]);
    }
    return *this;
}


ostream& operator<<(ostream& os, const Put& p)
{   
    if (!p.listaTacaka.brPodataka()) return  os;
    os<< p.listaTacaka[0];
    for (int i = 1; i < p.listaTacaka.brPodataka(); i++) {
        os << endl << p.listaTacaka[i];
    }
    return os;
}
