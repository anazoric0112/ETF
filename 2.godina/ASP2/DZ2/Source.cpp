#include "stablo.h"


void meni() {
	cout << "Unesite opciju koju zelite\n"
		"1. Napravite stablo reda od 3 do 10\n"
		"2. Proverite da li neki kljuc postoji u stablu\n"
		"3. Ispisite stablo\n"
		"4. Ubacite kljuc u stablo\n"
		"5. Obrisite kljuc iz stabla\n"
		"6. Obrisite stablo\n"
		"7. Napustite program\n";
}


int main() {
	/*Stablo s1(4);
	ifstream fi; fi.open("words.txt");
	map <string, int> mapa;
	string s; int i = 0;
	while (fi>>s) {
		s1.dodajRec(s);
		mapa[s]++;
		//cout << i - 9 << "\n" << s1 << "\n\n\n";
	}
	for (auto mm : mapa) if (mm.second) i++;
	fi.open("izb.txt");

	s1.obrisiRec("85"); //cout << s1;
	s1.obrisiRec("66"); //cout << s1; //ima  
	cout << s1;
	s1.obrisiRec("69"); 
	cout << s1;
	s1.obrisiRec("77"); cout << s1;
	s1.obrisiRec("70"); cout << s1;
	s1.obrisiRec("71"); cout << s1; //ima
	s1.obrisiRec("56"); cout << s1; //ima
	return 0;
	Stablo s1(3);
	for (int i = 10; i <= 50; i++) {
		string s = to_string(i);
		s1.dodajRec(s);
	}
	cout << s1;
	return 0;
	s1.op();
	s1.dodajRec("aa"); cout << s1;
	s1.dodajRec("bb");cout << s1;
	s1.dodajRec("cc"); cout << s1;
	s1.dodajRec("dd"); cout << s1;
	s1.dodajRec("ee"); cout << s1 << "\n\n\n";
	s1.dodajRec("eef"); cout << s1;
	s1.dodajRec("cd"); cout << s1;
	//s1.dodajCvor("ab"); //cout << s1;
	s1.dodajRec("eeff"); cout << s1;
	//cout << "PRVI PRESEK:\n" << s1 << "\nKRAJ";
	s1.dodajRec("da"); cout << s1;
	s1.dodajRec("pukne"); cout << s1;
	s1.dodajRec("sutra"); cout << s1;
	s1.dodajRec("kakosi"); cout << s1;
	s1.dodajRec("volimte"); cout << s1;
	s1.dodajRec("iako"); cout << s1;
	s1.dodajRec("mada"); cout << s1;
	s1.dodajRec("ti"); cout << s1;
	s1.dodajRec("tamo"); cout << s1;
	s1.dodajRec("probaj"); cout << "\n\n\n" << s1;

	return 0;

	
	string a;
	Stablo s1(4);
	Stablo s2 = s1;
	while (cin >> a) {
		s1.dodajRec(a);
		if (a == "malina") break;
	} cout << s1; return 0;
	s1.dodajRec("ana");
	s1.dodajRec("mama");
	s1.dodajRec("aba");
	s1.dodajRec("mozda");
	s1.dodajRec("neznam"); //cout << s1;
	s1.dodajRec("dokle"); //cout << s1;
	s1.dodajRec("nikad"); //cout << s1;
	s1.dodajRec("da"); //cout << s1;
	s1.dodajRec("pukne"); //cout << s1;
	s1.dodajRec("sutra"); //cout << s1;
	s1.dodajRec("kakosi"); //cout << s1;
	s1.dodajRec("volimte"); //cout << s1;
	s1.dodajRec("iako"); //cout << s1;
	s1.dodajRec("mada"); //cout << s1;
	s1.dodajRec("ti"); //cout << s1;
	s1.dodajRec("tamo"); //cout << s1;
	s1.dodajRec("probaj"); //cout << s1;
	s1.dodajRec("ovaj"); //cout << s1;
	s1.dodajRec("ako"); //cout << s1;
	s1.dodajRec("leptir"); //cout << s1;
	s1.dodajRec("jutro"); //cout << s1;
	s1.dodajRec("dobro"); //cout << s1;
	s1.dodajRec("lose"); //cout << s1;
	s1.dodajRec("sta"); //cout << s1;
	s1.dodajRec("sad"); //cout << s1;
	s1.dodajRec("eh"); //cout << s1;
	s1.dodajRec("bre"); //cout << s1;
	s1.dodajRec("gde"); //cout << s1;
	s1.dodajRec("er");	//cout << s1;
	s1.dodajRec("si"); //cout << s1;
	s1.dodajRec("zet"); //cout << s1;
	s1.dodajRec("jot"); //cout << s1;
	cout << s1; return 0;
	cout << s1.reciIspred("pukne") << " " << s1.reciIspred("volimte") << " " << s1.reciIspred("probaj") << " ";
	cout << s1.reciIspred("iako") << " " << s1.reciIspred("mada") << " " << s1.reciIspred("ana")<<" ";
	cout << s1.reciIspred("ako") << " " << s1.reciIspred("ti") << " " << s1.reciIspred("aba")<<" ";
	cout << s1.reciIspred("tamo") << " " << s1.reciIspred("noep") << " " << s1.reciIspred("mhm")<<" "; cout << endl;
	vector <string> v;
	v.push_back("strs");
	v.push_back("strd");
	v.push_back("stfdr");
	v.push_back("strf");
	v.push_back("stra");
	v.push_back("swwtra");
	v.push_back("strat");

	cout<<s1.nadjiRec("nikad")<<endl;
	cout<<s1.nadjiRec("nika")<<endl;
	cout<<s1.nadjiRec("aanikad")<<endl;
	cout<<s1.nadjiRec("niad")<<endl;
	cout<<s1.nadjiRec("volimte")<<endl;
	cout<<s1.nadjiRec("jel")<<endl;
	cout << s1;
	cout << "-----------\n";
	s2.dodajRec("iako"); cout << s2;
	s2.dodajRec("mada"); cout << s2;
	s2.dodajRec("ti"); cout << s2;
	s2.dodajRec("tamo"); cout << s2;
	s2.dodajRec("probaj"); cout << s2;
	s2.dodajRec("ovaj"); cout << s2;
	s2.dodajRec("ako"); cout << s2;
	cout << "---------------BRISANJE----------------\n";
	s1.obrisiRec("al"); //cout << s1;
	s1.obrisiRec("ako");// cout << s1;
	s1.obrisiRec("ti");// cout << s1;
	s1.obrisiRec("volimte"); //cout << s1;
	s1.obrisiRec("ovaj"); //	cout << s1;
	s1.obrisiRec("tamo");// cout << s1;
	s1.dodajRec("pula");
	s1.obrisiRec("dokle"); //cout << s1;
	s1.obrisiRec("dobro"); //cout << s1;
	s1.obrisiRec("ana"); //cout << s1;
	s1.obrisiRec("aba"); //cout << s1;
	s1.obrisiRec("sta"); //cout << s1;
	s1.obrisiRec("zet"); //cout << s1;
	s1.obrisiRec("iako"); //cout << s1;
	s1.obrisiRec("probaj"); //cout << s1;
	s1.obrisiRec("da"); //cout << s1;
	s1.obrisiRec("leptir"); //cout << s1;
	s1.obrisiRec("jutro"); //cout << s1;
	s1.obrisiRec("lose"); //cout << s1;
	s1.obrisiRec("nikad"); //cout << s1;
	s1.obrisiRec("mozda"); //cout << s1;
	s1.obrisiRec("mada"); //cout << s1;
	s1.obrisiRec("sad"); //cout << s1;
	s1.obrisiRec("pula"); //cout << s1;
	s1.obrisiRec("sutra"); //cout << s1;
	s1.obrisiRec("eh"); //cout << s1;
	s1.obrisiRec("jot"); //cout << s1;
	s1.obrisiRec("kakosi"); //cout << s1;
	s1.obrisiRec("neznam"); cout << s1;
	s1.obrisiRec("pukne"); //cout << s1;
	s1.obrisiRec("er"); //cout << s1;
	s1.obrisiRec("neznam"); cout << s1;
	s1.obrisiRec("si"); cout << s1;
	s1.obrisiRec("gde"); cout << s1;
	s1.dodajRec("sad"); cout << s1;
	s1.dodajRec("eh"); cout << s1;
	s1.dodajRec("bre"); cout << s1;
	s1.dodajRec("gde"); cout << s1;

	return 0;
	*/
	Stablo stablo(0);
	int ima = 0;
	cout << "Zelite li ucitavati podatke:\n1. Sa standardnog ulaza\n2. Iz fajla\n";
	int f; cin >> f; f--;
	ifstream fil; string dat;
	if (f) {
		cout << "Unesite ime fajla\n"; cin >> dat;
		fil.open(dat);
		if (!fil.is_open()) { cout << "Nevalidno ime fajla\n"; return 0; }
	}
	while (1) {
		meni();
		int izbor; if (f) fil >> izbor; else cin >> izbor;
		if (izbor == 1) {
			if (ima) { cout << "Stablo je vec u memoriji\n"; continue; }
			cout << "Unesite zeljeni red stabla izmedju 3 i 10\n";
			int red = 11; while (red < 3 || red>10) {
				if (f) fil >> red; else  cin >> red;
				if (red >= 3 && red <= 10) break;
				cout << "Nevalidan broj, pokusajte ponovo\n";
			}
			stablo = Stablo(red); ima = 1;
		}
		else if (izbor == 2) {
			if (!ima) { cout << "Nema stabla u memoriji\n"; continue; }
			cout << "Unesite zeljeni kljuc\n";
			string rec; if (f) fil >> rec; else cin >> rec;
			if (stablo.nadjiRec(rec)) cout << "Kljuc postoji u stablu\n";
			else cout << "Kljuc ne postoji u stablu\n";
		}
		else if (izbor == 3) {
			if (!ima) { cout << "Nema stabla u memoriji\n"; continue; }
			cout << stablo;
		}
		else if (izbor == 4) {
			if (!ima) { cout << "Nema stabla u memoriji\n"; continue; }
			cout << "Unesite kljuc koji zelite dodati\n";
			string rec; if (f) fil >> rec; else cin >> rec;
			if (stablo.dodajRec(rec)) cout << "Kljuc je uspesno dodat\n";
			else cout << "Kljuc vec postoji u stablu\n";
		}
		else if (izbor == 5) {
			if (!ima) { cout << "Nema stabla u memoriji\n"; continue; }
			cout << "Unesite  kljuc koji zelite obrisati\n";
			string rec; if (f) fil >> rec; else cin >> rec;
			if (stablo.obrisiRec(rec)) cout << "Kljuc je uspesno obrisan\n";
			else cout << "Kljuc ne postoji u stablu\n";
		}
		else if (izbor == 6) {
			if (!ima) { cout << "Nema stabla u memoriji\n"; continue; }
			stablo.Stablo::~Stablo();
			cout << "Stablo je obrisano"; ima = 0;
		}
		else if (izbor == 7) {
			cout << "----- Hvala na paznji :3 -----";
			if (fil.is_open()) fil.close();
			return 0;
		}
	}
}