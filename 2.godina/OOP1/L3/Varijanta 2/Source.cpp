#include "Posiljka.h"
#include "Prodavac.h"
#include "greske.h"
#include <iostream>
using namespace std;

int main() {
	try {
		Artikal a(12, 500.00, "Meso");
		Prodavac p("Mesara");
		p.prosiriKatalog(a, 20.00, 10);
		Posiljka pos(a);
		pos += p;
		pos.izracunajDetalje();
		cout << pos << endl;
	}
	catch (exception e) {
		cout << e.what() << endl;
	}
	return 0;
}