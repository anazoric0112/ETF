#include "Medalja.h"

Medalja::Medalja(string m, bool t)
{
	if (m == "Silver") mesto = 2;
	else if (m == "Gold") mesto = 1;
	else if (m == "Bronze") mesto = 3;
	else mesto = 4;
	tim = t;
}

bool operator<(const Medalja& m1, const Medalja& m2)
{
	return m1.dohvMesto() < m2.dohvMesto();
}
bool operator>(const Medalja& m1, const Medalja& m2)
{
	return m1.dohvMesto() > m2.dohvMesto();
}
bool operator==(const Medalja& m1, const Medalja& m2)
{
	return m1.dohvMesto() == m2.dohvMesto();
}
bool operator!=(const Medalja& m1, const Medalja& m2)
{
	return m1.dohvMesto() != m2.dohvMesto();
}

