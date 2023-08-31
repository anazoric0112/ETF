#include "cvet.h"
#include <iostream>

int Cvet::zarada() const{
	return pr - nab;
}
bool operator==(Cvet& a, Cvet& b) {
	return a.naziv() == b.naziv();
}
ostream& operator<<(ostream& os, const Cvet& c) {
	return os << c.naziv();
	
}