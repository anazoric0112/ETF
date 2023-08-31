#pragma once

#include "Igrac.h"

class Tim
{
protected:
	string naziv;
	int maxIgr, br = 0;
	double vr = 0;
	Igrac** igraci;

	void kopiraj(const Tim& t);
	void premesti(Tim& t);
	void brisi();

	virtual void ispisi(ostream& os) const;
public:
	Tim(int m, string n) :maxIgr(m), naziv(n) {
		igraci = new Igrac*[m];
		for (int i = 0; i < m; igraci[i++] = nullptr);
	}
	Tim(const Tim& t) { kopiraj(t); }
	Tim(Tim&& t) { premesti(t); }
	~Tim() { brisi(); }
	Tim& operator=(const Tim& t);
	Tim& operator=(Tim&& t);

	virtual void prikljuci(int i, const Igrac& igr);
	int brojIgraca() const { return br; }
	double vrednost() const { return vr; }
	int dohvMax() const { return maxIgr; }
	bool imaNaPoz(int i) const { return igraci[i] != nullptr; }

	Igrac& operator[](int i);
	const Igrac& operator[] (int i) const;

	bool operator==(const Tim& t) const;
	friend ostream& operator<<(ostream& os, const Tim& t);


};

