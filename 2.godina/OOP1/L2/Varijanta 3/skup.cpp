#include "skup.h"

Skup::Skup(string niska) {
	Cv* prev = nullptr;
	head = nullptr;
	for (int i = 0; i < niska.length(); i++) {

		Cv* t = head;
		bool f = 0;
		while (t) {
			if (t->c == niska[i]) {
				f = 1; break;
			} t = t->next;
		}
		if (f) continue;

		Cv* p = new Cv;
		p->c = niska[i];
		p->next = nullptr;

		if (!head) {
			head = p;
			prev = p;
			continue;
		} 
		prev->next = p;
		prev = p;
		
	}
}

Skup& Skup::operator+=(char ch) {
	if (!head) {
		head = new Cv;
		head->c = ch;
		head->next = nullptr;
		return *this;
	}
	Cv* t = head;
	while (t) {
		if (t->c == ch) {
			return *this;
		} t = t->next;
	}
	t = head;
	while (t->next) t = t->next;
	t->next = new Cv;
	t->next->c = ch;
	t->next->next = nullptr;
	return *this;
}

bool Skup::operator()(char ch) {
	Cv* t = head;
	while (t) {
		if (t->c == ch) {
			return true;
		} t = t->next;
	} return false;
}