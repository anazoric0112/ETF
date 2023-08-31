#define _CRT_SECURE_NO_WARNINGS
#include <stdlib.h>
#include <stdio.h>

typedef struct Ch {  //Ch predstavlja jedan čvor liste tj 1 element skupa
    int x;  //x predstavlja vrednost elementa
    struct Ch* sl, * pr;  //sl-sledbenik datog elementa, pr-prethodnik -||-
}Ch;

typedef struct S {  //S predstavlja ceo skup
    int d; //d-broj elemenata u skupu
    struct Ch* ch1, * chn;  //ch1 je pokazivač na prvi čvor skupa, a chn na poslednji tj n-ti
}S;

typedef struct Niz{   //struktura koja ce cuvati (max) 5 pokazivaca na skupove
    struct S* x1; struct S* x2; 
    struct S* x3; struct S* x4; 
    struct S* x5;
    int z;    //broj koji govori koliko je skupova trenutno u memoriji
}Niz;

S* ks() { //funkcija koja oslobadja memoriju za novi skup i vraća pokazivač na njega
    S* sk = calloc(1, sizeof(S)); 
    if (!sk) return NULL;
    sk->chn = sk->ch1 = calloc(1, sizeof(Ch));
    sk->d = 0;  //u ovom trenutku je skup prazan pa je njegova duzina jednaka 0
    return sk;
}

int jel(S* s, const int y) {  // funkcija koja proverava da li je element u skupu
    if (!s) return 0;  //ako dat skup ne postoji vraća se vrednost false
    if (!s->d) return 0;  //ako je skup prazan vraća se vrednost false

    for (Ch* i = s->ch1; i != s->chn;) {  //petlja koja za svaki element proverava da li je jednak datom elementu
        if (y == i->x) return 1;  //ako jeste, vraća se vrednost true
        i = i->sl;  
    }
    if (y == s->chn->x) return 1;  //proverava se posebno za poslednji element, pošto for petlja njega ne proverava
    return 0;
}

int krd(S* s) {  //funkcija koja vraća kardinalnost skupa
    if (s) return s->d;
    return 0;
}

Ch* dje(S* s, const int y) {  //funkcija koja nalazi mesto u skupu na koje treba dodati dati element
    if (y > s->chn->x) return s->chn;  //ako je element veći od poslednjeg, vraća se vrednost repa skupa
    if (y < s->ch1->x) return s->chn;  //ako je manji od prvog, vraća se vrednost glave
    for (Ch* i = s->ch1; i != s->chn; ) {
        if (y == i->x) return NULL;  //ako je element u skupu, ne treba da se ubaci pa se vraća vrednost NULL
        if (y < i->x) return i->pr;  //vraće se vrednost pokazivača na poslednji element skupa manji od datom elementa
        i = i->sl;  //azuriranje pokazivaca
    }
    if (y<s->chn->x) return s->chn->pr;  //proverava se pretposlednje mesto zbog prirode petlje
    return NULL;
}

void dod(S* s, const int y) {  //funkcija koja dodaje element u skup ukoliko je to moguće
    if (!s) return;
    Ch* xx = calloc(1, sizeof(Ch));
    if (!s->d) { //specijalan slučaj ukoliko je skup trenutno prazan
        xx->x = y;
        xx->pr = xx->sl = xx;  //sam sebi je i prethodnik i sledbenik
        s->chn = xx;  //poslednji je element skupa
        s->ch1 = xx;  //takodje i prvi
        s->d++;  //duzina skupa se povecava za 1
        return;
    }
    Ch* pok = dje(s, y);  //pok je pokazivač na element iza kojeg se dati element ubacuje

    if (!pok) return;  //ako je dobio vrednost NULL, ništa se ne izvršava

    xx->x = y;
    xx->pr = pok;  //njegov prethodnik je onaj na kog pokazuje rezultat funkcije 'dje'
    xx->sl = pok->sl;  //njegov sledbenik je sledbenik nadjenog elementa
    pok->sl->pr = xx;  //prethodnik njegovog sledbenika je sada on a ne nadjeni element
    pok->sl = xx;  //on je sada sledbenik nadjenog elementa
    s->d++;  //duzina skupa se povecava za 1

    if (pok == s->chn && y > pok->x) s->chn = xx;  //ako je element ubačen na kraj, postaje rep skupa
    if (pok == s->chn && y < s->ch1->x) s->ch1 = xx;  //ako je element ubačen na početak, postaje glava skupa
}

