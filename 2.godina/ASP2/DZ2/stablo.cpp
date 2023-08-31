#include "stablo.h"

//NE DIRAJ NI OVO
bool findInd(vector <string> v, string r) {
	vector<string>::iterator l;
	l = lower_bound(v.begin(), v.end(),r);
	if (r > *(v.end() - 1)) return false;
	else if (*l == r) return true;
	else return false;
	
}

//NE DIRAJ OVO
int findInd2(vector <string> v, string r) {
	vector<string>::iterator l;
	l = lower_bound(v.begin(), v.end(), r);
	if (l == v.end()) return v.size();
	for (int i = 0; i < v.size(); i++) {
		if (v[i] == *l) return i;
	} 
}

Stablo::Stablo(const Stablo& s) {
	copy(s);
}
Stablo::Stablo(Stablo&& s) {
	move(s);
}
Stablo::~Stablo() {
	dlt();
}

Stablo& Stablo::operator=(const Stablo& s) {
	if (this != &s) {
		dlt(); copy(s);
	} return *this;
}
Stablo& Stablo::operator=(Stablo&& s) {
	if (this != &s) {
		dlt(); move(s);
	} return *this;
}

void Stablo::copy(const Stablo& s) {
	m = s.m;
	maxk = s.maxk;
	min = s.min;
	if (!s.koren) {
		koren = nullptr; return;
	}
	queue <Cvor*> q,qn;
	Cvor* tr = s.koren;
	q.push(tr); 
	Cvor* novi = new Cvor;
	koren = novi;
	koren->otac = nullptr;
	qn.push(novi);
	while (q.size()) {
		tr = q.front(); q.pop();
		novi = qn.front(); qn.pop();
		novi->maxc = tr->maxc;
		novi->minc = tr->minc;
		for (int i = 0; i < tr->sinovi.size(); i++) {
			Cvor* sin = new Cvor;
			sin->otac = novi;
			novi->sinovi.push_back(sin);
			qn.push(sin);
			q.push(tr->sinovi[i]);
			if (i < tr->reci.size()) {
				novi->reci.push_back(tr->reci[i]);
			}
		}

	}
}
void Stablo::move(Stablo& s) {
	m = s.m;
	maxk = s.maxk;
	min = s.min;
	koren = s.koren;
	koren = nullptr;
}
void Stablo::dlt() {
	if (!koren) return;
	Cvor* tr = koren;
	queue <Cvor*> q;
	q.push(tr);
	while (q.size()) {
		tr = q.front(); q.pop();
		for (int i = 0; i < tr->sinovi.size(); i++) {
			q.push(tr->sinovi[i]);
		} delete tr;
	}
	koren = nullptr;
}

//vraca true ako rec postoji u stablu i false u suprotnom
bool Stablo::nadjiRec(string rec) {
	if (!koren) return false;
	Cvor* tr = koren;
	vector <string>::iterator it;
	while (1) {
		int ind = findInd2(tr->reci, rec);

		if (ind < tr->reci.size() && tr->reci[ind] == rec) return true;
		if (tr->sinovi.size()) {
			tr = tr->sinovi[ind];
		}
		else return false;

	}return false;
}

//STA JE BRE OVO
int Stablo::reciIspred(string rec) {
	if (!nadjiRec(rec)) return 0;
	int x=0;
	Cvor* tr = koren;
	stack <pair<Cvor*,int>> st;
	st.push({ tr,0 });
	map <Cvor*, bool> mapa;
	while (tr->sinovi.size()) {
		for (int i = tr->sinovi.size() - 1; i >= 0; i--) st.push({ tr->sinovi[i],i });
		mapa[tr] = 1;
		tr = tr->sinovi[0];
	}
	while (st.size()) {
		tr = st.top().first; 
		int ind = st.top().second;
		st.pop();
		if (!tr->sinovi.size()) {
			for (auto str : tr->reci) {
				if (str == rec) return x;
				x++;
			}
		}
		//if (tr->otac) if (ind!=tr->otac->reci.size())cout << endl<<"LMAO "<<tr->otac->reci[ind] << endl;
		//cout << "LOL " << ind << endl;
		if (tr->otac) if (ind!=tr->otac->reci.size()) if (tr->otac->reci[ind] == rec) return x;
		if (tr->otac) if (ind!=tr->otac->reci.size())x++;
		if (mapa[tr]) continue;
		while (tr->sinovi.size()) {
			for (int i = tr->sinovi.size() - 1; i >= 0; i--) st.push({ tr->sinovi[i],i });
			mapa[tr] = 1; tr = tr->sinovi[0];
		}
	}

	return x;
}

