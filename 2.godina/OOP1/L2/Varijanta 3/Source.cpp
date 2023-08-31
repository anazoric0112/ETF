#include "rec.h"
#include <iostream>

int main() {
	Skup s1("ana");
	Skup s2("filip");
	s1 += 'a';
	s1 += 'c';
	s2 += '\'';
	cout << s1('a') << s1('k') << s2('a') << endl;
	Rec r1("par]CAd"), r2("rad"), r3("lo,S "), r4("koLos"), r5("mama"), r6("sesta");
	//cout << r1 << r2 << r3 << r4 << r5 << r6;
	cout << (r1 ^ r2)<<(r3^r4)<<(r5^r6)<<endl;

	Rec r7("amAteR");
	Rec r8 ("maTer");
	//cout << r8 << r7;
	cout << (r8 ^ r7) << " " << (r1 ^ r2) << " " << (r3 ^ r4);
	return 0;
}