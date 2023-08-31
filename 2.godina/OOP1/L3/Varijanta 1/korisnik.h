#ifndef _korisnik_h_
#define _korisnik_h_

#include <string>
#include <iostream>
using namespace std;

class Korisnik{
	string ime;
	string email;
public:
	Korisnik(string i, string e) :ime(i), email(e){}
	Korisnik(const Korisnik&) = delete;
	Korisnik& operator=(const Korisnik&) = delete;
	~Korisnik() = default;

	string dohvatiIme() const { return ime; }
	string dohvatiEmail() const { return email; }

	friend ostream& operator<<(ostream& os, const Korisnik& k);

};


#endif

