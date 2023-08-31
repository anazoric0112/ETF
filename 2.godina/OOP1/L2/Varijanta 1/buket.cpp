#include "buket.h"
#include <iostream>

Buket::Buket(const Buket& b) {
	copy(b);
}

Buket::Buket(Buket&& b) {
	move(b);
}

Buket::~Buket() {
	dlt();
}

//razumna pretpostavka: moze da se unese vise 'istih' cvetova 
//u jedan buket i oni se tretiraju kao odvojeni cvetovi
void Buket::dodajCvet(const Cvet &c) {
	if (head == nullptr) {
		head = new Cv();
		head->cvet = &(const_cast<Cvet& >(c));
		head->next = nullptr;
		return;
	}
	Cv* p = head;
	while (p->next) p = p->next;
	p->next = new Cv();
	p->next->cvet = &(const_cast<Cvet&>(c));
	p->next->next = nullptr;
}

int Buket::nabavnaCena() const{
	Cv* p = head;
	int rez = 0;
	while (p) {
		rez += p->cvet->nabavnaCena();
		p = p->next;
	} return rez;
}

int Buket::prodajnaCena() const{
	Cv* p = head;
	int rez = 0;
	while (p) {
		rez += p->cvet->prodajnaCena();
		p = p->next;
	} return rez;
}

int Buket::zarada() const{
	return prodajnaCena() - nabavnaCena();
}

bool operator>(Buket &b1, Buket& b2) {
	return b1.prodajnaCena() > b2.prodajnaCena();
}

bool operator<(Buket &b1, Buket &b2) {
	return b1.prodajnaCena() < b2.prodajnaCena();
}

bool operator==(Buket &b1, Buket &b2) {
	return b1.prodajnaCena() == b2.prodajnaCena();
}

bool operator!=(Buket &b1, Buket &b2) {
	return !(b1 == b2);
}

Buket& Buket::operator=(const Buket& b) {
	if (this == &b) {
		return *this;
	}
	dlt();
	copy(b);
	return *this;
}

Buket& Buket::operator=(Buket&& b) {
	if (this == &b) {
		return *this;
	}
	dlt();
	move(b);
	return *this;
}


ostream& operator<<(ostream& os, const Buket& b) {
	if (!b.head) return os;
	Buket::Cv* p = b.head;
	os << "(";
	
	while (p) {
		os << *(p->cvet);
		if (p->next) os << ",";
		p = p->next;
	}return os << ") " << b.prodajnaCena() << "RSD\n";
}


void Buket::copy(const Buket& b) {
	Cv* tr = b.head;
	Cv* novi = nullptr, * posl = head;
	while (tr) {
		novi = new Cv(*tr);
		if (!head) head = novi;
		else posl->next = novi;
		posl = novi;
		tr = tr->next;
	}
}

void Buket::move(Buket& b) {
	head = b.head;
	b.head = nullptr;
}

void Buket::dlt() {
	Cv* tr = head;
	while (tr) {
		Cv* trr = tr;
		tr = tr->next;
		delete trr;
	}
	head = nullptr;
	
}