//ispisuje stablo u level orderu
ostream& operator<<(ostream& os, const Stablo& s) {
	if (!s.koren) return os;
	queue <pair<Stablo::Cvor*,int>> q;
	q.push({ s.koren,0 });
	Stablo::Cvor* tr;
	int x = (const_cast<Stablo&>(s)).reciIspred(*(s.koren->reci.end()-1));
	for (int i = 0; i < x; i++) os << "     ";
	for (auto str : s.koren->reci) os << str << " ";
	os << endl<<endl;
	int prev = 0;
	while (q.size()) {
		tr = q.front().first; 
		int h = q.front().second;
		q.pop();
		if (prev != h) os << endl << endl;
		prev = h;
		for (int i = 0; i < tr->sinovi.size();i++) {
			Stablo::Cvor* sin = tr->sinovi[i];
			int x = (const_cast<Stablo&>(s)).reciIspred(*(sin->reci.end()-1));
			if (i && sin->sinovi.size())
				x -= (const_cast<Stablo&>(s)).reciIspred((tr->sinovi[i - 1]->reci[0]));
			q.push({ sin,h + 1 });
			if (!sin->sinovi.size()) x = 0;
			for (int j = 0; j < x/2; j++) os << "  "; os << "| ";
			for (int j = 0; j < x / 2; j++) os << "  ";
			for (auto rec : sin->reci) os << rec << " ";
		}
		
	}return os<<endl;
}

//dodeljuje vrednosti parametara cvoru
void assignV(Stablo::Cvor* c,Stablo::Cvor* otac, int mxc, int mnc) {
	if (!c) return;
	c->maxc = mxc;
	c->minc = mnc;
	c->otac = otac;
}


