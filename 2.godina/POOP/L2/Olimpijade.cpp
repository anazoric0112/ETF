#include "Olimpijade.h"


Olimpijade::~Olimpijade()
{
	for (auto d : drzave) delete d.second;
	for (auto d : discipline) delete d.second;
	for (auto s : sportisti) delete s.second;
	for (auto o : olimpijade) delete o.second;
}

bool Olimpijade::imaLiDrzava(string d) const
{
	if (drzave.find(d)!=drzave.end()) return true;
	else return false;
}
bool Olimpijade::imaLiSportista(int id) const
{
	if (sportisti.find(id)!=sportisti.end()) return true;
	else return false;
}
bool Olimpijade::imaLiOlimpijada(string doba, int godina) const
{
	string kada = to_string(godina) + " " + doba;
	if (olimpijade.find(kada)!=olimpijade.end()) return true;
	else return false;
}
bool Olimpijade::imaLiOlimpijada(string kada) const
{
	if (olimpijade.find(kada) != olimpijade.end()) return true;
	else return false;
}
bool Olimpijade::imaLiDisciplina(string d) const
{
	if (discipline.find(d) != discipline.end()) return true;
	else return false;
}

void Olimpijade::dodajDrzavu(string d)
{
	if (!imaLiDrzava(d)) {
		Drzava* nova = new Drzava(d);
		drzave[d] = nova;
	}
}
void Olimpijade::dodajSportistu(int i, int p, Sport& s, Drzava* dr)
{
	if (!dr) throw GDrzava();
	if (!imaLiSportista(i)) {
		Sportista* novi = new Sportista(i, p, s, dr);
		sportisti[i] = novi;
	}
	else {
		sportisti[i]->azurirajPrvuGodinu(p);
	}
	sportisti[i]->dodajDrzavu(dr);
	sportisti[i]->dodajSport(s);
}
void Olimpijade::postaviDetaljeSportisti(int id,string v, string t, string g, char p, string i)
{	
	if (imaLiSportista(id)) sportisti[id]->postaviDetalje(v, t, g, p, i);
}
void Olimpijade::dodajGrad(string g)
{
	gradovi.insert(g);
}
void Olimpijade::dodajOlimpijadu(string doba, int godina,  string grad)
{	
	if (doba != "Summer" && doba != "Winter") throw GDoba();
	if (!imaLiOlimpijada(doba, godina)) {
		Olimpijada* o = new Olimpijada(godina, doba);
		string kada = to_string(godina) + " " + doba;
		olimpijade[kada] = o;
		olimpijadeGde[kada] = grad;
		dodajGrad(grad);
	}
}
void Olimpijade::dodajDisciplinu(string d, bool t, Sport sport)
{
	if (!imaLiDisciplina(d)) {
		bool i = !t;
		Disciplina* disc = new Disciplina(sport, d, t, i);
		discipline[d] = disc;
	}
	if (t) discipline[d]->postaviT();
	else discipline[d]->postaviI();
}

void Olimpijade::dodajGodinuDisciplini(int godina, string disc)
{
	if (!imaLiDisciplina(disc)) throw GDisciplina();
	discipline[disc]->dodajGodinu(godina);
}

void Olimpijade::dodajGodinuSportisti(int godina, int id)
{
	if (!imaLiSportista(id)) throw GSportista();
	sportisti[id]->dodajGodinu(godina);
}
void Olimpijade::dodajMedaljuSportisti(int godina, Medalja* m, int id)
{
	if (!imaLiSportista(id)) throw GSportista();
	if (!m) return;
	sportisti[id]->dodajMedalju(m,godina);
}

