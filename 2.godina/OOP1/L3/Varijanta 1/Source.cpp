#include "lista.h"
#include "eptekst.h"
#include "oznaka.h"
#include <iostream>

using namespace std;


int main() {
	try {
		Korisnik k1("Ana Zoric","ana@gmail.com");
		Korisnik k2("Katarina Mitic","katam@yahoo.com");
		EPorukaTekst ep(k1, k2, "Nova poruka");
		cout << ep;
	}
	catch (exception e) {
		cout << e.what();
	}
	return 0;
}