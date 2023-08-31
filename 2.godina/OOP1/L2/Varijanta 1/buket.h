#ifndef _buket_h_
#define _buket_h_

#include "cvet.h"

class Buket{
	
	typedef struct Cv{
		Cvet* cvet;
		Cv* next;
	};
	Cv* head;

	void copy(const Buket& b);
	void move(Buket& b);
	void dlt();
	

public:
	
	Buket() :head(nullptr) {}
	Buket(const Buket&);
	Buket(Buket&&);
	~Buket();

	void dodajCvet(const Cvet &c);

	int nabavnaCena()const;
	int prodajnaCena()const;
	int zarada()const;

	friend bool operator>(Buket &b1, Buket &b2);
	friend bool operator<(Buket& b1, Buket& b2);
	friend bool operator==(Buket& b1, Buket& b2);
	friend bool operator!=(Buket& b1, Buket& b2);

	friend ostream& operator<<(ostream& os, const Buket& b);
	Buket& operator=(const Buket& b);
	Buket& operator=(Buket&& b);

};


#endif