#ifndef _knjiga_h_
#define _knjiga_h_

#include <string>
#include <iostream>
using namespace std;

class Knjiga {
	string naziv;
	string autor;
	static int brojac;
	int id;
	
public:
	Knjiga(string n, string a) :naziv(n), autor(a) {
		brojac++; id = brojac; 
	}
	Knjiga(const Knjiga&) = delete;
	Knjiga(Knjiga&&) = delete;
	
	string Naziv()const { return naziv; }
	string Autor()const { return autor; }
	int Id()const { return id; }

	Knjiga* operator!()const;
	friend ostream& operator<<(ostream& os, const Knjiga& k);

	Knjiga& operator=(const Knjiga& k) = delete;
	Knjiga& operator=(Knjiga&& k) = delete;

};


#endif