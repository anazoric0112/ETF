#ifndef _sport_h_
#define _sport_h_

#include <string>
using namespace std;

class Sport
{
private:
	string naziv;
public:
	Sport(string ime) :naziv(ime) {}

	string dohvNaziv() const { return naziv; }

	friend bool operator<(const Sport& s1, const Sport& s2);
	friend bool operator>(const Sport& s1, const Sport& s2);
	friend bool operator==(const Sport& s1, const Sport& s2);
	friend bool operator!=(const Sport& s1, const Sport& s2);
};

#endif