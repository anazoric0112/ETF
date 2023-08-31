#include "cvecara.h"
using namespace std;
#include <iostream>

Cvecara::Cvecara(const Cvecara& c) {
	copy(c);
}

Cvecara::Cvecara(Cvecara&& c) {
	move(c);
}

Cvecara::~Cvecara() {
	dlt();
}

void Cvecara::dodajBuket(const Buket &b) {
	if (b.zarada() < b.nabavnaCena()*0.2) return;
	zarada -= b.nabavnaCena();
	
	if (!head) {
		head = new Cv();
		head->b = &(const_cast<Buket&>(b));
		head->next = nullptr;
		return;
	}
	Cv* p = head;
	while (p->next) {
		if (p->next->b->prodajnaCena() > b.prodajnaCena()) {
			
			Cv* novi = new Cv;
			novi->b = &(const_cast<Buket&>(b));
			novi->next = p->next;
			p->next = novi;
			return;
		} p = p->next;
	} p->next = new Cv;
	p->next->b = &(const_cast<Buket&>(b));
	p->next->next = nullptr;
	if (p->b->prodajnaCena()>p->next->b->prodajnaCena()){
		swap(p->b, p->next->b);
	}
	
}

void Cvecara::prodajBuket(int  poz) {
	Cv* p = head;
	int i = 1;
	if (poz == 0) {
		zarada += head->b->prodajnaCena();
		head = head->next;
		delete p;
		return;
	}
	while (p && p->next) {
		if (i == poz) {
			Cv* t = p->next;
			p->next = t->next;
			zarada += t->b->prodajnaCena();
			delete t;
			return;
		}
		i++; p = p->next;
	} 
}

Cvecara& Cvecara::operator=(const Cvecara& c) {
	if (this == &c) {
		return *this;
	}
	dlt();
	copy(c);
	return *this;
}

Cvecara& Cvecara::operator=(Cvecara && c){
	if (this == &c) {
		return *this;
	}
	dlt();
	move(c);
	return *this;
}

ostream& operator<<(ostream& os, const Cvecara& c) {
	if (!c.head) return os;
	Cvecara::Cv* p = c.head;
	os <<"zarada="<< c.zarada << "RSD\n";
	while (p) {
		os << *(p->b) << endl;
		p = p->next;
	} return os << "";
}

void Cvecara::copy(const Cvecara& c) {
	Cv* tr = c.head;
	zarada = c.zarada;
	Cv* novi = nullptr, * posl = head;
	while (tr) {
		novi = new Cv(*tr);
		if (!head) head = novi;
		else posl->next = novi;
		posl = novi;
		tr = tr->next;
	}
}
void Cvecara::move(Cvecara& c ) {
	head = c.head;
	zarada = c.zarada;
	c.head = nullptr;
}
void Cvecara::dlt() {
	Cv* tr = head;
	while (tr) {
		Cv* trr = tr;
		tr = tr->next;
		delete trr;
	}
	head = nullptr;
}