void Olimpijade::dodajMedaljuDrzavi(Sport sport, string drzava,Medalja* m)
{
	if (!m) return;
	if (!imaLiDrzava(drzava)) throw GDrzava();
	drzave[drzava]->dodajMedalju(*m, sport);
}
void Olimpijade::dodajTimDrzavi(Tim* tim, string drzava, int godina, string doba)
{
	if (doba != "Summer" && doba != "Winter") throw GDoba();
	if (!imaLiDrzava(drzava)) throw GDrzava();
	drzave[drzava]->dodajTim(tim, godina, doba);
}

void Olimpijade::dodajDisciplinuOlimpijadi(string doba, int godina, string disciplina)
{
	if (!imaLiOlimpijada(doba,godina)) throw GGodina();
	string kada = to_string(godina) + " " + doba;
	Olimpijada* o = olimpijade[kada];
	o->dodajDisc(discipline[disciplina]);
}
void Olimpijade::dodajSportistuOlimpijadi(string kada, int id)
{
	if (!imaLiOlimpijada(kada)) throw GGodina();
	if (!imaLiSportista(id)) throw GSportista();
	olimpijade[kada]->dodajSp(sportisti[id]);
}
void Olimpijade::dodajDrzavuOlimpijadi(string kada, string drzava)
{
	if (!imaLiOlimpijada(kada)) throw GGodina();
	if (!imaLiDrzava(drzava)) throw GDrzava();
	olimpijade[kada]->dodajDrz(drzave[drzava]);
}
void Olimpijade::dodajMedaljuOlimpijadi(string kada, string drzava, Medalja* m)
{
	if (!m) return;
	if (!imaLiOlimpijada(kada)) throw GGodina();
	if (!imaLiDrzava(drzava)) throw GDrzava();
	olimpijade[kada]->dodajMed(drzave[drzava], *m);
}

vector<function<bool(Sportista*)>> Olimpijade::filtrirajIspisSportista(bool u[4],
	Sport* sport, Drzava* drzava, int godina, Medalja* medalja) const
{
	vector <function<bool(Sportista*)>> fs;
	if (u[0]) {
		auto f1=[sport](Sportista* s) {
			return s->imaLiSport(sport->dohvNaziv());
		};
		fs.push_back(f1);
	}
	if (u[1]) {
		auto f2 = [drzava](Sportista* s) {
			return s->dohvDrzavu() == *drzava;
		};
		fs.push_back(f2);
	}
	if (u[2]) {
		auto f3 = [godina](Sportista* s) {
			return s->ucestvovaoGodine(godina);
		};
		fs.push_back(f3);
	}
	if (u[3]) {
		auto f4 = [medalja](Sportista* s) {
			return s->osvojioMedalju(*medalja);
		};
		fs.push_back(f4);
	}
	return fs;
}

vector<function<bool(Disciplina*)>> Olimpijade::filtrirajIspisDisciplina(bool u[3], bool tim, Sport* sport, int godina) const
{
	vector<function<bool(Disciplina*)>> fs;
	if (u[0]) {
		auto f1 = [sport](Disciplina* s) {
			return s->dohvSport() == *sport;
		};
		fs.push_back(f1);
	}
	if (u[1]) {
		auto f2 = [godina](Disciplina* s) {
			return s->imaLiGodine(godina);
		};
		fs.push_back(f2);
	}
	if (u[2]) {
		auto f3 = [tim](Disciplina* s) {
			if (tim) return s->jelTimska();
			else return s->jelIndiv();
			//return s->jelTimska() == tim;
		};
		fs.push_back(f3);
	}
	return fs;
}

int Olimpijade::brojUcesnika(bool u[4],
	Sport* sport, Drzava* drzava, int godina, Medalja* medalja) const
{	
	vector<function<bool(Sportista*)>> fs = filtrirajIspisSportista(u,sport,drzava,godina,medalja);
	int c = 0;
	for (auto sp : sportisti) {
		bool flag = false;
		for (auto f : fs) {
			if (!f(sp.second)) {
				flag = true; break;
			}
		} if (!flag) c++;
	} 
	return c;
}