bool Stablo::dodajRec(string rec) {
	if (koren && nadjiRec(rec)) {
		return false;
	}
	Cvor* tr = koren;
	int ind,indsin=-1;
	//ako nema korena poseban slucaj
	if (!koren) {
		koren = new Cvor;
		assignV(koren, nullptr, maxk, 2);
		koren->reci.push_back(rec);
		return true;
	}

	//ako je koren jedini cvor poseban slucaj
	if (koren->sinovi.size() == 0) {
		vector <string> nov; //vektor u koji ce se redjati reci
		int f = 0; //da li je nova rec ubacena u vektor
		for (int i = 0; i < koren->reci.size(); i++) {
			if (koren->reci[i] > rec && !f) {
				nov.push_back(rec); f = 1;
			}
			nov.push_back(koren->reci[i]);
		}
		if (!f) nov.push_back(rec);
		koren->reci = nov; //sad je taj novi vektor u korenu

		//ako je prekoracen broj cvorova koren se lomi na 3 dela tj jedan koren i dva sina
		if (koren->reci.size() > maxk-1) {
			Cvor* c1 = new Cvor, *c2 = new Cvor;
			koren->sinovi.push_back(c1);
			koren->sinovi.push_back(c2);
			assignV(c1, koren, m, min);
			assignV(c2, koren, m, min);
			int br1 = (koren->reci.size() - 1) / 2;
			int br2 = koren->reci.size() - 1 - br1;
			for (int i = 0; i < br1; i++) c1->reci.push_back(koren->reci[i]);
			for (int i = 0; i < br2; i++) c2->reci.push_back(koren->reci[i + br1 + 1]);
			string rec2 = koren->reci[br1];
			for (int i = 0; i < br1 + br2 + 1; i++) koren->reci.pop_back();
			koren->reci.push_back(rec2);
		}
		return true;
	}


	//ako koren nije jedini cvor u stablu:

	//ova petlja pronalazi mesto na kome treba da se nadje nova rec
	while (tr->sinovi.size()) {
		ind = findInd2(tr->reci, rec);
		tr = tr->sinovi[ind];
		indsin = ind;
	}
	ind = findInd2(tr->reci, rec);

	//ukoliko tu gde treba da se ubaci ima mesta, ubaci se na odgovarajuce mesto
	if (tr->reci.size() != tr->maxc-1) {
		vector <string> nov; int f = 0;
		for (int i = 0; i < tr->reci.size(); i++) {
			if (tr->reci[i] > rec && !f) { 
				nov.push_back(rec); f = 1;
			}
			nov.push_back(tr->reci[i]);
		}
		if (!f) nov.push_back(rec);
		tr->reci = nov; 
		return true;
	}

	//ako nema mesta tu gde treba da se ubaci
	if (tr->reci.size() == tr->maxc - 1) {
		do {
			for (int i = 0; i < tr->otac->sinovi.size(); i++) {
				if (tr == tr->otac->sinovi[i]) { indsin = i; break; }
			}

			//brat je cvor od koga se pozajmljuje, prvo se gleda desno pa levo od cvora
			int brat = -1;
			if ((indsin + 1 < tr->otac->sinovi.size())
				&& (tr->otac->sinovi[indsin + 1]->reci.size() < tr->otac->sinovi[indsin + 1]->maxc - 1))
				brat = indsin + 1;
			else if ((indsin - 1 >= 0)
				&& (tr->otac->sinovi[indsin - 1]->reci.size() < tr->otac->sinovi[indsin - 1]->maxc - 1))
				brat = indsin - 1;

			//ako postoji brat u koji moze da se ubaci
			if (brat != -1) {
				int vel = 2; //vel je velicina vektora u koji ce se redjati reci
				bool nlist = false;
				if (tr->sinovi.size()) nlist = true;
				if (tr->sinovi.size()) vel--;
				vel += tr->otac->sinovi[indsin]->reci.size();
				vel += tr->otac->sinovi[brat]->reci.size();
				vector <string> novi(vel); 
				int j = 0, f = 0; //j je indeks za iteraciju kroz vektor
				if (indsin > brat) swap(indsin, brat);

				//reci se ubace u vektor
				for (auto str : tr->otac->sinovi[indsin]->reci) {
					if (!f && !nlist && str > rec) { novi[j++] = rec; f = 1; }
					novi[j++] = str;
				}
				if (!f && !nlist && tr->otac->reci[indsin] > rec) { novi[j++] = rec; f = 1; }
				novi[j++] = tr->otac->reci[indsin];
				for (auto str : tr->otac->sinovi[brat]->reci) {
					if (!f && !nlist && str > rec) { novi[j++] = rec; f = 1; }
					novi[j++] = str;
				} if (!f && !nlist) novi[j++] = rec;

				int j1 = vel / 2;
				int md = j1 + 1;
				int j2 = vel - j1 - 1;
				int iv = 0, iv2 = 0;
				//ubacivanje reci u prvi cvor
				while (tr->otac->sinovi[indsin]->reci.size()) tr->otac->sinovi[indsin]->reci.pop_back();
				for (int ii = 0; ii < j1; ii++) tr->otac->sinovi[indsin]->reci.push_back(novi[iv++]);
				//ubacivanje prvog razdvojnog
				tr->otac->reci[indsin] = novi[iv++];
				//ubacivanje reci u drugi cvor
				while (tr->otac->sinovi[brat]->reci.size()) tr->otac->sinovi[brat]->reci.pop_back();
				for (int ii = 0; ii < j2; ii++) tr->otac->sinovi[brat]->reci.push_back(novi[iv++]);
				
				if (nlist) {
					vector <Cvor*> vp; //vektor pokazivaca za slucaj da cvorovi nisu listovi;
					for (int i = 0; i < tr->otac->sinovi[indsin]->sinovi.size(); i++) vp.push_back(tr->otac->sinovi[indsin]->sinovi[i]);
					for (int i = 0; i < tr->otac->sinovi[brat]->sinovi.size(); i++) vp.push_back(tr->otac->sinovi[brat]->sinovi[i]);
					
					while (tr->otac->sinovi[indsin]->sinovi.size()) tr->otac->sinovi[indsin]->sinovi.pop_back();
					for (int ii = 0; ii < tr->otac->sinovi[indsin]->reci.size()+1; ii++) tr->otac->sinovi[indsin]->sinovi.push_back(vp[iv2++]);
					for (int ii = 0; ii < tr->otac->sinovi[indsin]->sinovi.size(); ii++) tr->otac->sinovi[indsin]->sinovi[ii]->otac = tr->otac->sinovi[indsin];

					while (tr->otac->sinovi[brat]->sinovi.size()) tr->otac->sinovi[brat]->sinovi.pop_back();
					for (int ii = 0; ii < tr->otac->sinovi[brat]->reci.size() + 1; ii++) tr->otac->sinovi[brat]->sinovi.push_back(vp[iv2++]);
					for (int ii = 0; ii < tr->otac->sinovi[brat]->sinovi.size(); ii++) tr->otac->sinovi[brat]->sinovi[ii]->otac = tr->otac->sinovi[brat];
					
				}


				return true;
			}

			//ako ne postoji brat u koji moze da se ubaci
			else {

				if (indsin != tr->otac->sinovi.size() - 1) brat = indsin + 1;
				else brat = indsin - 1;
				int vel = 2; //vel je velicina vektora u koji ce se redjati reci
				bool nlist = false;
				if (tr->sinovi.size()) nlist = true;
				if (tr->sinovi.size()) vel--;
				vel += tr->otac->sinovi[indsin]->reci.size();
				vel += tr->otac->sinovi[brat]->reci.size();
				vector <string> novi(vel); //izracunata je potrebna velicina i napravljen je vektor
				int j = 0, f = 0; //j je indeks za iteraciju kroz vektor
				if (indsin > brat) swap(indsin, brat);

				//reci se ubace u vektor
				for (auto str : tr->otac->sinovi[indsin]->reci) {
					if (!f && !nlist && str > rec) { novi[j++] = rec; f = 1; }
					novi[j++] = str;
				}
				if (!f && !nlist && tr->otac->reci[indsin] > rec) { novi[j++] = rec; f = 1; }
				novi[j++] = tr->otac->reci[indsin];
				for (auto str : tr->otac->sinovi[brat]->reci) {
					if (!f && !nlist && str > rec) { novi[j++] = rec; f = 1; }
					novi[j++] = str;
				} if (!f && !nlist) novi[j++] = rec;

				int j1 = floor((2 * m - 2) / 3);
				int j2 = floor((2 * m - 1) / 3);
				int j3 = floor(2 * m / 3);
				int iv = 0, iv2 = 0;
				vector <Cvor*> vp; //vektor pokazivaca za slucaj da cvorovi nisu listovi;
				

				//ubacivanje reci u prvi cvor
				while (tr->otac->sinovi[indsin]->reci.size()) tr->otac->sinovi[indsin]->reci.pop_back();
				for (int ii = 0; ii < j1; ii++) tr->otac->sinovi[indsin]->reci.push_back(novi[iv++]);

				//ubacivanje prvog razdvojnog
				tr->otac->reci[indsin] = novi[iv++];

				//ubacivanje reci u drugi cvor
				while (tr->otac->sinovi[brat]->reci.size()) tr->otac->sinovi[brat]->reci.pop_back();
				for (int ii = 0; ii < j2; ii++) tr->otac->sinovi[brat]->reci.push_back(novi[iv++]);

				//ubacivanje drugog razdvojnog
				tr->otac->reci.push_back(novi[iv++]);
				j = tr->otac->reci.size() - 1;
				while (j != brat) {
					swap(tr->otac->reci[j], tr->otac->reci[j - 1]); j--;
				}

				//pravi se treci cvor
				Cvor* pok = new Cvor;
				assignV(pok, tr->otac, m, min);
				for (int ii = 0; ii < j3; ii++) {
					pok->reci.push_back(novi[iv++]);
				} 
				tr->otac->sinovi.push_back(pok);
				j = tr->otac->sinovi.size() - 1;
				while (j - 1 != brat) {
					swap(tr->otac->sinovi[j], tr->otac->sinovi[j - 1]); j--;
				}

				if (nlist) {
					for (int i = 0; i < tr->otac->sinovi[indsin]->sinovi.size(); i++) vp.push_back(tr->otac->sinovi[indsin]->sinovi[i]);
					for (int i = 0; i < tr->otac->sinovi[brat]->sinovi.size(); i++) vp.push_back(tr->otac->sinovi[brat]->sinovi[i]);

					while (tr->otac->sinovi[indsin]->sinovi.size()) tr->otac->sinovi[indsin]->sinovi.pop_back();
					for (int ii = 0; ii < tr->otac->sinovi[indsin]->reci.size() + 1; ii++) tr->otac->sinovi[indsin]->sinovi.push_back(vp[iv2++]);
					for (int ii = 0; ii < tr->otac->sinovi[indsin]->sinovi.size(); ii++) tr->otac->sinovi[indsin]->sinovi[ii]->otac = tr->otac->sinovi[indsin];

					while (tr->otac->sinovi[brat]->sinovi.size()) tr->otac->sinovi[brat]->sinovi.pop_back();
					for (int ii = 0; ii < tr->otac->sinovi[brat]->reci.size() + 1; ii++) tr->otac->sinovi[brat]->sinovi.push_back(vp[iv2++]);
					for (int ii = 0; ii < tr->otac->sinovi[brat]->sinovi.size(); ii++) tr->otac->sinovi[brat]->sinovi[ii]->otac = tr->otac->sinovi[brat];

					for (int ii = 0; ii < pok->reci.size() + 1; ii++) pok->sinovi.push_back(vp[iv2++]);
					for (int ii = 0; ii < pok->sinovi.size(); ii++) pok->sinovi[ii]->otac = pok;
				}
				tr = tr->otac;
			}

		} while (tr->otac && tr->reci.size() ==tr->maxc);

		//ako je cvor koji treba da se prelama koren 
		if (!tr->otac && tr->reci.size() == maxk) {
			//cout << tr->maxc << endl << endl;
			vector <string> novi = tr->reci;
			vector <Cvor*> vp = tr->sinovi;
			int j1 = tr->reci.size()/2;
			int j2 = tr->reci.size() - 1 - j1;
			int iv = 0, iv2 = 0;

			while (tr->sinovi.size()) tr->sinovi.pop_back();
			while (tr->reci.size()) tr->reci.pop_back();

			Cvor* levi = new Cvor, * desni = new Cvor;
			tr->sinovi.push_back(levi); tr->sinovi.push_back(desni);
			assignV(levi, tr, m, min);
			assignV(desni, tr, m, min);

			//ubacivanje reci i pokazivaca u cvorove
			for (int ii = 0; ii < j1; ii++) levi->reci.push_back(novi[iv++]);
			for (int ii = 0; ii < j1 + 1; ii++) levi->sinovi.push_back(vp[iv2++]);
			tr->reci.push_back(novi[iv++]);
			for (int ii = 0; ii < j2; ii++) desni->reci.push_back(novi[iv++]);
			for (int ii = 0; ii < j2 + 1; ii++) desni->sinovi.push_back(vp[iv2++]);

			for (int i = 0; i < j1 + 1; i++) levi->sinovi[i]->otac = levi;
			for (int i = 0; i < j2 + 1; i++) desni->sinovi[i]->otac = desni;
		}
		return true;
	}
}

