#pragma once

#include <exception>
#include <stdio.h>
using namespace std;

class GNemaFajla : public exception {
public:
	GNemaFajla() : exception("Greska: Nema fajla sa datim imenom\n") {}
};

class GSport : public exception {
public:
	GSport() : exception("Greska: Nepostojeci sport\n") {}
};

class GSportista : public exception {
public:
	GSportista() : exception("Greska: Nepostojeci sportista\n") {}
};

class GDrzava : public exception {
public:
	GDrzava() : exception("Greska: Nepostojeca drzava\n") {}
};

class GDisciplina : public exception {
public:
	GDisciplina() : exception("Greska: Nepostojeca disciplina\n") {}
};

class GBroj : public exception {
public:
	GBroj() : exception("Greska: nevalidan broj\n") {}
};

class GGodina : public exception {
public:
	GGodina() : exception("Greska: nevalidni ulazni podaci o trazenoj olimpijadi\n") {}
};

class GDoba : public exception {
public:
	GDoba() : exception("Greska: nevalidni ulazni podaci o trazenoj olimpijadi\n") {}
};

class GMedalja : public exception {
public:
	GMedalja() : exception("Greska: nevalidan opis medalje\n") {}
};
