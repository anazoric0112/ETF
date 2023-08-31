#include <iostream>
#include <fstream>
#include <map>
#include <set>
#include <vector>
#include <string>
#include <cstring>
#include <string.h>
#include <stdio.h>
#include <stack>
#include <stdlib.h>
#include <queue>
#include <algorithm>
using namespace std;

struct St{
    string rec,prevod;
    struct St* l,* r,* u;
};

map <string,multiset<string>> m;
map <string,int> ct;
int nema=1;

void meni();
St* ks(string rec, string prevod, St* otac);
void dodaj(St* st, string rec, string pr);
St* nadji(St* st,string rec);
St* pr(St* st);
void brisi(St* st, string rec);
string prefiks(St* st, string pref,int duzina);
void ds(St* st);
void lord(St*st);


St* ks(string rec, string prevod, St* otac){
    St* s=new St;
    s->l=s->r=nullptr;
    s->rec=rec;
    s->prevod=prevod;
    s->u=otac;
    ct[rec]++;
    m[rec].insert(prevod);
    return s;
}

void dodaj(St* st, string rec, string pr){
    bool isti=0; St* o=nullptr, *s=st; 
    while (s){
        o=s;
        if (s->rec>rec) s=s->l;
        else if (s->rec<rec) s=s->r; 
        else {isti=1;break;}
    }
    if (s==nullptr) {
        s=ks(rec,pr,o);
        if (o->rec>rec) o->l=s;
        else o->r=s;
    } else if (isti){
        St* p=s->l,*pom=s->l;
        while (p) {o=p;p=p->r;}
        p=ks(rec,pr,o);
        if (pom==nullptr) s->l=p;
        else o->r=p;
    }
}

St* nadji(St* st,string rec){
    St* s=st;
    while (s){
        if (s->rec>rec) s=s->l;
        else if (s->rec<rec) s=s->r;
        else break;
    } 
    return s;
}

string prefiks(St* st, string rec2, int duzina){
    int d=256; duzina=max(duzina,1);
    string rez="",pref;
    stack <St*> stek;   St* c=st;
    while (c) {stek.push(c); c=c->l;}
    while(stek.size()){
        c=stek.top(); stek.pop();
        pref=rec2.substr(0,duzina);
        if (c->rec.find(pref,0)==0 && c->rec.length()<d){
            d=c->rec.length();
            rez=c->rec;
        } c=c->r;
        while (c){
            stek.push(c);
            c=c->l;
        }
    }  return rez;
}

void brisi(St** st, string rec){
    St* t,*o;
    for (int i=0;i<ct[rec];i++){
        t=nadji(*st,rec); 
        m[t->rec].erase(t->prevod);
        if (!t->l && !t->r){ //prvi slucaj
            if (t->u) {
                if (t->u->l==t) t->u->l=nullptr;
                else t->u->r=nullptr;
            } else nema=1; delete t; t=nullptr;
        }else if (!t->l){ //drugi slucaj
            if (!t->u){
                t->r->u=nullptr;
                *st=t->r;
            } else {
                if (t->u->l==t) t->u->l=t->r;
                else  t->u->r=t->r;
                t->r->u=t->u;
            } delete t; t=nullptr;
        }  else if (!t->r){ //treci slucaj
            if (!t->u){
                t->l->u=nullptr;
                *st=t->l;
            }else {
                if (t->u->l==t) t->u->l=t->l;
                else  t->u->r=t->l;
                t->l->u=t->u;
            } delete t; t=nullptr;
        } else { //onaj sto ga ne volim
            St* pret=t->l;
            while  (pret->r) pret=pret->r;
            t->rec= pret->rec;
            t->prevod=pret->prevod;
            if (pret==t->l){
                if (pret->l){
                    t->l=pret->l;
                    pret->l->u=t; 
                } else t->l=nullptr;
                delete pret;
            }else {
                if (pret->l){
                    pret->u->r=pret->l;
                    pret->l->u=pret->u;
                } else pret->u->r=nullptr;
                delete pret;
            } pret=nullptr;
        }
    }  ct[rec]=0;
}

void ds(St* st){
    queue <St*> q;
    q.push(st);
    while (q.size()){
        St* c=q.front();
        q.pop();
        if (c->l) q.push(c->l);
        if (c->r) q.push(c->r);
        delete c;
    }
}

