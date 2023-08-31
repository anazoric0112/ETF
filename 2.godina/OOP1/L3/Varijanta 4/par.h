#pragma once

#include <iostream>
using namespace std;


template <typename T,typename U>
class Par {
	T* pod1;
	U* pod2;
public:
	Par(T* p1, U* p2) :pod1(p1), pod2(p2) {}
	T* dohvPod1() const { return pod1; }
	U* dohvPod2() const { return pod2; }
	void postaviPod1(T* p) { pod1 = p; }
	void postaviPod2(U* p) { pod2 = p; }

	bool operator==(Par<T, U> p) const {
		return *pod1 == *p.pod1 && *pod2 == *p.pod2;
	}

	friend ostream& operator<<(ostream& os, const Par<T, U>& par) {
		return os << "[" << *par.dohvPod1() << "-" << *par.dohvPod2() << "]";
	}
};

