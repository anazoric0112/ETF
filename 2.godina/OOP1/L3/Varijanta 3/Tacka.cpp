#include "Tacka.h"

double Tacka::udaljenost(const Tacka& t) const
{
	return sqrt(pow(x - t.x, 2) + pow(y - t.y, 2));
}

bool Tacka::operator==(const Tacka& t) const
{
	return x == t.x && y == t.y;
}

ostream& operator<<(ostream& os, const Tacka& t)
{
	return os << "(" << t.x << "," << t.y << ")";
}
