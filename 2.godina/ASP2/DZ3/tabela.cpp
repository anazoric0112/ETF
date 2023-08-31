#include "tabela.h"
#include "doublehashing.h"

void HashTable::copy(const HashTable& tab) {
	vel = tab.vel;
	br = tab.br;
	p = tab.p; pq = tab.pq; q = tab.q; k = tab.k;
	t = new Baket*[vel];
	for (int i = 0; i < vel; i++) {
		z[i] = tab.z[i];
		if (!tab.z[i]) continue;
		t[i]->info = new Info[k];
		t[i]->pop = tab.t[i]->pop;
		t[i]->d = tab.t[i]->d;
		t[i]->polja = tab.t[i]->polja;
		for (int j = 0; j < k; j++) {
			if (!tab.t[i]->info[j].indeks) continue;
			t[i]->info[j].predmeti = tab.t[i]->info[j].predmeti;
			t[i]->info[j].ime = tab.t[i]->info[j].ime;
			t[i]->info[j].prezime = tab.t[i]->info[j].prezime;
			t[i]->info[j].indeks = tab.t[i]->info[j].indeks;
		}
	}
}
void HashTable::move(HashTable& tab) {
	vel = tab.vel;
	br = tab.br;
	p = tab.p; pq = tab.pq; q = tab.q; k = tab.k;
	for (int i = 0; i < vel; i++) z[i] = tab.z[i];
	t = tab.t;
	tab.t = nullptr;
}
void HashTable::delet() {
	delete t;
	t = nullptr;
}

HashTable::HashTable(const HashTable& t) {
	copy(t);
}
HashTable::HashTable(HashTable&& t) {
	move(t);
}
HashTable::~HashTable() {
	delet();
}

HashTable& HashTable::operator=(const HashTable& tab) {
	if (&tab != this) {
		delet();
		copy(tab);
	} return *this;
}
HashTable& HashTable::operator=(HashTable&& tab) {
	if (&tab != this) {
		delet();
		move(tab);
	} return *this;
}

HashTable::Info* HashTable::findKey(int klj) {
	int ind = adresa(klj);
	for (int i = 0; i < k; i++) {
		if (t[ind]->info[i].indeks == klj) return &t[ind]->info[i];
	}
	
	return nullptr;
}

bool HashTable::insertKey(int klj, Info* inf) {
	int ind = adresa(klj);
	if (findKey(klj)) return 0;
	
	//ako ima mesta u baketu 
	if (t[ind]->pop < k) {
		for (int i = 0; i < k; i++) {
			if (t[ind]->info[i].indeks <= 0) {
				t[ind]->info[i] = *inf;
				t[ind]->pop++;
				br++;   
				for (auto polje : t[ind]->polja) z[ind] = 1;
				return 1;
			}
		}
	}
	//ako ima potrebe, dodaju se polja tabeli i ona se prosiruje
	if (t[ind]->d == b) {
		vel *= 2; p++; b++;
		HashTable::Baket** tt = t;
		t = new Baket * [vel];
		for (int i = 0; i < vel / 2; i++) t[i] = tt[i];
		for (int i = vel / 2; i < vel; i++) {
			t[i] = t[i - vel / 2];
			t[i - vel / 2]->polja.insert(i);
			z[i] = z[i - vel / 2];
		}
	}
	t[ind]->d++;
	//novi baket
	Baket* novi=new Baket;
	novi->pop = 0;
	novi->info = new Info[k];
	novi->d = t[ind]->d;
	//pokazivaci polja na bakete se preraspodeljuju
	for (auto polje : t[ind]->polja) {
		if (polje % (int)pow(2, t[ind]->d) != ind % (int)pow(2, t[ind]->d)) {
			t[polje] = novi;
			novi->polja.insert(polje);
			z[polje] = 1;
		}
	}
	for (auto polje: novi->polja) t[ind]->polja.erase(polje);

	//infoi se preraspodeljuju na bakete
	for (int i = 0; i < k; i++) {
		int adr = adresa(t[ind]->info[i].indeks);
		if (adr % (int)pow(2, t[ind]->d) != ind % (int)pow(2, t[ind]->d)) {
			
			novi->info[novi->pop].indeks = t[ind]->info[i].indeks;
			novi->info[novi->pop].ime = t[ind]->info[i].ime;
			novi->info[novi->pop].prezime = t[ind]->info[i].prezime;
			novi->info[novi->pop].predmeti = t[ind]->info[i].predmeti;
			t[ind]->info[i].indeks = -1;
			novi->pop++;
			t[ind]->pop--;
		}
	}
	//umece se kljuc na kraju
	ind = adresa(klj);
	for (int i = 0; i < k; i++) {
		if (t[ind]->info[i].indeks <= 0) {
			t[ind]->info[i] = *inf;
			t[ind]->pop++;
			br++;   z[ind] = 1;
			return 1;
		}
	}
	return 0;
	
}

bool HashTable::deleteKey(int klj) {
	int ind = adresa(klj);
	if (!findKey(klj)) return 0;
	for (int i = 0; i < k; i++) {
		if (t[ind]->info[i].indeks == klj) {
			t[ind]->info[i].indeks = -1;
			br--;
			for (int i = 0; i < k; i++) {
				if (t[ind]->info[i].indeks > 0) break;
				if (i == k - 1) z[ind] = 0;
			}
			t[ind]->pop--;
			Baket* tt = t[ind];
			if (t[ind]->pop==0) {
				tt->d--;
				for (auto po : tt->polja) {
					if (po > tt->d) t[po] = t[po - tt->d];
					else t[po] = t[po + tt->d];
					t[po]->d = tt->d;
				}
			}
			return 1;
		}
	}
	return 0;
}

void HashTable::clear() {
	for (int i = 0; i < vel; i++) {
		z[i] = 0;
		t[i]->pop = 0;
		for (int j = 0; j < k; j++) {
			if (t[i]->info[j].indeks>0) t[i]->info[j].indeks = -1;
		}
	} br = 0;
}

ostream& operator<<(ostream& os,const  HashTable& h) {
	for (int i = 0; i < h.tableSize(); i++) {
		os << "Baket " << i + 1 << " :\n";
		for (int j = 0; j < h.k; j++) {
			os << "Lokacija " << j + 1 << ": \n";
			if (h.t[i]->info[j].indeks == -1) os << "DELETED\n";
			else if (h.t[i]->info[j].indeks == 0) os << "EMPTY\n";
			else {
				os << h.t[i]->info[j].indeks << " " << h.t[i]->info[j].ime << " " << h.t[i]->info[j].prezime << " ";
				for (auto pr : h.t[i]->info[j].predmeti) os << pr << " "; os << endl;
			}

		}os << endl;
	} return os;
}

bool HashTable::dodajIspit(int klj, string ispit) {
	Info* inf = findKey(klj);
	if (!inf) return 0;
	for (auto isp: inf->predmeti) {
		if (isp == ispit) return 0;
	}
	inf->predmeti.push_back(ispit);
	return 1;
}
bool HashTable::obrisiIspit(int klj, string ispit) {
	Info* inf = findKey(klj);
	if (!inf) return 0;
	int ind = 0;
	for (int i = 0; i < inf->predmeti.size(); i++) {
		if (inf->predmeti[i] == ispit) { ind = i; break; }
	}
	for (int i = ind; i < inf->predmeti.size() - 1; i++) {
		swap(inf->predmeti[i], inf->predmeti[i + 1]);
	}
	inf->predmeti.pop_back();
	return 1;
}