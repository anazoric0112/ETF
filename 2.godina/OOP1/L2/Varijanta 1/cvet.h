#ifndef _cvet_h_
#define _cvet_h_

#include <string>
using namespace std;

class Cvet {
	int pr;
	int nab;
	string ime;
public:
	Cvet(int p=0, int n=0, string nz="") :pr(p), nab(n), ime(nz) {}
	Cvet(const Cvet&) = default;
	Cvet(Cvet&&) = default;

	int prodajnaCena() const { return pr; }
	int nabavnaCena() const { return nab; }
	string naziv() const{ return ime;}
	int zarada() const;

	friend bool operator==(Cvet &a,Cvet &b);
	friend ostream& operator<<(ostream& os, const Cvet& c);
	Cvet& operator=(const Cvet&) = default;
	Cvet& operator=(Cvet&&) = default;
	
};

#endif
