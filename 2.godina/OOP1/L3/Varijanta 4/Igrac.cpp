#include "Igrac.h"

void Igrac::povecajVr(double procenat)
{
	vr = vr * (procenat + 100) / 100;
}

void Igrac::smanjiVr(double procenat)
{
	vr = vr * (100 - procenat) / 100;
}

bool Igrac::operator==(const Igrac& i) const
{
	return ime == i.ime && vr == i.vr;
}

ostream& operator<<(ostream& os, const Igrac& i)
{
	return os << i.ime << "#" << i.vr;
}
