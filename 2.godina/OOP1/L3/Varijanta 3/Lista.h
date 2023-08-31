#ifndef _lista_h_
#define _lista_h_

#include "greske.h"

template <typename T>
class Lista {
	typedef struct Elem {
		Elem* sled;
		T data;
		Elem(const T& t, Elem* sl = nullptr) :data(t), sled(sl) {}
	};
	int br;
	Elem* prvi, * posl;
	void kopiraj(const Lista& l);
	void premesti(Lista&);
	void brisi();
public:
	Lista() :br(0), prvi(nullptr), posl(nullptr) {}
	Lista(const Lista& l) { kopiraj(l); }
	Lista(Lista&& l) { premesti(l); }
	~Lista() { brisi(); }

	Lista& operator=(const Lista& l) {
		if (this != &l) {
			brisi(); kopiraj(l);
		} return *this;
	}
	Lista& operator=(Lista&& l) {
		if (this != &l) {
			brisi(); premesti(l);
		} return *this;
	}


	Lista<T>& operator+=(const T& pod);
	T& operator[](int i);
	const T& operator[](int i) const;

	int brPodataka() const { return br; }
};

template <typename T>
Lista<T>& Lista<T>::operator+=(const T& pod) {
	Elem* novi = new Elem(pod);
	if (!prvi) prvi = novi;
	else posl->sled = novi;
	posl = novi;
	br++;
	return *this;
}

template <typename T>
T& Lista<T>::operator[](int i) {
	if (i >= br) throw GNemaPodatka();
	if (i < 0) throw GNevalidanIndeks();
	Elem* tr = prvi;
	for (int j = 0; j < i; j++, tr = tr->sled);
	return tr->data;
}

template <typename T>
const T& Lista<T>::operator[](int i) const {
	return const_cast<Lista&>(*this)[i];
}

template<typename T>
void Lista<T>::kopiraj(const Lista& l) {
	prvi = posl = nullptr;
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
	prvi = posl = nullptr;
}

template<typename T>
void Lista<T>::premesti(Lista& l) {
	br = l.br;
	prvi = l.prvi;
	posl = l.posl;
	l.prvi = l.posl = nullptr;
}
#endif