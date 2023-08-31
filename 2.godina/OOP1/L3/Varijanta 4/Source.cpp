#include "Mec.h"

int main() {
	try {
		Igrac i1("Ana",100);
		Igrac i2("Mesi");
		Igrac i3("Ronaldo", 2000);
		Igrac i4("Milica", 1500);
		Igrac i5("Jana", 680);
		Igrac i6("Janko");
		Igrac i7("Stefan", 800);
		Tim t(4, "Barselona");
		Tim t2(3, "Junajted");
		PrivilegovaniTim t3(1000, 4, "Real");
		t.prikljuci(0, i1);
		t.prikljuci(1, i4);
		t.prikljuci(2, i5);
		t.prikljuci(3, i7);

		t2.prikljuci(0, i2);
		t2.prikljuci(1, i3);
		t2.prikljuci(2, i7);


		t3.prikljuci(1, i2);
		t3.prikljuci(2, i3);
		t3.prikljuci(3, i6);
		cout << t << t.vrednost()<< endl;
		cout << t3 << t3.vrednost()<<endl;;
		Mec m1(t, t3);
		m1.odigraj();
		cout << m1 << endl;

		Mec m2(t, t2);
		m2.odigraj();
		cout << m2 << endl;

		Mec m3(t3, t2);
		m3.odigraj();
		cout << m3 << endl;
	}
	catch (exception e) {
		cout << e.what();
	}
	return 0;
}