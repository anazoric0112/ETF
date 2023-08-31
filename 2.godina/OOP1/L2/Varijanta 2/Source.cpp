#include "biblioteka.h"

int main() {
	Knjiga k1("Mama", "Mia");
	Knjiga k2("lol", "lmao");
	Knjiga k3("idk", "covek");

	Biblioteka b1("prva",10);
	Biblioteka b2("druga",3);
	Biblioteka b3("treca",5);
	//cout << k1 << k2 << k3;
	
	b3 += k1;
	b3 += k1;
	b3 += k2;
	
	
	b1 += k1;
	b1 += k3;
	b1 += k2;

	b2 += k2;
	b2 += k2;
	b2 += k3;
	b2 += k1;
	cout << b3;
	cout << b2;
	cout << b1;
	Biblioteka b5;
	b5 = b2;
	Biblioteka b4 = b3;
	cout << b5 << b4<<b1;
	//cout << b4<<b6;

	return 0;
}
