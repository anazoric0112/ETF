#include "rec.h"

Rec::Rec(string niska) {
	rec = "";
	Skup mala("qwertyuioplkjhgfdsazxcvbnm");
	Skup velika("QWERTYUIOPLKJHGFDSAZXCVBNM");
	for (int i = 0; i < niska.length(); i++) {
		if (mala(niska[i]) || velika(niska[i])) {
			rec = rec + niska[i];
		}
	}
}

int Rec::operator~() {
	Skup samoglasnici("aeiouAEIOU");
	Skup sonanti("lrnLRN");
	int slogovi = 0;
	for (int i = 0; i < rec.length(); i++) {
		if (samoglasnici(rec[i])) slogovi++;
		else if (sonanti(rec[i]) && (
			(i > 0 && !samoglasnici(rec[i - 1])) &&
			(i < rec.length() - 1 && !samoglasnici(rec[i + 1]))
			)) {
			slogovi++;
		}
	}
	return slogovi;
}
int Rec::operator+() {
	return rec.length();
}

int Rec::operator()(int n){
	if (n < 0) n += ~*this;
	Skup samoglasnici("aeiouAEIOU");
	Skup sonanti("lrnLRN");
	int slog = -1;
	for (int i = 0; i < rec.length(); i++) {
		if (samoglasnici(rec[i])) slog++;
		else if (sonanti(rec[i]) && (
			(i > 0 && !samoglasnici(rec[i - 1])) &&
			(i < rec.length() - 1 && !samoglasnici(rec[i + 1]))
			)) {
			slog++;
		}
		if (slog == n) return i;
	}
	return -1;
}

bool operator^(Rec r1, Rec r2) {
	if (!~r1 || !~r2) {
		cout << "iju\n"; return false;
	}
	if (~r1 == 1 || ~r2 == 1) {
		int p1 = r1(-1);
		int p2 = r2(-1);
		while (p1 < +r1 && p2 < +r2) {
			if ((r1.rec[p1] != r2.rec[p2]) && (abs(r1.rec[p1] - r2.rec[p2]) != 32)) {
				return false;
			}
			p1++; p2++;
		}
		return true;
	}
	int p1 = r1(-2);
	int p2 = r2(-2);
	while (p1 < +r1 && p2 < +r2) {
		if ((r1.rec[p1] != r2.rec[p2]) && (abs(r1.rec[p1] - r2.rec[p2]) != 32)) {
			return false;
		}
		p1++; p2++;
	}
	return true;
}
istream& operator>>( istream& is, Rec& r) {
	Skup mala("qwertyuioplkjhgfdsazxcvbnm");
	Skup velika("QWERTYUIOPLKJHGFDSAZXCVBNM");
	string ucitano;
	is >> ucitano;
	r.rec="";
	for (int j = 0; j < ucitano.length(); j++) {
		if (mala(ucitano[j]) || velika(ucitano[j])) {
			r.rec = r.rec + ucitano[j];
		}
	} 
	return is;

}
ostream& operator<<(ostream& os, const Rec& r) {
	return os << r.rec;
}