void isp(S* s) {  //idpisuje skup
    printf("\n");
    if (!s) return; if (!s->d) {printf("Prazan skup\n"); return;}  //desi se ako je skup prazan
    for (Ch* i = s->ch1; i != s->chn;) {  //iteracija kroz elemente
        printf("%d ", i->x);
        i = i->sl;
    }
    printf("%d\n", s->chn->x);
}

void brs(S* s, const int y){  //funkcija koja briše element iz skupa
    if (!s) return;
    if (!s->d) return;
    if (y==s->ch1->x){  //specijalan slucaj kada se brise prvi element
        s->ch1->sl->pr=s->chn;  //prethodnik drugog elementa postaje poslednji element skupa
        s->chn->sl=s->ch1->sl;  //sledbenik poslednjeg postaje drugi element skupa
        free(s->ch1);  //prvi element se brise
        s->ch1=s->chn->sl;  //sada je drugi element skupa postao prvi
        s->d--;  //duzina skupa se smanjuje za 1
        return;
    }
    if (y==s->chn->x){  //specijalan slucaj kada se brise poslednji element
        s->chn->pr->sl=s->ch1;  //sledbenik pretposlednjeg elementa postaje prvi element
        s->ch1->pr=s->chn->pr;  //prethodnik prvog elementa postaje pretposlednji
        free(s->chn);  //brise se poslednji element
        s->chn=s->ch1->pr;  //pretposlednji element je sada postao poslednji
        s->d--;  //duzina skupa se smanjuje za 1
        return;
    }
    for (Ch* i=s->ch1;i!=s->chn;){  //prolazi se kroz skup dok se ne nadje trazeni element
        if (y==i->x) {
            i->pr->sl=i->sl;  //njegov sledbenik postaje sledbenik njegovog prethodnika
            i->sl->pr=i->pr;  //njegov prethodnik postaje prethodnik njegovoh sledbenika
            free(i);  //trazeni student se brise iz liste
            s->d--;  //duzina skupa se smanjuje za 1
            return;
        }
        else i=i->sl;  //azurira se iterator
    }
    return;
}

S* kop(S* s){  //funkcija koja pravi kopiju skupa
    if (!s) return NULL;
    S* sk=ks();  //kreira se novi skup
    for( Ch *i=s->ch1; i!=s->chn;){  //svaki element datog skupa se dodaje u novi skup
        dod(sk, i->x);
        i=i->sl;
    }
    dod(sk, s->chn->x);  //dodaje se poslednji element posebno jer nije ukljucen u petlju
    return sk;
}

S* un(S* s1, S* s2){  //vraca uniju dva skupa
    if (!s1 && !s2){return NULL;} //ako nijedan ne postoji, vraća se vrednost NULL
    if (!s1->d && !s2->d) return NULL;  //takodje i ako su oba prazna
    if (!s1 || !s1->d) return kop(s2);  //ako je jedan prazan vraza se kopija drugog
    if (!s2 || !s2->d) return kop(s1);
    S* sV=s2; S* sM=s1;  //sV je pokazivač na skup koji ima veći poslednji element, a sM manji
    if(s1->chn->x>s2->chn->x) {sV=s1; sM=s2;} 
    S* sk=kop(sV);  //sk je kopija skupa sa većim poslednjim elementom
    if (!sk) return NULL;
    Ch *i=sV->ch1; 
    for (Ch *j=sM->ch1; j!=sM->chn;){  //prolazi se kroz sM 
        if (i->x > j->x){   //pomera se udesno pokazivač koji pokazuje na manji element
           dod(sk,j->x);  j=j->sl;
        }
        if (i->x < j->x){
            i=i->sl;
        }
        if (i->x==j->x){  //ako su jednaki pomeraju se oba
            i=i->sl; j=j->sl;
        }
    }
    if (sM->chn->x!=sV->chn->x) dod(sk, sM->chn->x);  //poslednji element u sM se dodaje ako već nije u uniji
    return sk;
}