void lord(St*st){
    queue <St*> q,q2;
    q.push(st); q2.push(st);
    vector <int> v;
    v.push_back(1);
    int ct=0,vr=0,d=1,fl=0;
    while (q.size()){
        St* t=q.front(); q.pop(); ct++;
        if (t->l) {q.push(t->l); q2.push(t->l); vr++;}
        if (t->r) {q.push(t->r); q2.push(t->r); vr++;}
        if (ct==d){
            v.push_back(vr); ct=0; fl=1;
        }
        if (fl) {d=vr; fl=0; vr=0;}
    }
    for (auto vv:v){
        for (int i=0;i<vv;i++){
            St*pom=q2.front(); q2.pop();
            cout<<pom->rec<<" "<<pom->prevod<<" "; 
            if (pom->u) {
                cout<<"(otac: "<<pom->u->rec<<" "<<pom->u->prevod;
                if (pom==pom->u->l) cout<<",L)    ";
                else cout<<",R)    ";
            }
        }cout<<endl<<endl;
    }
}


int main(){
    St* st=nullptr; 
    cout<<"Zelite li unositi podatke putem:\n0.datoteke\n1.standardnog ulaza?\n";
    int ul,izb,l; ; cin>>ul; 
    string dat=" "; ifstream f;
    if (!ul){
        cout<<"Unesite ime datoteke iz koje zelite citati podatke\n"; 
        cin>>dat; f.open(dat); 
        if (!f.is_open()) {cout<<"Greska pri otvaranju datoteke\n"; return 0;}
    } else f.open(dat);
    meni();
    while ((f.is_open() && f>>izb)|| (!f.is_open() && cin>>izb)){ 
        St* rz;  string a,b;
        if (nema && (izb!=1 && izb!=8 )){
            cout<<"Vas zahtev ne moze biti ispunjen jer nema stabla " 
                "u memoriji.\nPokusajte opet\n"; meni(); continue;
        }
        switch(izb){
            case 1:
                if (!nema && st){ ds(st); cout<<"Staro stablo je obrisano\n";}
                cout<<"Unesite po jednu rec i njen prevod u svakom redu\n"
                    "kada zavrsite, unesite rec 'kraj'\n";  nema=1;
                while (1){
                    ul?(cin>>a):(f>>a);
                    if (a=="kraj") break;
                    ul?(cin>>b):(f>>b);
                    if(nema) {st=ks(a,b,nullptr); nema=0; continue;}
                    dodaj(st,a,b);
                }
                break;
            case 2:
                cout<<"Unesite rec ciji prevod zelite\n";
                ul?(cin>>a):(f>>a);
                rz=nadji(st,a);
                if (!rz) cout<<"Rec ne postoji u stablu\n";
                else  for (auto r: m[rz->rec]) cout<<r<<endl;
                break;
            case 3:
                cout<<"Unesiite rec i prevod koje zelite ubaciti\n";
                ul?(cin>>a>>b):(f>>a>>b);  
                dodaj(st,a,b);
                break;
            case 4:
                lord(st);
                break;
            case 5:
                cout<<"Unesite rec koju zelite da obrisete\n";
                ul?(cin>>a):(f>>a); 
                if (ct[a]==0) cout<<"Rec ne postoji u stablu\n";
                else brisi(&st,a);
                break;
            case 6:
                ds(st); nema=1;
                break;
            case 7: 
                cout<<"Unesite zeljenu rec\n"; 
                ul?(cin>>a):(f>>a);
                cout<<"Ukoliko zelite da specificirate duzinu prefiksa,\n"
                    "upisite broj razlicit od 0. U subprotnom, upisite 0\n";
                ul?(cin>>l):(f>>l);
                b=prefiks(st,a,l);    
                if (b=="") cout<<"Ne postoji rec sa tim prefiksom\n";
                else cout<<b<<endl;
                break;
            case 8: if (f.is_open()) f.close(); return 0;
        } meni();
    }  if (f.is_open()) f.close();
    return 0;
}


void meni(){
    cout<<"\n1: Formirajte stablo i inicijalizujte pocetni sadrzaj\n"
    "2: Pretrazite rec po zelji\n"
    "3: Unesite novu rec i njen prevod\n"
    "4: Ispisite sadrzaj stabla\n"
    "5: Obrisite rec po zelji\n"
    "6: Obrisite stablo\n"
    "7: Nadjite najkracu rec koja ima zeljeni prefiks\n"
    "8: Napustite program\n\n";
}