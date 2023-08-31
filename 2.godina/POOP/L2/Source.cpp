#include "Olimpijade.h"
#include <functional>
#include "greske.h"

using namespace std;

void meni();
void filteriS(bool u[4], Sport* s, Drzava* d, int* g, Medalja* m, Olimpijade* o);
void filteriD(bool u[3], Sport* s, int* g, bool* tim);

Olimpijade* ucitaj(string * f1, string * f2, string path) {
	Olimpijade* o = new Olimpijade();
	bool ucitano = false;
	while (!ucitano) {
		try {
			cout << "Unesite ime fajla za citanje podataka o dogadjajima i o sportistima\n";
			cin >> *f1 >> *f2;
			*f1 = path + *f1;
			*f2 = path + *f2;
			cout << "Ako zelite uneti samo podatke za specificnu godinu, upisite je, a ako ne upisite 0\n";
			int specg; cin >> specg;
			o->ucitajFajlove(*f1, *f2, specg);
			cout << "Fajlovi su uspesno ucitani\n\n";
			ucitano = true;
		}
		catch (GNemaFajla g) { cout << g.what(); }
	}
	return o;
}

int main() {
	//Olimpijade* o=new Olimpijade(); //napisi dealokator
	string path = "D:\\4. semestar\\POOP\\L1\\Tekst\\";
	string f1, f2;
	Olimpijade* o=ucitaj( &f1, &f2, path);
	/*bool ucitano = false;
	while (!ucitano) {
		try {
			cout << "Unesite ime fajla za citanje podataka o dogadjajima i o sportistima\n";
			cin >> f1 >> f2;
			f1 = path + f1;
			f2 = path + f2;
			cout << "Ako zelite uneti samo podatke za specificnu godinu, upisite je, a ako ne upisite 0\n";
			int specg; cin >> specg;
			o->ucitajFajlove(f1, f2, specg);
			cout << "Fajlovi su uspesno ucitani\n\n";
			ucitano = true;
		}
		catch (GNemaFajla g) { cout << g.what(); }
	}*/
	while (1) {
		meni();  int izbor; cin >> izbor;
		try {
			if (izbor == 1 || izbor == 2 || izbor == 3) {
				bool u[4] = { false,false,false,false };
				Sport* s = new Sport("");
				Drzava* d = new Drzava("");
				int g = 0;
				Medalja* m = new Medalja("", false);
				filteriS(u, s, d, &g, m, o);
				if (izbor == 1)
					cout << o->brojUcesnika(u, s, d, g, m) << endl;
				if (izbor == 2)
					cout << o->prosecnaVisina(u, s, d, g, m) << endl;
				if (izbor == 3)
					cout << o->prosecnaTezina(u, s, d, g, m) << endl;
			}
			else if (izbor == 4) {
				bool u[3] = { false,false,false };
				Sport* s = new Sport("");
				bool t = false;
				int g = 0;
				filteriD(u, s, &g, &t);
				cout << o->brojDisciplina(u, t, s, g) << endl;
			}
			else if (izbor == 5) {
				cout << "Unesite ime drzave\n";
				string naziv; char c;  cin.get(c);
				getline(cin, naziv);
				if (!o->imaLiDrzava(naziv)) throw GDrzava();
				else cout << o->dohvatiDrzavu(naziv)->sportoviSaMedaljom() << endl;
			}
			else if (izbor == 6) {
				cout << "Unesite godinu i godisnje doba odrzavanja olimpijade\n";
				string kada; char c; cin.get(c); getline(cin, kada);
				o->dohvatiOlimpijadu(kada)->najboljeDrzave();
			}
			else if (izbor == 7) {
				o->drzaveSaNajboljimUspehom();
			}
			else if (izbor == 8) {
				o->najmladjiOsvajaci();
			}
			else if (izbor == 9) {
				o->paroviDrzavaSportista();
			}
			else if (izbor == 10) { //da se implementira ova metoda do kraja
				cout << "Unesite godinu i godisnje doba prve, a zatim i druge olimpijade\n";
				string doba1, doba2; int godina1, godina2;
				cin >> godina1 >> doba1 >> godina2 >> doba2;
				o->presekSportista2Ol(to_string(godina1) + " " + doba1, to_string(godina2) + " " + doba2);
			}
			else if (izbor == 11) {
				string naziv, doba; int godina;
				cout << "Koja drzava?\n";
				char c; cin.get(c); getline(cin, naziv);
				cout<< "Koja godina i koje godisnje doba ? \n";
				cin >>  godina >> doba;
				o->dohvatiDrzavu(naziv)->timoviSaOlimpijade(to_string(godina) + " " + doba);
			}
			else if (izbor == 12) {
				o->ispisiSveGradove();
			}
			else if (izbor == 13) {
				delete o;
				o=ucitaj( &f1, &f2, path);
			}
			else if (izbor == 14) {
				cout << "Jeste li sigurni da zelite da izadjete iz programa?\nDA/NE\n";
				string daNe; cin >> daNe;
				if (daNe == "DA") break;
			}
			else cout << "Ta opcija ne postoji\n";
		}
		catch (exception g) {
			cout << g.what();
		}
	}

	return 0;
}

