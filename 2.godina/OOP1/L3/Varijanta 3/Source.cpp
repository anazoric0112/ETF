#include "obicnoVozilo.h"

int main() {

	try {
		Put p;
		Tacka t1(1, 2);
		Tacka t2(3, 2);
		Tacka t3(3, 8);
		Tacka t4(4, 8);
		Tacka t5(4, 5);
		p += t1;
		p += t2;
		p += t3;
		p += t4;
		p += t5;
		cout << p.duzina() << endl;
		cout << p << endl;
		ObicnoVozilo v("500l");
		cout << v.cena(p);

	}
	catch (exception e) {
		cout << e.what();
	}


	return 0;
}