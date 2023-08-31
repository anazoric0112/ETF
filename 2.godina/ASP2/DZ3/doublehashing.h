#ifndef _double_hashing_h_
#define _double_hashing_h_

#include "address.h"

class DoubleHashing: public Address {
public:
	int getAddress(int k, int a, int i, int n, int p, int q) {
		return  (a + (q + (k % p))) % n;
	}
};

#endif