S* pr(S *s1, S *s2){  //vraca presek 2 skupa
    if (!s1 || !s2){return NULL;}  //ako neki ne postoji, vraca NULL
    if (!s1->d || !s2->d) return NULL;  //takodje i ako je neki prazan
    S* sV=s2; S* sM=s1;  //sV je pokazivač na skup koji ima veći poslednji element, a sM manji
    if(s1->chn->x>s2->chn->x) {sV=s1; sM=s2;}
    S* sk=kop(sM);  //sk je kopija skupa sa manjim poslednjim elementom
    if (!sk) return NULL;
    Ch *j=sM->ch1;
    for (Ch *i=sV->ch1; 1;){  // prolazi se kroz sV dok se ne dodje do prvog cvora sM-a
         if (i->x > j->x){  //pomera se udesno pokazivač koji pokazuje na manji element
            brs(sk, j->x);
            j=j->sl;
            if (j==sM->ch1) break;
         }
         if (i->x < j->x){
            i=i->sl;
         }
         if (i->x==j->x){  //ako su jednaki pomeraju se oba
            i=i->sl; 
            j=j->sl;
            if (j==sM->ch1) break;
         }
    }
    return sk;
}

S* rzl(S* s1, S* s2){  //vraca razliku prvog skupa u odnosu na drugi
    if (!s1 && !s2){return NULL;}  //ako nijedan ne postoji vraca NULL
    if (!s1->d) return NULL;  //ako je prvi prazan vraca NULL
    if (!s2->d) return kop(s1);  //ako je drugi prazan vraca kopiju prvog
    S* sk=kop(s1);  //sk je kopija prvog
    if (!sk) return NULL;
    Ch *i=s1->ch1;
    for (Ch *j=s2->ch1; 1;){  //prolazi se kroz drugi
        if (i->x==j->x){  //brisu se iz kopije svi elementi koji pripadaju drugom
            brs(sk,i->x); 
            i=i->sl; j=j->sl;  //azuriraju se oba pokazivaca
            if (i==s1->ch1) break;  //ako je bilo koji skup stigao do svog pocetka
            if (j==s2->ch1) break;  //prekida se petlja
        }
        if (i->x > j->x) {  //pomera se udesno pokazivač koji pokazuje na manji element
            j=j->sl;
            if (j==s2->ch1) break;  //ako se pomeri na pocetak, prekida se petlja
        }
        if (i->x < j->x) {  //pomera se udesno pokazivač koji pokazuje na manji element
            i=i->sl;
            if (i==s1->ch1) break;  //ako se pomeri na pocetak, prekida se petlja
        }
    }
    return sk;
}

void dst(S* s){  //destruktor skupa
    if (!s) return;
    if (!s->d) return;
    for (Ch *i=s->ch1; i!=s->chn;){
        Ch *t=i->sl;
        brs(s, i->x);
        i=t;
    }
    brs(s, s->chn->x);
}

void meni(){  //ispisuje glavni meni korisniku
    printf("\n1: Ucitati novi skup\n");
    printf("2: Dodati clan u skup\n");
    printf("3: Izbrisati clan iz skupa\n");
    printf("4: Ispisati skup\n");
    printf("5: Unija / presek / razlika dva skupa\n");
    printf("6: Brisanje skupa\n");
    printf("7: Izlaz iz programa\n\n");
}

void err(){  //ispisuje gresku korisniku
    printf("\nNeispravan unos. Molimo Vas da pokusate ponovo.\n\n");
}

Niz* niz(){  //kreira niz za cuvanje skupova
    Niz* n= calloc(1, sizeof(Niz));
    n->z=0;
    return n;
}

