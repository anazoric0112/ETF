#include "Drzava.h"

bool operator<(const Drzava& d1, const Drzava& d2)
{
    return d1.dohvNaziv() < d2.dohvNaziv();
}
bool operator>(const Drzava& d1, const Drzava& d2)
{
    return d1.dohvNaziv() > d2.dohvNaziv();
}
bool operator==(const Drzava& d1, const Drzava& d2)
{
    return d1.dohvNaziv() == d2.dohvNaziv();
}
bool operator!=(const Drzava& d1, const Drzava& d2)
{
    return d1.dohvNaziv() != d2.dohvNaziv();
}

void Drzava::dodajMedalju(const Medalja& m, const Sport& s)
{
    medalje[s].push_back(m);
}

void Drzava::dodajTim(Tim* t, int god, string doba)
{
    if (doba != "Summer" && doba != "Winter") throw GDoba();
    string kljuc = to_string(god) + " " + doba;
    timovi[kljuc].insert(t);
}

void Drzava::timoviSaOlimpijade(string kada) const
{
    if (timovi.find(kada) == timovi.end()) throw GGodina();
    for (auto tim : timovi.find(kada)->second) {
        if (!tim->jeLiTim()) continue;
        cout << "Broj igraca: " << tim->brojIgraca() << ", disciplina: " << tim->dohvDisc()->dohvNaziv() << endl;
    }
}
