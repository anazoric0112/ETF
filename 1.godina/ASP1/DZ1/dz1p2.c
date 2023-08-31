#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct St{    //struktura 'student' koja sadrzi podatke o studentu
    char ime[20]; char prezime[20];
    char index[10]; char sp[5]; int god;
}St;
typedef struct Cl{     //jedan element reda odnosno liste
    struct St* st;     //pokazivac na studenta tj podatke o njemu
    struct Cl* sl, * pr;    //pokazivaci na prethodni i sledeci element
}Cl;
typedef struct Red{     //struktura reda odnosno liste (dvostruko ulancana lista)
    struct Cl *prvi, *posl;     //pokazivaci na prvi i poslednji element reda/liste
    int max; int d;     //max-maksimalna duzina, d-trenutna duzina
}Red;

void pod(Red* l, St* s){     //ucitava podatke o sudentu 
    if (!s) return;
    printf("\nUnesite ime i prezime studenta\n\n"); scanf("%s", s->ime); scanf("%s", s->prezime);
    printf("\nUnesite indeks studenta \n\n"); scanf("%s", s->index);
    printf("\nUnesite trenutnu godinu studija studenta\n\n"); scanf("%d", &s->god);
    printf("\nUnesite skracenicu studijskog programa studenta\n\n"); scanf("%s",s->sp);
}

int slucn(int n){     //generise pseudoslucajan ceo broj od 1 do n
    time_t s; s = time(NULL);
    s=(429493445*(s)+907633385)%n+1;
    if (s<0) s=-s; return s;
}
float sluc1(time_t *seed){     //generise pseudoslucajan broj od 0 do 1
    *seed=429493445*(*seed)+907633385;
    if (*seed<0) *seed=-*seed;
    return *seed%10000/10000.;
}

Red* ks(const int n){     //konstruktor reda/liste
    Red* r=calloc(1, sizeof(Red));     //alocira se memorija za red/listu
    if (!r) return NULL;
    r->prvi=calloc(1, sizeof(Cl));     //alocira se memorija za prvi element
    r->posl=calloc(1, sizeof(Cl));     //alocira se memorija za poslednji element
    if (n) r->max=n; r->d=0;     //pocetna duzina se inicijalizuje na nulu
}
St * kss(Red* l, int pd){     //konstruktor studenta
    St* s=calloc(1,sizeof(St));     //alocira se memorija za studenta
    if (!s) return NULL;
    if (pd) pod(l,s);     //odmah se trazi unos podataka o studentu
    return s;
}

St* kojeg(Red* l,int n){     //vraca pokazivac na studenta u zavisnosti od rednog broja studenta u listi
    if (!l) return NULL; if (!l->d) return NULL;
    if (n==l->d) return l->posl->st;     //ako je redni broj studenta u redu jednak njegovoj duzini, vraca se poslednji
    int br=1;     //brojac ciklusa se postavlja na 1
    for (Cl *i=l->prvi; i!=l->posl; ){     //petlja koja pronalazi trazenog studenta
        if (n==br) return i->st;     //uporedjuje njegov redni broj i brojac ciklusa
        i=i->sl; br++;     //azuriraju se brojac i pokazivac 
    } return NULL;
}

void brs(Red* l, St* s){     //brise studenta iz liste
    if (!l) return; if (!l->d) return;
    if (s==l->prvi->st){     //specijalan slucaj ako je student prvi u listi
        l->prvi->sl->pr=l->posl;     //prethodnik drugog u listi postaje poslednji element liste
        l->posl->sl=l->prvi->sl;     //sledbenik poslednjeg postaje drugi element liste
        free(l->prvi);     //prvi element se brise
        l->prvi=l->posl->sl;     //sada je drugi element liste postao prvi
        l->d--;     //duzina liste se smanjuje za 1
        return;
    }
    if (s==l->posl->st){     //specijalan slucaj ako je student poslednji u listi
        l->posl->pr->sl=l->prvi;     //sledbenik pretposlednjeg elementa postaje prvi element liste
        l->prvi->pr=l->posl->pr;     //prethodnik prvog elementa postaje pretposlednji
        free(l->posl);     //brise se poslednji element
        l->posl=l->prvi->pr;     //pretposlednji element je sada postao poslednji
        l->d--;     //duzina liste se smanjuje za 1
        return;
    }
    for (Cl* i=l->prvi;i!=l->posl;){     //prolazi se listom dok se ne nadje trazeni student
        if (s==i->st) {
            i->pr->sl=i->sl;     //njegov sledbenik postaje sledbenik njegovog prethodnika
            i->sl->pr=i->pr;     //njegov prethodnik postaje prethodnik njegovoh sledbenika
            free(i);     //trazeni student se brise iz liste
            l->d--;     //duzina liste se smanjuje za 1
            return;
        }
        else i=i->sl;
    }
    return;
}

