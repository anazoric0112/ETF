#include "biblioteka.h"

Biblioteka::Biblioteka(const Biblioteka& b) {
	copy(b);
}

Biblioteka::Biblioteka(Biblioteka&& b) {
	move(b);
}

Biblioteka::~Biblioteka() {
	dlt();
}

Biblioteka&  Biblioteka::operator+=(const Knjiga& k) {
	if (brojKnjiga == maxKnjiga) return *this;
	data[brojKnjiga] = !k;
	brojKnjiga++;
	return *this;
}

Knjiga* Biblioteka::dohvatiKnjigu(int idk) const {
	for (int i = 0; i < brojKnjiga; i++) {
		if ((*data[i]).Id() == idk) return data[i];
	} return nullptr;
}

ostream& operator<<(ostream& os, const Biblioteka& b) {
	os << "BIBLIOTEKA " << b.naziv << " " << b.brojKnjiga << " / " << b.maxKnjiga << endl;
	for (int i = 0; i < b.brojKnjiga; i++) {
		os << *b.data[i];
	} return os << endl;
}


Biblioteka& Biblioteka::operator=(const Biblioteka& b) {
	if (this == &b) return *this;
	dlt();
	copy(b);
	return *this;

}
Biblioteka& Biblioteka::operator=(Biblioteka&& b) {
	if (this == &b) return *this;
	cout << "mov2";
	dlt();
	move(b);
	return *this;
}


void Biblioteka::copy(const Biblioteka& b) {
	naziv = b.naziv;
	brojKnjiga = b.brojKnjiga;
	maxKnjiga = b.maxKnjiga;
	data = new Knjiga * [maxKnjiga];
	for (int i = 0; i < brojKnjiga; i++) {
		data[i] = !(*b.data[i]);
	}
}

void Biblioteka::move(Biblioteka& b) {
	cout << "mov";
	naziv = b.naziv;
	brojKnjiga = b.brojKnjiga;
	maxKnjiga = b.maxKnjiga;
	data = b.data;
	b.data = nullptr;
}

void Biblioteka::dlt() {
	delete[] data;
}