void ubaci(S* s,Niz *n){  //ubacuje skupove u kreirani niz
    if (n->z==5) return;  //na osnovu slobodnih mesta
    if (!n->x1) {n->x1=calloc(1, sizeof(S));n->x1=s;}
    else if (!n->x2) {n->x2=calloc(1, sizeof(S));n->x2=s;}
    else if (!n->x3) {n->x3=calloc(1, sizeof(S));n->x3=s;}
    else if (!n->x4) {n->x4=calloc(1, sizeof(S));n->x4=s;}
    else if (!n->x5) {n->x5=calloc(1, sizeof(S));n->x5=s;}
    n->z++;  //broj popunjenihmesta je veci za 1
}

void ispisSkupova(Niz* n){  //ispisuje korisniku sve dostupne skupove
    printf("\n");
    if (n->x1){printf("1:"); isp(n->x1); printf("\n");}
    if (n->x2){printf("2:"); isp(n->x2); printf("\n");}
    if (n->x3){printf("3:"); isp(n->x3); printf("\n");}
    if (n->x4){printf("4:"); isp(n->x4); printf("\n");}
    if (n->x5){printf("5:"); isp(n->x5); printf("\n");}
    printf("\n");
    return;
}

void cisti(Niz* n,const int y){  //uklanja odredjeni skup iz niza
    if (y==1) n->x1=NULL;
    else if (y==2) n->x2=NULL;
    else if (y==3) n->x3=NULL;
    else if (y==4) n->x4=NULL;
    else if (y==5) n->x5=NULL;
    n->z--; return;
}

S* trazeniSkup(Niz *n,const int y){  //pronalazi trazeni skup u nizu na osnobu njegovog indeksa
    if (y==1) return n->x1;
    else if (y==2) return n->x2;
    else if (y==3) return n->x3;
    else if (y==4) return n->x4;
    else if (y==5) return n->x5; 
    else return NULL;
}

