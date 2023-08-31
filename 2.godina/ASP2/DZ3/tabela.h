#ifndef _tabela_h_
#define _tabela_h_

#include <string>
#include <vector>
#include <iostream>
#include <set>
using namespace std;

class HashTable {
	int vel, k, p, pq, q, b;
	int br = 0, z[100000] = {0};

	void copy(const HashTable&);
	void move(HashTable&);
	void delet();
public:
	
	struct Info {
		vector <string> predmeti;
		string ime;
		string prezime;
		int indeks = 0;
	};
	struct Baket {
		set <int> polja;
		int pop,d;
		Info* info;
	};
	Baket** t = nullptr;

	HashTable(){}
	HashTable(int v, int kk, int ppp, int ppq, int qq, int bb) 
			:vel(v), k(kk), p(ppp), pq(ppq), q(qq), b(bb) {
		br = 0;
		t = new Baket*[vel];
		for (int i = 0; i < vel; i++) {
			t[i] = new Baket;
			t[i]->info = new Info[k];
			t[i]->pop = 0; t[i]->d = b;
			t[i]->polja.insert(i);
		}
	}
	HashTable(const HashTable&);
	HashTable( HashTable&&);
	~HashTable();

	HashTable& operator=(const HashTable&);
	HashTable& operator=(HashTable&&);

	int adresa(int k) { return k % (int)pow(2,p); }
	Info* findKey(int klj);
	bool insertKey(int klj, Info* i);
	bool deleteKey(int klj);
	void clear();
	int keyCount() const { return br; }
	int tableSize() const { return vel; }
	friend ostream& operator<<(ostream& os, const HashTable& h);
	double FillRatio() const { return br * 1. / vel / k; }
	bool dodajIspit(int klj, string ispit);
	bool obrisiIspit(int klj, string ispit);
};

#endif