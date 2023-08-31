#ifndef _greske_h_
#define _greske_h_

#include <exception>
using namespace std;

class GNemaTekuceg : public exception {
public:
	GNemaTekuceg() : exception("Greska: Nema tekuceg elementa\n") {}
};

class GPorukaJePoslata : public exception {
public:
	GPorukaJePoslata() : exception("Greska: Poruka je vec poslata\n") {}
};


#endif
