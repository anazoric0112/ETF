#include "Sport.h"

bool operator<(const Sport& s1, const Sport& s2)
{
	return s1.dohvNaziv() < s2.dohvNaziv();
}
bool operator==(const Sport& s1, const Sport& s2)
{
	return s1.dohvNaziv() == s2.dohvNaziv();
}
bool operator>(const Sport& s1, const Sport& s2)
{
	return s1.dohvNaziv() > s2.dohvNaziv();
}
bool operator!=(const Sport& s1, const Sport& s2)
{
	return s1.dohvNaziv() != s2.dohvNaziv();
}