void dod(Red *r, St* s){     //dodavanje studenta u red (ili listu)
    if (!r) return;
    Cl* xx = calloc(1, sizeof(Cl));
    if (!r->d) {     //poseban slucaj kada je red trenutno prazan
        xx->st = s;
        xx->pr = xx;     //sam sebi je prethodnik
        xx->sl = xx;     //sam sebi je sledbenik
        r->posl = xx;     //poslednji je element reda
        r->prvi = xx;     //takodje je i prvi
        r->d++;     //duzina reda se povecava za 1
        return;
    }
    if (r->d!=r->max){     //ako red nije prazan, student se dodaje na kraj
        xx->st = s;
        xx->pr = r->posl;     //njegov prethodnik je poslednji u redu
        xx->sl = r->prvi;     //njegov sledbenik je prvi u redu
        r->prvi->pr = xx;     //postaje prethodnik prvog elementa
        r->posl->sl = xx;     //postaje sledbenik poslednjeg elementa
        r->posl=r->prvi->pr;     //sada je on poslednji element
        r->d++;     //duzina reda se povecava za 1
    }
    else printf("\nRed je pun, nije moguce dodati nikoga\n\n");
}

void sledeci(Red* r){     //dohvatanje studenta iz reda i njegov uspesan upis
    if (!r) return; if (!r->d) return;
    r->posl->sl=r->prvi->sl;     //drugi clan reda postaje sledbenik poslednjeg tj prvi
    r->prvi->sl->pr=r->posl;     //prethodnik drugog clana reda je sada poslednji clan
    free(r->prvi); r->d--;     //student se brise iz reda i red je kraci za 1
    if (r->d) r->prvi=r->posl->sl;
}
void prem(Red* r){     //premestanje na kraj nakon dohvatanja studenta i neuspesnog upisa
    if (!r) return; 
    if (!r->d) return;
    r->posl=r->prvi;     //pokazivac na poslednji element sada pokazuje na njega jer ide na kraj reda
    r->prvi=r->prvi->sl;     //prvi je sada onaj koji je bio iza njega
}

int prazan(Red* r){     //proverava da li je red prazan
    if (!r) {printf("\nTrazeni red ne postoji\n\n"); return 0;}
    if (r->d==0) return 1; return 0;
}
int pun(Red *r){     //proverava da li je red pun
    if (!r) {printf("\nTrazeni red ne postoji\n\n"); return 0;}
    if (!r->max) {printf("\nNe postoji ogranicenje duzine trazenog reda/trazene liste\n\n"); return 0;}
    if (r->d==r->max) return 1; return 0;
}

void meni(){
    printf("\nIzaberite redni broj zeljene operacije:\n\n");
    printf("1: Unos podataka o studentu\n");
    printf("2: Pokretanje simulacije\n");
    printf("3: Kraj Programa\n\n");
}
void err(){
    printf("\nNeispravan unos. Molimo Vas da pokusate ponovo.\n\n");
}

int main(){
    time_t *seed=calloc(1,sizeof(time_t)); 
    *seed = time(NULL);
    Red *lista=ks(0);     //pravi se prazna lista
    Red *red=ks(lista->d);     //pravi se prazan red

    while (1){  meni();     //ispisuje se meni na opcetku svake iteracije
        char izbor[30]; scanf("%s", izbor);
        if (izbor[1]) {err(); continue;}
        else if (izbor[0]=='1'){
            St* s1=kss(lista, 1);
            dod(lista,s1); red->max=lista->d;
            printf("\nUspesno ste uneli podatke o studentu\n");
            printf("Trenutni broj studenata u listi je %d\n\n", lista->d);
            printf("Ukoliko niste zavrsili sa unosom podataka, ponovo izaberite '1'\n\n");
        }
        else if (izbor[0]=='2'){
            while (lista->d){     //premestanje u red se izvrsava dok lista ne ostane prazna
                St* t=kojeg (lista, slucn(lista->d));
                dod(red,t); brs(lista,t);
            }
            if (red->d==0) {printf("\nRed je prazan, simulacija ne moze biti pokrenuta\n\n"); continue;}
            int brojac=0; float x;
            printf("\nUnesite realan broj izmedju 0 i 0.5, ukljucujuci i te brojeve\n\n"); 
            int k=1;
            while (k){
                scanf("%f", &x);
                if (x<0.0 || x>0.5) err();
                else k=0;
            }
            while(1){     //upis se vrsi dok red ne ostane prazan
                float b=sluc1(seed);
                if (b>x) {
                    St* tmp=red->prvi->st; tmp->god+=1;
                    printf("\nIme i prezime: %s %s, upisana godina:  %d \n\n", tmp->ime, tmp->prezime, tmp->god);
                    sledeci(red); brojac++;
                }
                else{ prem(red); brojac++;}
                if (red->d==0) break;     //kada se red izsprazni, prekida se petlja
            }
            printf("\nBroj koraka simulacije je: %d \n\n", brojac);
        }
        else if (izbor[0]=='3'){ 
            printf("\n\nUspesno ste izasli iz programa\n"); 
            break;
            }
        else err();
    }
    return 0;
}
