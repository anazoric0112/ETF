#ifndef _skup_h_
#define _skup_h_

#include <string>
#include <iostream>
using namespace std;

class Skup {
	typedef struct Cv {
		char c;
		Cv* next;
	};
	Cv* head;
public:
	Skup(string niska="");
	Skup(const Skup&) = delete;
	Skup(Skup&&) = delete;

	Skup& operator=(const Skup&) = delete;
	Skup& operator=(Skup&&) = delete;

	Skup& operator+=(char ch);
	bool operator()(char ch);

};

#endif