int main() {
    Niz* n=niz();

    while (1){
        meni();

        char izbor[30];
        scanf("%s", izbor);
        if (izbor[1]) {err(); continue;}

        int unos;
        if (!('7'>=izbor[0] && izbor[0]>='1')) {err();}

        else if (izbor[0]=='1'){
            if (n->z==5){
                printf("\nNema mesta za jos jedan skup. Probajte neki da izbrisete\n\n");
                continue;
            }
            printf("\nUnesite broj elemenata koje zelite da unesete\n\n");
            int bre;
            scanf("%d", &bre); 
            if (bre==0) {
                printf("\nKreirali ste prazan skup\n\n"); 
                S* skup=ks(); ubaci(skup,n);
                continue;
            }
            if (!bre){err(); continue;}

            S* skup=ks();
            ubaci(skup, n);
            printf("\nUnesite zeljene elemente jedan po jedan\n\n");
            for  (int i=1; i<=bre; i++){
                scanf("%d", &unos);
                dod(skup, unos);
            }
        }
        else if (izbor[0]=='2'){
            if(n->z==0) {printf("\nTrenutno nema skupova. Pokusajte da unesete neki\n\n");continue;}
            printf("\nUnesite redni broj skupa kojem zelite dodati element\n\n");
            ispisSkupova(n);
            scanf("%d", &unos); 
            if (!trazeniSkup(n,unos)) {err(); continue;}

            printf("\nSada unesite element\n\n");
            int unos3; scanf("%d", &unos3);
            dod (trazeniSkup(n,unos),unos3);
        }
        else if (izbor[0]=='3'){
            if(n->z==0) {printf("\nTrenutno nema skupova. Pokusajte da unesete neki\n\n");continue;}
            printf("\nUnesite redni broj skupa kojem zelite obrisati element\n\n");
            ispisSkupova(n);
            scanf("%d",&unos); if (!trazeniSkup(n,unos)) {err(); continue;}

            if (!trazeniSkup(n,unos)->d) {printf("\nDati skup nema clanove\n\n");continue;}
            printf("\nSada unesite koji element zelite da obrisete\n\n");
            int unos4; scanf("%d", &unos4);
            brs(trazeniSkup(n,unos),unos4);
        }
        else if (izbor[0]=='4'){
            if(n->z==0) {printf("\nTrenutno nema skupova. Pokusajte da unesete neki\n\n");continue;}
            printf("\nUnesite broj skupa koji zelite da ispisete\n\n");
            if (n->x1) printf ("1 \n");
            if (n->x2) printf ("2 \n");
            if (n->x3) printf ("3 \n");
            if (n->x4) printf ("4 \n");
            if (n->x5) printf ("5\n\n");
            
            scanf("%d", &unos);
            if (trazeniSkup(n,unos)) isp(trazeniSkup(n,unos));
            else {err(); continue;}
        }
        else if (izbor[0]=='5'){
            if (n->z==5){
                printf("\nNema mesta za jos jedan skup. Probajte neki da izbrisete\n\n");
                continue;
            } 
            if(n->z==0) {printf("\nTrenutno nema skupova. Pokusajte da unesete neki\n\n");continue;}
            if(n->z==1) {printf("\nTrenutno je u memoriji samo jedan skup. Pokusajte da unesete jos neki\n\n");continue;}
            
            ispisSkupova(n);
            printf("\nIzaberite prvi skup nad kojim zelite da izvrsite operaciju\n\n");
            int unos1; scanf("%d", &unos1);
            if (!trazeniSkup(n,unos1)) {err(); continue;}
            printf("\nIzaberite drugi skup nad kojim zelite da izvrsite operaciju\n\n");
            int unos2; scanf("%d", &unos2);
            if (!trazeniSkup(n,unos2)) {err(); continue;}

            printf("\nUnesite redni broj operacije koju zelite da izvršite\n\n");
            printf("1: unija; 2: presek; 3: razlika\n\n");
            scanf ("%d", &unos);

            if (!trazeniSkup(n,unos1)->d && !trazeniSkup(n,unos2)->d){
                printf("\nRezultat je prazan skup\n\n"); 
                S* skup=ks(); ubaci(skup,n); continue;
            }

            if (unos==1) {
                S* rez=un(trazeniSkup(n,unos1),trazeniSkup(n,unos2));
                if (rez) ubaci(rez,n);
                if(!rez->d) printf("\nUnija ova dva skupa je prazan skup\n\n");
                else isp(rez);
            } else if (unos==2) {
                if (!trazeniSkup(n,unos1)->d || !trazeniSkup(n,unos2)->d){
                    printf("\nPresek ova dva skupa je prazan skup\n\n"); 
                    S* skup=ks(); ubaci(skup,n); continue;
                }
                S* rez=pr(trazeniSkup(n,unos1),trazeniSkup(n,unos2));
                if (rez) ubaci(rez,n);
                if(!rez->d) printf("\nPresek ova dva skupa je prazan skup\n\n");
                else isp(rez);
            } else if (unos==3) {
                if (!trazeniSkup(n,unos1)->d) {
                    printf("\nRazlika ova dva skupa je prazan skup\n\n");
                    S* skup=ks(); ubaci(skup,n); continue;
                }
                S* rez=rzl(trazeniSkup(n,unos1),trazeniSkup(n,unos2));
                if (rez) ubaci(rez,n);
                if(!rez->d) printf("\nRazlika ova dva skupa je prazan skup\n\n");
                else isp(rez);
            } else {err(); continue;}
        }
        else if (izbor[0]=='6'){
            if(n->z==0) {printf("Trenutno nema skupova\n\n");continue;}
            printf("Izaberite skup koji zelite da obrisete\n\n");
            ispisSkupova(n);
            
            scanf ("%d", &unos);
            if (trazeniSkup(n,unos)) {dst(trazeniSkup(n,unos)); cisti(n,unos);}
            else {err(); continue;}
        }
        else if (izbor[0]=='7'){
            break;
        }
    }
    return 0;
}
