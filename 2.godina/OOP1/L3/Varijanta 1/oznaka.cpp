#include "oznaka.h"

ostream& operator<<(ostream& os, const VrOznaka& v) {
	return os << v.dan << "." << v.mesec << "." 
		<< v.godina << "-" << v.sat << ":" << v.minut;
}