double Olimpijade::prosecnaTezina(bool u[4],
	Sport* sport, Drzava* drzava, int godina, Medalja* medalja) const
{
	vector<function<bool(Sportista*)>> fs = filtrirajIspisSportista(u, sport, drzava, godina, medalja);
	int c = 0;
	double t = 0;
	for (auto sp : sportisti) {
		bool flag = false;
		for (auto f : fs) {
			if (!f(sp.second)) {
				flag = true; break;
			}
		} if (!flag && sp.second->dohvTezinu() != -1) { c++; t += sp.second->dohvTezinu(); }
	}
	return c != 0 ? t / c : 0;
}

double Olimpijade::prosecnaVisina(bool u[4],
	Sport* sport, Drzava* drzava, int godina, Medalja* medalja) const
{
	vector<function<bool(Sportista*)>> fs = filtrirajIspisSportista(u, sport, drzava, godina, medalja);
	int c = 0;
	double v = 0;
	for (auto sp : sportisti) {
		bool flag = false;
		for (auto f : fs) {
			if (!f(sp.second)) {
				flag = true; break;
			}
		} if (!flag && sp.second->dohvVisinu() !=-1) { c++; v += sp.second->dohvVisinu(); }
	}
	return c != 0 ? v / c : 0;
}

int Olimpijade::brojDisciplina(bool u[3], bool tim, Sport* sport, int godina) const
{
	vector<function<bool(Disciplina*)>> fs = filtrirajIspisDisciplina(u, tim, sport, godina);
	int c = 0;
	for (auto d: discipline) {
		bool flag = false;
		for (auto f : fs) {
			if (!f(d.second)) {
				flag = true; break;
			}
		} if (!flag) c++;
		if (!flag && d.second->jelIndiv() && d.second->jelTimska()) c++;
	}
	return c;
}

//ne znam treba li mi?
Olimpijada* Olimpijade::dohvatiOlimpijadu(string doba, int godina) const
{
	if (!imaLiOlimpijada(doba, godina)) throw GGodina();
	string kada = to_string(godina) + " " + doba;
	auto o = olimpijade.find(kada);
	return olimpijade.find(kada)->second;
}
Olimpijada* Olimpijade::dohvatiOlimpijadu(string kada) const
{
	if (!imaLiOlimpijada(kada)) throw GGodina();
	auto o = olimpijade.find(kada);
	return olimpijade.find(kada)->second;
}
Drzava* Olimpijade::dohvatiDrzavu(string d) const
{
	if (!imaLiDrzava(d)) throw GDrzava();
	return drzave.find(d)->second;
}

void Olimpijade::ispisiSveGradove() const
{
	for (auto grad : gradovi) cout << grad << endl;
}

void Olimpijade::presekSportista2Ol(string kada1, string kada2) const
{
	Olimpijada* o1 = dohvatiOlimpijadu(kada1);
	Olimpijada* o2 = dohvatiOlimpijadu(kada2);
	o1->presek(o2);
}

void Olimpijade::drzaveSaNajboljimUspehom() const
{
	set <string> najbolje;
	for (auto ol : olimpijade) {
		najbolje.insert(ol.second->najboljaDrzava()->dohvNaziv());
	}
	for (auto n : najbolje) cout << n << endl;
}

void Olimpijade::najmladjiOsvajaci() const
{
	struct poredi {
		bool operator()(const Sportista* s1, const Sportista* s2) const {
			return s1->dohvGodine() < s2->dohvGodine();
		}
	};
	/*auto poredi = [](const Sportista* s1, const Sportista* s2) {
		return s1->dohvGodine() < s2->dohvGodine();
	};*/
	multiset <Sportista*, poredi> najmladji;
	for (auto s : sportisti) {
		if (s.second->prvoUcesce()) najmladji.insert(s.second);
	} int i = 0;
	for (auto it = najmladji.begin(); it != najmladji.end() && i<10; ++it) {
		if ((*it)->dohvGodine() <= 0) continue;
		cout << **it; i++;
	}
}

