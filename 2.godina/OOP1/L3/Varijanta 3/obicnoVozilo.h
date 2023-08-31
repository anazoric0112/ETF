#pragma once

#include "Vozilo.h"

class ObicnoVozilo: public Vozilo
{
public:
	ObicnoVozilo(string n) :Vozilo(n) {}
	double startnaCena() const override { return 120; }
};

