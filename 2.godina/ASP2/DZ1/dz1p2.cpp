#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <algorithm>
#include <math.h>
#include <fstream>
using namespace std;
int i,ii=1;
ifstream fajl;
string dat;

void meni(){
    cout<<"\n\n1: Zamislite broj i neka racunar pokusa da ga pogodi\n"
     "2: Promenite decimalnu tacnost\n"
     "3: Promenite opseg vrednosti\n"
     "4: Promenite mod igre\n"
     "5: Izadjite iz programa\n";
    cout<<"NAPOMENA\nAko do sad niste rucno menjali granice opsega ili"
        "\nvrednost decimalne tacnost, njigove inicijalne vrednosti su:"
        "\ndonja granica = 0, gornja granica = 100, decimalna tacnost = 2\n\n";
}
int fb(){
    cout<<"Zamisljeni broj je:\n"
        "1: veci\n"
        "2: manji\n"
        "3: zadovoljavajuc\n";
    int rez; cin>>rez; return rez;
}
void pocetak(){
    cout<<"Ukoliko zelite da unesete broj koji ste zamislili\n"
        "i da racunar samo ispise broj koraka koji mu je\n"
        "bio potreban da pogodi, unesite 1\n"
        "Ukoliko zelite sami da odgovarate na pitanja da li\n"
        "je vas broj veci, manji ili jednak, unesite 2\n";
    cin>>i;
    if (i==1) {cout<<"Zelite li da unosite podatke preko standardnog ulaza ili preko datoteke\n";
        cout<<"1.Preko datoteke\n2.Preko standardnog ulaza\n"; cin>>ii; ii--;
        }
    if (!ii) {
        cout<<"Unesite ime datoteke iz koje citate podatke\n";
        cin>>dat; fajl.open(dat);
    }
}
int main(){
    pocetak();
    double down=0,up=100; int dc=2; 
    meni(); int izb;
    while ((!ii && fajl>>izb) || (ii && cin>>izb)){
        int koraci=0,f,d=2,x,y;
        double tr,broj; 
        double donja=down, gornja=up;
        if (izb==1){
            d=(gornja-donja)/10;  tr=donja+d;
            if (i==1){
                cout<<"Unesite svoj realan broj\n"; 
                if (ii) cin>>broj;
                else fajl>>broj;
                if (broj>gornja || broj<donja ) {
                    cout<<"Broj koji ste uneli nije u dozvoljenom opsegu\n\n";
                    meni(); continue;
                }
            }
            while (1){ cout<<tr<<endl<<endl;
                koraci++;
                if (i==2){
                    cout<<endl<<tr<<endl;
                    f=fb();
                }
                else {
                    if (tr<broj) f=1;
                    else if (tr>broj) f=2;
                    x=trunc(tr*(pow(10,dc)));
                    y=trunc(broj*(pow(10,dc)));
                    if (x==y) f=3;  
                }
                if (f==1){
                    donja=tr; d*=2;
                    if (donja+d>gornja) tr=(gornja+donja)/2;
                    else tr=donja+d;
                } else if (f==2){
                    gornja=tr;
                    tr=(tr+donja)/2;
                } else if (f==3) {
                    cout<<"Broj je pogodjen\n"; 
                    cout<<"Broj koraka je bio "<<koraci<<endl;
                    break;
                }
            }
        } else if (izb==2){
            cout<<"Unesite zeljenu decimalnu tacnost\n";
            if (ii)cin>>dc; else fajl>>dc;
        } else if (izb==3){
            cout<<"Unesite zeljene intervale\n";
            if (ii)cin>>down>>up; else fajl>>down>>up;
        } else if (izb==4) {
            if (fajl.is_open())fajl.close();
            pocetak(); 
        } else if (izb==5) {
            if (fajl.is_open())fajl.close();
            return 0;
        } 
          meni();
    }
    if (fajl.is_open()) fajl.close();
    return 0;
}