void Olimpijade::paroviDrzavaSportista() const
{
	for (auto s : sportisti) {
		for (auto d = s.second->pocetakDr(); d != s.second->krajDr(); d++) {

			if (s.second->timIndiv()) {
				string dd = "(" + *d + ")";
				cout << left << setw(30) << dd;
				cout << *s.second;
			}
		}
	}
}

void Olimpijade::ucitajFajlove(string fajl1, string fajl2, int zadataGodina) 
{
	ifstream f(fajl1);
	ifstream f2(fajl2);
	if (!f || !f2) throw GNemaFajla();
	string red;
	regex timovi("([0-9]*) ([a-zA-Z]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!\\[(('[0-9]*',? ?)+)\\]!([a-zA-Z]*)");
	regex indiv("([0-9]*) ([a-zA-Z]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([0-9]*)!([a-zA-Z]*)");
	regex tim("([0-9]+)");
	smatch sm, tm; 
	while (getline(f, red)) {
		vector <int> id;
		bool timski = 0;
		//ako je timski sport 
		if (zadataGodina && red.rfind(to_string(zadataGodina), 0) != 0) continue;
		if (!regex_match(red, sm, indiv)) {
			if (!regex_match(red, sm, timovi)) {
				cout << red << endl; continue;
			} timski = 1;
			string identifikatori = sm[8];
			while (regex_search(identifikatori, tm, tim)) {
				id.push_back(stoi(tm.str(0)));
				identifikatori = tm.suffix().str();
			}
		}
		else id.push_back(stoi(sm[8])); //id-jevi za tim napravljeni !!!
		//izvlace se prosti podaci iz regexa
		int godina = stoi(sm[1]);
		if (zadataGodina && godina != zadataGodina) continue;
		string doba = sm[2];
		string grad = sm[3];
		string sportStr = sm[4];
		string disciplinaStr = sm[5];
		string drzavaStr = sm[7];
		string kada = to_string(godina) + " " + doba;

		Sport* sport = new Sport(sportStr);
		Medalja* medalja = nullptr;

		if (timski && sm[10] != "") {
			medalja = new Medalja(sm[10], true);
		}
		else if (!timski && sm[9] != "") {
			medalja = new Medalja(sm[9], false);
		}
		dodajDisciplinu(disciplinaStr, timski, *sport);
		dodajGodinuDisciplini(godina, disciplinaStr);

		dodajDrzavu(drzavaStr);
		dodajGrad(grad);

		Tim* ekipa = new Tim(discipline[disciplinaStr], timski);
		for (auto idSp : id) {
			dodajSportistu(idSp, godina, *sport, drzave[drzavaStr]);
			ekipa->dodajSportistu(sportisti[idSp]);
			dodajMedaljuSportisti(godina, medalja, idSp);
			dodajGodinuSportisti(godina, idSp);
		}

		dodajMedaljuDrzavi(*sport, drzavaStr, medalja);
		dodajTimDrzavi(ekipa, drzavaStr, godina, doba);

		dodajOlimpijadu(doba, godina, grad);
		dodajDisciplinuOlimpijadi(doba, godina, disciplinaStr);
		for (auto idSp : id) {
			dodajSportistuOlimpijadi(kada, idSp);
		}
		dodajDrzavuOlimpijadi(kada, drzavaStr);
		dodajMedaljuOlimpijadi(kada, drzavaStr,medalja);
	}

	f.close();
	regex sablon("([0-9]+)!([^!]*)!(M|F)!([^!]*)!([^!]*)!([^\n]*)\0");
	int prev = 0;
	while (getline(f2, red)) {
		if (!regex_match(red, sm, sablon)) continue;
		string polStr = sm[3];
		char pol = polStr[0];
		postaviDetaljeSportisti(stoi(sm[1]), sm[5], sm[6], sm[4], pol, sm[2]);
	}
	f2.close();
}