void meni() {
	cout << "Izaberite jednu od funkcionalnosti programa:\n"
		"1. Broj sportista na svim olimpijadama\n"
		"2. Prosecna visina sportista\n"
		"3. Prosecna tezina sportista\n"
		"4. Sve discipline na olimpijadi\n"
		"5. Broj sportova u kojima je neka drzava osvojila bar jednu medalju\n"
		"6. Tri najbojle drzave po uspehu na nekoj olimpijadi\n"
		"7. Drzave koje su bar na jednoj olimpijadi bile najbolje\n"
		"8. Najmljadjih 10 ucesnika koji su osvojili medalju na svojoj prvoj olimpijadi\n"
		"9. Svi sportisti koji su osvojili bar 1 medalju u timu i bar 1 individualno\n"
		"10. Svi sportisti sa zadatog para olimpijada\n"
		"11. Svi timovi koje je drzava imala na nekoj olimpijadi\n"
		"12. Svi gradovi u kojima je odrzana olimpijada\n"
		"13. Novo ucitavanje\n"
		"14. Kraj programa\n\n";
}

void filteriS(bool u[4], Sport* s, Drzava* d, int* g, Medalja* m, Olimpijade* o) {
	cout << "U jednom redu unesite brojeve opcija koje zelite\n";
	cout << "Filtrirajte po:\n"
		"1. Sportu\n2. Drzavi\n3. Godini odrzavanja\n"
		"4. Osvojenoj medalji\n"
		"0. Ni po cemu\n\n";
	char c; cin.get(c);
	while (cin.get(c)) {
		if (c == '1') u[0] = true;
		if (c == '2') u[1] = true;
		if (c == '3') u[2] = true;
		if (c == '4') u[3] = true;
		if (c == '\n') break;
	} string naziv;
	if (u[0]) {
		cout << "Unesite naziv sporta\n"; 
		getline(cin, naziv);
		*s = Sport(naziv);
	}
	if (u[1]) {
		cout << "Unesite naziv drzave za koju nastupa sportista\n";
		getline(cin, naziv);
		if (!o->imaLiDrzava(naziv)) throw GDrzava();
		*d = *o->dohvatiDrzavu(naziv);
	}
	if (u[2]) {
		cout << "Unesite godinu odrzavanja olimpijade\n";
		cin >> *g; 
		if (u[3]) { char c; cin.get(c); }
	}
	if (u[3]) {
		cout << "Unesite Gold / Silver / Bronze\n";
		getline(cin, naziv);
		if (naziv != "Gold" && naziv != "Silver" && naziv != "Bronze") throw GMedalja();
		*m = Medalja(naziv, false);
	}
}

void filteriD(bool u[3], Sport* s, int* g, bool* tim) {
	cout << "U jednom redu unesite brojeve opcija koje zelite\n";
	cout << "Filtrirajte po:\n"
		"1. Sportu\n2. Godini odrzavanja\n3. Tipu dogadjaja\n"
		"0. Ni po cemu\n\n";
	char c; cin.get(c);
	while (cin.get(c)) {
		if (c == '1') u[0] = true;
		if (c == '2') u[1] = true;
		if (c == '3') u[2] = true;
		if (c == '\n') break;
	} string naziv;
	if (u[0]) {
		cout << "Unesite naziv sporta\n";
		getline(cin, naziv);
		Sport* s1 = new Sport(naziv);
		*s = *s1;
		delete s1;
	}
	if (u[2]) {
		cout << "Unesite 0 ako je dogadjaj individualan, a 1 ako je timski\n";
		cin >> *g;
		if (*g >= 1) *tim = true;
		else *tim = false;
		*g = 0;
	}
	if (u[1]) {
		cout << "Unesite godinu odrzavanja olimpijade\n";
		cin >> *g;
	}
}