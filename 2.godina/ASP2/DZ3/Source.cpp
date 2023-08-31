#include "tabela.h"
#include "doublehashing.h"
#include <iostream>
#include <fstream>

void meni() {
	cout << "\n\nIzaberite opciju koju zelite\n"
		"1. Pretrazite studenta po indeksu\n"
		"2. Ubacite studenta u bazu\n"
		"3. Obrisite studenta iz baze\n"
		"4. Obrisite sve studente iz baze\n"
		"5. Ispisite broj studenata u bazi\n"
		"6. Ispisite velicinu tabele\n"
		"7. Ispisite sve podatke iz baze\n"
		"8. Ispisite stepen popunjenosti baze\n"
		"9. Dodajte ispit studentu sa zeljenim indeksom\n"
		"10. Obrisite ispit studenta sa zeljenim indeksom\n"
		"11. Zavrsite program\n";
}

int main() {
	cout << "Unesite velicinu jednog baketa, kao  i "
		"koeficijente p i q za pomocnu adresnu funkciju (celobrojne vrednosti) "
		"i inicijalnu dubinu tabele b: \n";
	int k, n, pp, p, q, b;
	cin >> k >> p >> q >> b;
	n = (int)pow(2, b);
	pp = b;
	HashTable tabela(n, k, pp, p, q, b);

	/*ifstream f; f.open("students_100000.csv");
	string str; getline(f, str, '\n');
	while (getline(f, str, '\n')) {
		string s = "";
		HashTable::Info novi; int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (j == 0) {
				if (str[i] != ',') s += str[i];
				else { novi.indeks = stoi(s); j = 1; s = ""; }
			}
			else if (j == 1) {
				if (str[i] != ' ') s += str[i];
				else { novi.ime = s; j = 2; s = ""; }
			}
			else if (j == 2) {
				if (str[i] != ',') s += str[i];
				else { novi.prezime = s; j = 3; s = ""; }
			}
			else if (j == 3) {
				if (str[i] != ' ' && str[i] != '\n') s += str[i];
				else { novi.predmeti.push_back(s); s = ""; }
			}
		} novi.predmeti.push_back(s);
		tabela.insertKey(novi.indeks, &novi);
	}
	ifstream ff; ff.open("students_100000.csv");
	 getline(ff, str, '\n');
	while (getline(ff, str, '\n')) {
		string s = ""; int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (j == 0) {
				if (str[i] != ',') s += str[i];
				else { j= stoi(s); break; }
			}
		}
		tabela.deleteKey(j);
	}
	cout << tabela.keyCount() << " " << tabela.tableSize();*/

	while (1) {
		meni();
		int izb; cin >> izb;
		if (izb == 1) {
			if (tabela.keyCount() == 0) {
				cout << "Tabela je prazna\n";
				continue;
			}
			cout << "Unesite zeljeni indeks u formatu GGGGBBBB: ";
			int indeks; cin >> indeks;
			HashTable::Info* inf = tabela.findKey(indeks);
			if (!inf) {
				cout << "Studenta nema u bazi\n"; continue;
			}
			cout << inf->ime << " " << inf->prezime << " ";
			for (auto pr : inf->predmeti) cout << pr << " "; cout << endl;
		}
		else if (izb == 2) {
			cout << "Zelite li uneti podatke:"
				"1.Iz fajla\n2.Sa standardnog ulaza\n";
			int ii; cin >> ii; string str;
			if (ii == 2) {
				HashTable::Info novi;
				cout << "Unesite indeks, ime i prezime:\n";
				cin >> novi.indeks >> novi.ime >> novi.prezime;
				cout << "Koliko predmeta zelite uneti?"; int p; cin >> p;
				for (int i = 0; i < p; i++) {
					cin >> str; novi.predmeti.push_back(str);
				}
				bool ups = tabela.insertKey(novi.indeks, &novi);
				if (ups) cout << "Uspesno ste dodali studenta\n";
				else cout << "Student sa tim indeksom je vec u bazi\n";
			}
			else if (ii == 1) {
				cout << "Unesite ime fajla:\n";
				ifstream f; cin >> str; f.open(str);
				getline(f, str, '\n');
				while (getline(f, str, '\n')) {
					string s = "";
					HashTable::Info novi; int j = 0;
					for (int i = 0; i < str.length(); i++) {
						if (j == 0) {
							if (str[i] != ',') s += str[i];
							else { novi.indeks = stoi(s); j = 1; s = ""; }
						}
						else if (j == 1) {
							if (str[i] != ' ') s += str[i];
							else { novi.ime = s; j = 2; s = ""; }
						}
						else if (j == 2) {
							if (str[i] != ',') s += str[i];
							else { novi.prezime = s; j = 3; s = ""; }
						}
						else if (j == 3) {
							if (str[i] != ' ' && str[i] != '\n') s += str[i];
							else { novi.predmeti.push_back(s); s = ""; }
						}
					} novi.predmeti.push_back(s);
					tabela.insertKey(novi.indeks, &novi);
				}
			}
		}
		else if (izb == 3) {
			if (tabela.keyCount() == 0) {
				cout << "Tabela je prazna\n";
				continue;
			}
			cout << "Unesite zeljeni indeks u formatu GGGGBBBB: ";
			int indeks; cin >> indeks;
			int ups = tabela.deleteKey(indeks);
			if (ups) cout << "Uspesno ste obrisali studenta\n";
			else cout << "Studenta nije bilo u bazi\n";
		}
		else if (izb == 4) {
			if (tabela.keyCount() == 0)
				cout << "Tabela je vec prazna\n";
			else tabela.clear();
		}
		else if (izb == 5) {
			cout << tabela.keyCount();
		}
		else if (izb == 6) {
			cout << tabela.tableSize();
		}
		else if (izb == 7) {
			if (tabela.keyCount() == 0)
				cout << "Tabela je potpuno prazna\n";
			else cout << tabela;
		}
		else if (izb == 8) {
			cout << tabela.FillRatio();
		}
		else if (izb == 9) {
			if (tabela.keyCount() == 0)
				cout << "Tabela je prazna\n";
			else {
				cout << "Unesite indeks studenta i oznaku ispita koji mu zelite dodati";
				int inx; string ispit; cin >> inx >> ispit;
				int l = tabela.dodajIspit(inx, ispit);
				if (!l) cout << "Studenta nema u bazi ili je vec prijavio taj ispit\n";
				else cout << "Uspesno ste dodali ispit studentu\n";
			}
		}
		else if (izb == 10) {
			if (tabela.keyCount() == 0)
				cout << "Tabela je prazna\n";
			else {
				cout << "Unesite indeks studenta i oznaku ispita koji mu zelite odjaviti";
				int inx; string ispit; cin >> inx >> ispit;
				int l = tabela.obrisiIspit(inx, ispit);
				if (!l) cout << "Studenta nema u bazi ili mu taj ispit nije prijavljen\n";
				else cout << "Uspesno ste odjavili ispit studentu\n";
			}
		}
		else if (izb == 11) { break; }
	}
	return 0;
}