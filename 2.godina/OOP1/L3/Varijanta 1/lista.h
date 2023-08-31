#ifndef _lista_h_
#define _lista_h_

#include "greske.h"


template <typename T>
class Lista {
	typedef struct Elem {
		T data;
		Elem* sled;
		Elem(const T& t, Elem* sl = nullptr) { 
			data = t; sled = sl; 
		}
	};
	Elem* prvi, * posl;
	mutable Elem* tek;
	int br;

	void kopiraj(const Lista&);
	void premesti(Lista&);
	void brisi();

public:
	Lista() :prvi(nullptr), posl(nullptr), tek(nullptr), br(0) {}

	Lista(const Lista& l) { kopiraj(l); }
	Lista(Lista&& l) { premesti(l); }
	~Lista() { brisi(); }

	Lista& operator=(const Lista& l) {
		if (this != &l) {
			brisi();
			kopiraj(l);
		}
		return *this;
	}

	Lista& operator=(Lista&& l) {
		if (this != &l) {
			brisi();
			premesti(l);
		}
		return *this;
	}

	Lista& postaviNaPrvi() { 
		tek = prvi; 
		return *this;
	}
	Lista& postaviNaSledeci() { 
		if (tek) tek = tek->sled; 
		return *this;
	}
	const Lista& postaviNaPrvi() const {
		tek = prvi;
		return *this;
	}
	const Lista& postaviNaSledeci() const {
		if (tek) tek = tek->sled;
		return *this;
	}



	bool imaLiTekuci() const {
		if (tek) return true;
		return false;
	}

	Lista& dodajElem(const T& pod) {
		Elem* novi = new Elem(pod);
		br++;
		if (!prvi) {
			prvi = novi;
			posl = novi;
			return *this;
		}
		posl->sled = novi;
		posl = novi;
		return *this;
	}

	int brojElem() const { return br; }

	T& dohv() {
		if (!imaLiTekuci()) throw GNemaTekuceg();
		return tek->data;
	}
	/*const T& dohv() const {
		if (!imaLiTekuci()) throw GNemaTekuceg();
		return tek->data;
	}*/
};

template<typename T>
void Lista<T>::kopiraj(const Lista& l) {
	prvi = posl = tek = nullptr;
	br = l.br;
	for (Elem* tr = l.prvi; tr != nullptr; tr = tr->sled) {
		Elem* novi = new Elem(tr->data);
		if (!prvi) prvi = novi;
		else posl->sled = novi;
		posl = novi;
	}
}

template<typename T>
void Lista<T>::brisi() {
	while (prvi) {
		Elem* stari = prvi;
		prvi = prvi->sled;
		delete stari;
	}
	prvi = posl = tek = nullptr;
}

template<typename T>
void Lista<T>::premesti(Lista& l) {
	br = l.br;
	prvi = l.prvi; 
	posl = l.posl;
	tek = l.tek;
	l.prvi = l.posl = l.tek = nullptr;
}
#endif