#pragma once

#include <exception>
using namespace std;

class GIndeks : public exception {
public:
	GIndeks() :exception("Greska: Nevalidan indeks\n") {}
};

class GNemaIgraca : public exception {
public:
	GNemaIgraca() :exception("Greska: Nema igraca na toj poziciji u timu\n") {}
};

class GPremalaVrednost :public exception {
public:
	GPremalaVrednost() :exception("Greska: Igrac nema dovoljnu vrednost za ovaj tim\n") {}
};

class GMecNijeOdigran :public exception {
public:
	GMecNijeOdigran() :exception("Greska: Mec jos uvek nije odigran\n") {}
};