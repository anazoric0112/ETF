#include "cvecara.h"
#include <iostream>

Buket fja(Cvet& c1, Cvet & c2) {
	Buket b;
	b.dodajCvet(c1);
	b.dodajCvet(c2);

	return b;
}

int main() {
	Cvet c1(3, 2, "ruza");
	Cvet c2(10, 4, "lala");
	Cvet c3(11, 7, "maslacak");
	Cvet c4(17, 11, "ruzica");
	Cvet c5(16, 13, "karanfil");
	Cvet c6(20, 18, "jasmin");
	Cvet c7(19, 16, "lavanda");
	/*cout << c1 << " " << c2 << " " << c3 << endl;
	cout << c1.nabavnaCena()<<endl;
	cout << c2.prodajnaCena()<<endl;
	cout << c3.zarada()<<endl;
	if (c4 == c1) cout << "c1 i c4 su jednaki\n";
	else cout <<"c1 i c4 nisu jednaki\n";
	if (c2==c3) cout << "c2 i c3 su jednaki\n";
	else cout << "c2 i c3 nisu jednaki\n";*/

	Buket b1;
	b1.dodajCvet(c1);
	b1.dodajCvet(c3);
	b1.dodajCvet(c1);
	b1.dodajCvet(c4);
	b1.dodajCvet(c2);

	Buket b2; 
	b2 = b1;
	b2.dodajCvet(c2);
	b2.dodajCvet(c3);
	Buket b3;
	b3.dodajCvet(c4);
	b3.dodajCvet(c2);
	b3.dodajCvet(c7);
	Buket b4;
	b4.dodajCvet(c2);
	b4.dodajCvet(c4);
	b4.dodajCvet(c6);
	b4.dodajCvet(c7);
	b4.dodajCvet(c7);
	//b1 = b1;
	
	cout << b1.nabavnaCena() << " " << b1.prodajnaCena() << " " << b1.zarada()<<endl;
	cout << b1 << b2 << b3 << b4;
	cout << (b1 > b2) << " " << (b3 < b4) << " " << (b4 == b2) << " " << (b3 != b3) << " " << (b3 != b1);

	cout << b1.nabavnaCena() << " " << b1.prodajnaCena() << endl;
	cout << b2.nabavnaCena() << " " << b2.prodajnaCena() << endl;
	cout << b3.nabavnaCena() << " " << b3.prodajnaCena() << endl;
	cout << b4.nabavnaCena() << " " << b4.prodajnaCena() << endl; 
	


	Cvecara c;
	Cvecara cv3;

	c.dodajBuket(b2);
	cout << c;
	c.dodajBuket(b1);
	cout << c;
	c.dodajBuket(b3);
	cout << c;
	c.dodajBuket(b4);
	cv3 = c;
	cout << c;
	c.prodajBuket(3);
	cout << c;
	c.prodajBuket(0);
	cout << c;
	c.prodajBuket(3);
	cout << c;

	Cvecara cv2 = Cvecara();
	Buket b5 = b4;
	cout << b5<<b4;
	cv2.dodajBuket(b5);
	cv2.dodajBuket(b4);
	cout << cv2;
	//Buket b6; b6 = fja(c3, c4);
	//cout << b6;
	cout << cv2;
	cout << cv3;
	return 0;
}