bool Stablo::obrisiRec(string rec) {
	if (!koren) return false;
	if (koren && !nadjiRec(rec)) return false;
	Cvor* tr = koren; int ind=0,indsin=0;

	//ako je koren jedini, poseban slucaj
	if (!koren->sinovi.size()) {
		ind = findInd2(koren->reci, rec);
		for (int i = ind; i < koren->reci.size() - 1; i++) {
			swap(koren->reci[i], koren->reci[i + 1]);
		} koren->reci.pop_back(); 
		if (koren->reci.size() == 0) {
			delete koren;
			koren = nullptr;
		}
		return true;
	}

	//trazi se rec u stablu
	while (tr->sinovi.size()) {
		ind = findInd2(tr->reci, rec);
		if (ind == tr->reci.size()) {
			tr = tr->sinovi[ind]; 
			indsin = ind; continue;
		}
		if (tr->reci[ind] == rec) break;
		tr = tr->sinovi[ind];
		indsin = ind;
	}
	ind = findInd2(tr->reci, rec);

	//ako nije list menja se inroder sledbenikom
	if (tr->sinovi.size()) {
		Cvor* tt = tr->sinovi[ind+1];
		while(tt->sinovi.size()) {
			tt = tt->sinovi[0];
		}
		swap(tt->reci[0], tr->reci[ind]);
		 
		if (tr == tt->otac) indsin = ind + 1;
		else indsin = 0;
		tr = tt; ind = 0;
	}
	
	//ako je list dovoljno popunjen rec se samo obrise
	if (tr->reci.size() != min - 1) {
		for (int i = ind; i < tr->reci.size()-1; i++) {
			swap(tr->reci[i], tr->reci[i + 1]);
		} tr->reci.pop_back(); return true;
	}
	// ako nije trazi se prvo pozajmica od brata
	else {
		do {
			if (!tr->otac) break;
			for (int i = 0; i < tr->otac->sinovi.size(); i++)
				if (tr->otac->sinovi[i] == tr) indsin = i;

			int brat = -1; int k = 0;
			if (indsin != tr->otac->reci.size() && tr->otac->sinovi[indsin + 1]->reci.size() != min - 1) {
				brat = indsin + 1;
			}
			else if (indsin != 0 && tr->otac->sinovi[indsin - 1]->reci.size() != min - 1) {
				brat = indsin - 1;
			}
			else if (indsin < tr->otac->reci.size() - 1 && tr->otac->sinovi[indsin + 2]->reci.size() != min - 1) {
				brat = indsin + 2; k = 1;
			}
			else if (indsin > 1 && tr->otac->sinovi[indsin - 2]->reci.size() != min - 1) {
				brat = indsin - 2; k = 1;
			}
			//ima da se pozajmi od nekog brata- nema brisanje

			bool nlist = false;
			if (tr->sinovi.size()) nlist = true;
			if (brat != -1) {

				if (!nlist) for (int i = ind; i < tr->reci.size() - 1 && tr->reci.size(); i++) swap(tr->reci[i], tr->reci[i + 1]);
				if (!nlist && tr->reci.size())tr->reci.pop_back();
				Cvor* br = tr->otac->sinovi[brat], * br2 = nullptr;
				if (k) {
					br2 = tr->otac->sinovi[brat];
					if (brat > indsin) br = tr->otac->sinovi[brat - 1];
					else br = tr->otac->sinovi[brat + 1];
				}
				if (indsin > brat) { //cvor je desno od svog brata
					tr->reci.push_back(tr->otac->reci[indsin - 1]);
					sort(tr->reci.begin(), tr->reci.end());
					tr->otac->reci[indsin - 1] = br->reci[br->reci.size() - 1];
					br->reci.pop_back();
					if (nlist) {
						tr->sinovi.push_back(*(br->sinovi.end() - 1));
						(*(tr->sinovi.end() - 1))->otac = tr;
						br->sinovi.pop_back();
						for (int i = tr->sinovi.size() - 1; i > 0; i--) swap(tr->sinovi[i], tr->sinovi[i - 1]);
					}

					if (k) {
						br->reci.push_back(tr->otac->reci[indsin - 2]);
						sort(br->reci.begin(), br->reci.end());
						tr->otac->reci[indsin - 2] = *(br2->reci.end() - 1);
						br2->reci.pop_back();
						if (nlist) {
							br->sinovi.push_back(*(br2->sinovi.end() - 1));
							(*(br->sinovi.end() - 1))->otac = br;
							br2->sinovi.pop_back();
							for (int i = br->sinovi.size() - 1; i > 0; i--) swap(br->sinovi[i], br->sinovi[i - 1]);
						}

					}
				}
				else { //cvor je levo od svog brata
					tr->reci.push_back(tr->otac->reci[indsin]);
					tr->otac->reci[indsin] = br->reci[0];
					for (int i = 0; i < br->reci.size() - 1; i++) swap(br->reci[i], br->reci[i + 1]);
					br->reci.pop_back();
					if (nlist) {
						tr->sinovi.push_back(br->sinovi[0]);
						(*(tr->sinovi.end() - 1))->otac = tr;
						for (int i = 0; i < br->sinovi.size() - 1; i++) swap(br->sinovi[i], br->sinovi[i + 1]);
						br->sinovi.pop_back();
					}

					if (k) {
						br->reci.push_back(tr->otac->reci[indsin + 1]);
						tr->otac->reci[indsin + 1] = br2->reci[0];
						for (int i = 0; i < br2->reci.size() - 1; i++) swap(br2->reci[i], br2->reci[i + 1]);
						br2->reci.pop_back();
						if (nlist) {
							br->sinovi.push_back(br2->sinovi[0]);
							(*(br->sinovi.end() - 1))->otac = br;
							for (int i = 0; i < br2->sinovi.size() - 1; i++) swap(br2->sinovi[i], br2->sinovi[i + 1]);
							br2->sinovi.pop_back();
						}
					}
				}
				tr = tr->otac;

			}//nema da pozajmi od brata -ima brisanje
			else if (tr->otac->sinovi.size() > 2) {
				int br1 = 0, br2 = 0, iv = 0, iv2 = 0;
				bool nlist = false;
				if (tr->sinovi.size()) nlist = true;
				if (!nlist) for (int i = ind; i < tr->reci.size() - 1 && tr->reci.size(); i++) swap(tr->reci[i], tr->reci[i + 1]);
				if (!nlist && tr->reci.size())tr->reci.pop_back();

				//odredjuje se koja su braca dostupna za spajanje
				if (indsin != tr->otac->sinovi.size() - 1 && indsin) { br1 = indsin - 1; br2 = indsin + 1; }
				else if (indsin) { br1 = indsin - 2; br2 = indsin - 1; swap(br2, indsin); }
				else { br1 = indsin + 1; br2 = indsin + 2; swap(br1, indsin); }

				vector <string> novi; //reci se redjaju u vektor
				for (auto str : tr->otac->sinovi[br1]->reci) novi.push_back(str);
				novi.push_back(tr->otac->reci[br1]);
				for (auto str : tr->otac->sinovi[indsin]->reci) novi.push_back(str);
				novi.push_back(tr->otac->reci[indsin]);
				for (auto str : tr->otac->sinovi[br2]->reci) novi.push_back(str);

				while (tr->otac->sinovi[br1]->reci.size()) tr->otac->sinovi[br1]->reci.pop_back();
				while (tr->otac->sinovi[indsin]->reci.size()) tr->otac->sinovi[indsin]->reci.pop_back();
				Cvor* t = tr->otac->sinovi[br2];

				//cuvaju se pokazivaci
				vector <Cvor*> vp;
				for (auto p : tr->otac->sinovi[br1]->sinovi) vp.push_back(p);
				for (auto p : tr->otac->sinovi[indsin]->sinovi) vp.push_back(p);
				for (auto p : tr->otac->sinovi[br2]->sinovi) vp.push_back(p);
				while (tr->otac->sinovi[br1]->sinovi.size()) tr->otac->sinovi[br1]->sinovi.pop_back();
				while (tr->otac->sinovi[indsin]->sinovi.size()) tr->otac->sinovi[indsin]->sinovi.pop_back();
				while (tr->otac->sinovi[br2]->sinovi.size()) tr->otac->sinovi[br2]->sinovi.pop_back();

				//ubacuju se reci i pokazivaci u cvorove i brise se visak pokazivaca i reci
				int mid = novi.size() / 2;
				for (int i = 0; i < mid; i++) tr->otac->sinovi[br1]->reci.push_back(novi[iv++]);
				if (nlist) for (int i = 0; i < mid + 1; i++) {
					tr->otac->sinovi[br1]->sinovi.push_back(vp[iv2++]);
					tr->otac->sinovi[br1]->sinovi[i]->otac = tr->otac->sinovi[br1];
				}
				tr->otac->reci[br1] = novi[iv++];

				for (int i = br1 + 1; i < tr->otac->reci.size() - 1; i++) swap(tr->otac->reci[i], tr->otac->reci[i + 1]);
				tr->otac->reci.pop_back();

				for (int i = 0; i < novi.size() - mid - 1; i++) tr->otac->sinovi[indsin]->reci.push_back(novi[iv++]);
				if (nlist) for (int i = 0; i < novi.size() - mid; i++) {
					tr->otac->sinovi[indsin]->sinovi.push_back(vp[iv2++]);
					tr->otac->sinovi[indsin]->sinovi[i]->otac = tr->otac->sinovi[indsin];
				}

				for (int i = br2; i < tr->otac->reci.size()+1; i++) swap(tr->otac->sinovi[i], tr->otac->sinovi[i + 1]);
				tr->otac->sinovi.pop_back();
				tr = tr->otac;
				delete t;
			} //ako nema 2 brata -ima brisanje
			else {
				bool nlist = false;
				Cvor* levi = tr->otac->sinovi[0], * desni = tr->otac->sinovi[1];
				if (tr->sinovi.size()) nlist = true;
				if (!nlist) for (int i = ind; i < tr->reci.size() - 1 && tr->reci.size(); i++) swap(tr->reci[i], tr->reci[i + 1]);
				if (!nlist && tr->reci.size())tr->reci.pop_back();
				if (tr->otac->reci.size()) { //ma molim
					levi->reci.push_back(tr->otac->reci[0]);
					tr->otac->reci.pop_back();
				}
				for (auto str : desni->reci) levi->reci.push_back(str);
				while (desni->reci.size()) desni->reci.pop_back();
				if (nlist) {
					//bilo je ind??? i nije bilo otac
					//for (int i = indsin; i < tr->otac->sinovi.size()-1 ; i++) swap(tr->otac->sinovi[i], tr->otac->sinovi[i + 1]);
					//tr->sinovi.pop_back();
					for (auto p : desni->sinovi) levi->sinovi.push_back(p);
					for (int i = 0; i < levi->sinovi.size(); i++) levi->sinovi[i]->otac = levi;
				}
				
				tr = tr->otac;
				delete tr->sinovi[1];
			}
			
		}while (tr->otac && tr->reci.size() < tr->minc - 1);
		if (!tr->otac && !tr->reci.size()) {
			for (auto str : tr->sinovi[0]->reci) tr->reci.push_back(str);
			for (auto str : tr->sinovi[1]->reci) tr->reci.push_back(str);
			sort(tr->reci.begin(), tr->reci.end());
			Cvor* t1 = tr->sinovi[0], * t2 = tr->sinovi[1];
			tr->sinovi.pop_back(); tr->sinovi.pop_back();
			for (auto p : t1->sinovi) tr->sinovi.push_back(p);
			for (auto p : t2->sinovi) tr->sinovi.push_back(p);
			//delete t1; delete t2;
			for (int i = 0; i < tr->sinovi.size(); i++) tr->sinovi[i]->otac = tr;

		}
	}return true;
}
