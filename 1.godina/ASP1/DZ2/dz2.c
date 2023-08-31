#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

typedef struct Cvor {     //cvor stabla
    int** mat;
    struct Cvor** deca;
}Cv;
typedef struct Cl {     //jedan element reda odnosno liste
    int** mat;     //pokazivac na kvadrat
    Cv* cvor;     //pokazivac na cvor stabla ciji je to kvadrat
    struct Cl* sl, * pr;    //pokazivaci na prethodni i sledeci element
}Cl;
typedef struct Red {     //struktura reda odnosno liste (dvostruko ulancana lista)
    struct Cl* prvi, * posl;     //pokazivaci na prvi i poslednji element reda/liste
    int d;     //max-maksimalna duzina, d-trenutna duzina
}Red;

Cv* ks(int** m, int n);
int** km(int n);
void dm(int** m, int n);
int** kop(int** m, int n);
void im(int** m, int n);
int** dodaj(int** m, int broj, int x, int y, int n);
int validno(int** m, int n, int broj, int x, int y, int s);
int validan(int** m, int n, int* niz, int nn, int s);
int nadji(int** m, int n, int* i, int* j);
int reseno(int** m, int n, int s);
void dete(Cv* roditelj, int** m, int n);
void popuni(Red* r, Cv* koren, int** m, int* niz, int n, int nn, int s);
Red* kr();
int empty(Red* r);
void push(Red* r, Cv* cv);
void pop(Red* r);
void ispisired(Red* r, int n);
int upis(int** mag, int n, int* niz, int* nn);
void meni();
void alerr();
int arn(int** m, int* niz, int n, int nn);
void selectionSort(int* v, int n);
void levelorder(Cv* stablo, Red* r, int n);
int** popback(Red* r, int n);
void postorder(Cv* koren, Red* r, int n);
int savrsen(int** m, int n, int s);
void matrice(Red* r,int n, Cl** tr, int *l);
int nivo(int **m,int n);
void err();

//pravi stablo
//ne ukljucuje kopiju matrice
Cv* ks(int** m, int n) {
    Cv* koren;
    koren = (Cv*)calloc(1, sizeof(Cv));
    if (!koren) alerr();
    koren->mat = m;
    koren->deca = (Cv**)calloc(n, sizeof(Cv*));
    if (!koren->deca) alerr();
    for (int i = 0; i < n; i++) koren->deca[i] = NULL;
    return koren;
}

//pravi matricu 
int** km(int n) {
    int** mag;
    mag = (int**)calloc(n, sizeof(int*));
    if (!mag) alerr();
    for (int i = 0; i < n; i++) {
        *(mag + i) = (int*)calloc(n, sizeof(int));
        if (!*(mag + i)) alerr();
    } return mag;
}

//brise matricu 
void dm(int** m, int n) {
    for (int i = 0; i < n; i++) free(m + i);
    free(m);
}

//kopija matrice
int** kop(int** m, int n) {
    int** nova = km(n);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            nova[i][j] = m[i][j];
        }
    } return nova;
}

//pravi kopiju
//dodaje joj clan 
int** dodaj(int** m, int broj, int x, int y, int n) {
    int** mat = kop(m, n);
    *(*(mat + x) + y) = broj;
    return mat;
}

//ispisuje matricu
void im(int** m, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", *(*(m + i) + j));
        }
        printf("\n");
    }printf("\n");
}

//proverava moze li broj u polje kvadrata
int validno(int** m, int n, int broj, int x, int y, int s) {
    s = s / n;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
            if (m[i][j] == broj) return 0;
    }
    int sum1 = 0, sum2 = 0, k1 = 1, k2 = 1;
    for (int i = 0; i < n; i++) {
        sum1 += m[x][i]; if (!m[x][i] && i != y) k1 = 0;
        sum2 += m[i][y]; if (!m[i][y] && i != x) k2 = 0;
    }sum1 += broj; sum2 += broj;
    if (k1) { if (sum1 != s) return 0; }
    else { if (sum1 > s) return 0; }
    if (k2) { if (sum2 != s) return 0; }
    else { if (sum2 > s) return 0; }

    if (x == y || x + y == n - 1) {
        int t1 = 1, t2 = 1;
        for (int i = 0; i < n; i++) {
            if (i != x) {
                if (!m[i][i]) t1 = 0;
                if (!m[i][n - i - 1]) t2 = 0;
            }
        }
        if (t1 && x == y) {
            int sd1 = 0;
            for (int i = 0; i < n; i++) sd1 += m[i][i];
            if ((sd1 + broj) != s) return 0;
        }
        if (t2 && x + y == n - 1) {
            int sd2 = 0;
            for (int i = 0; i < n; i++) sd2 += m[i][n - i - 1];
            if ((sd2 + broj) != s) return 0;
        }
    }

    return 1;
}

//proverava unet kvadrat
int validan(int** m, int n, int* niz, int nn, int s) {
    if (s % n) return 0;
    int k = 0;
    if (!arn(m, niz, n, nn)) return 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (m[i][j]) {
                int broj = m[i][j]; m[i][j] = 0;
                if (!validno(m, n, broj, i, j, s)) k = 1;
                m[i][j] = broj;
                if (k) return 0;
            }
        }
    }
    return 1;
}

//proverava da li je kvadrat savrsen
int reseno(int** m, int n, int s) {
    s = s / n;
    for (int i = n - 1; i >= 0; i--) {
        for (int j = n - 1; j >= 0; j--)
            if (!m[i][j]) return 0;
    }
    for (int i = 0; i < n; i++) {
        int sk = 0; int sr = 0;
        for (int j = 0; j < n; j++) {
            sr += m[i][j]; sk += m[j][i];
        }
        if (sr != s || sk != s) return 0;
    }
    int sd1 = 0, sd2 = 0;
    for (int i = 0; i < n; i++) {
        sd1 += m[i][i]; sd2 += m[i][n - i - 1];
    } if (sd1 != s || sd2 != s) return 0;
    return 1;
}

//dodaje se sin prosledjenom cvoru
void dete(Cv* roditelj, int** m, int n) {
    int i = 0;
    while (roditelj->deca[i] != NULL) i++;
    roditelj->deca[i] = ks(m, n);
}

//nalazi nulu u matrici
int nadji(int** m, int n, int* i, int* j) {
    for ((*i) = 0; (*i) < n; (*i)++) {
        for ((*j) = 0; (*j) < n; (*j)++) {
            if (!m[*i][*j]) return 1;
        }
    } return 0;
}

//pravi stablo odlucivanja
void popuni(Red* r, Cv* koren, int** m, int* niz, int n, int nn, int s) {
    int i = 0, j = 0, indeks = 0; int l;
    push(r, koren);
    Cl* t = r->prvi;
    while (!empty(r)) {
        indeks = 0;
        for (int k = 0; k < nn; k++) {
            l = nadji(t->mat, n, &i, &j);
            if (!l) break;
            if (validno(t->mat, n, niz[k], i, j, s)) {
                int** mat = dodaj(t->mat, niz[k], i, j, n); //dobije se kopija
                dete(t->cvor, mat, nn); //kopija ide u stablo
                push(r, t->cvor->deca[indeks++]);
            }
        } t = t->sl; pop(r); if (!t) break;
    }
}

int main() {
    int n, nn, suma = 0;
    Red* r = kr();
    int** mag;
    int* niz = (int*)calloc(1, sizeof(int));
    Cv* stablo = NULL;
    char str;
    while (1) {
        meni();
        scanf_s("\n%c", &str);
        if (str == '1') {
            printf("Uneti dimenziju magicnog kvadrata\n");
            scanf_s("%d", &n);
            if (n<1) {err(); continue;}
            mag = km(n);nn = pow(n, 2);
            suma = upis(mag, n, niz, &nn);
            if (!validan(mag, n, niz, nn, suma)) { printf("Pocetno stanje kvadrata i unete vrednosti nisu validne\nPokusajte ponovo\n\n"); continue; }
            stablo = ks(mag, (int)pow(n, 2));
            popuni(r, stablo, mag, niz, n, nn, suma);
        }
        else if (str == '2') {
            if (stablo) printf("%d\n\n", suma / n);
            else printf("Morate prvo uneti vrednosti\n\n");
        }
        else if (str == '3') {
            if (stablo) levelorder(stablo, r, n);
            else printf("Morate prvo uneti vrednosti\n\n");
        }
        else if (str == '4') {
            if (stablo) {
                postorder(stablo, r, n);
                int** mtr; int res=1;
                while (!empty(r)) {
                    mtr = popback(r, n); //printf ("matrica:\n\n"); im (mtr,n);
                    if (reseno(mtr, n, suma)) {printf ("Resenje %d:\n\n", res++); im(mtr, n);}
                    free(mtr);
                }
            }
            else printf("Morate prvo uneti vrednosti\n\n");
        }
        else if (str == '5') {
            if (stablo) {
                postorder(stablo, r, n);
                int** mtr;
                int flag = 0;
                while (!empty(r)) {
                    mtr = popback(r, n);
                    if (reseno(mtr, n, suma) && savrsen(mtr, n, suma)) {
                        im(mtr, n); flag = 1;
                    }
                } if (!flag) printf("Ne postoji nijedno resenje koje predstavlja savrsen kvadrat\n\n");
            }
            else printf("Morate prvo uneti vrednosti\n\n");
        }
        else if (str == '6') break;
    }
    return 0;
}

//pravi red
Red* kr() {     //konstruktor reda
    Red* r = (Red*)calloc(1, sizeof(Red));
    if (!r) return NULL;
    r->prvi = NULL; r->posl = NULL;
    r->d = 0;
    return r;
}

//da li je red prazan
int empty(Red* r) {
    if (r->d) return 0;
    else return 1;
}

//dodaje na red
void push(Red* r, Cv* cv) {
    if (!(empty(r))) {
        Cl* novi = (Cl*)calloc(1, sizeof(Cl));
        r->posl->sl = novi;
        novi->pr = r->posl;
        r->posl = novi;
        novi->cvor = cv;
        novi->mat = cv->mat;
        novi->sl = NULL;
    }
    else {
        Cl* novi = (Cl*)calloc(1, sizeof(Cl));
        r->prvi = novi; r->posl = novi;
        novi->sl = NULL; novi->pr = NULL;
        novi->cvor = cv;
        novi->mat = cv->mat;
    } r->d++;
}

//skida sa reda
void pop(Red* r) {
    if (!empty(r) && r->d != 1) {
        Cl* novi = r->prvi;
        r->prvi->sl->pr = NULL;
        r->prvi = r->prvi->sl;
        r->d--;
        free(novi);
    }
    else if (r->d == 1) {
        Cl* novi = r->prvi;
        r->prvi->sl = NULL;
        r->prvi->pr = NULL;
        r->prvi = NULL;
        r->posl = NULL;
        r->d--;
        free(novi);
    }
}

//skida sa kraja reda
int** popback(Red* r, int n) {
    if (r->d == 1) {
        Cl* novi = r->prvi;
        r->prvi->sl = NULL;
        r->prvi->pr = NULL;
        r->prvi = NULL;
        r->posl = NULL;
        r->d--;
        int** mat = kop(novi->mat, n);
        free(novi);
        return mat;
    }
    else if (!empty(r)) {
        Cl* novi = r->posl;
        r->posl->pr->sl = NULL;
        r->posl = r->posl->pr;
        r->d--;
        int** mat = kop(novi->mat, n);
        free(novi);
        return mat;
    }
    else return NULL;
}

//ispisuje red
void ispisired(Red* r, int n) {
    Cl* clan = r->prvi;
    while (1) {
        im(clan->mat, n);
        clan = clan->sl;
        if (!clan) break;
    }
}

//ispisuje meni
void meni() {
    printf("Izaberite neku od ponudjenih opcija iz menija:\n");
    printf("1: Uneti dimenzije magicnog kvadrata i inicijalizovati sadrzaj\n");
    printf("2: Izracunati karakteristicnu sumu magicnog kvadrata\n");
    printf("3: Ispis izgleda formiranog stabla\n");
    printf("4: Ispis resenja magicnog kvadrata\n");
    printf("5: Ispis koja su resenja takodje i savrseni kvadrati\n");
    printf("6: Zavrsiti program\n\n");
    return;
}

//ispisuje gresku prilikom alokacije
void alerr() {
    printf("Doslo je do greske pri alokaciji memorije.");
    exit(1);
}

//upisuje pocetno stanje kvadrata
int upis(int** mag, int n, int* niz, int* nn) {
    printf("Unesite pocetni izgled kvadrata duzine stranice %d\n", n);
    printf("Za polja koja ne zelite da inicijalizujete, unestite 0\n");
    int s = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            scanf_s("%d", &mag[i][j]);
            if (mag[i][j]) (*nn)--;
            s += mag[i][j];
        }
    }
    printf("Sada unesite jos %d vrednosti kojima zelite da popunite magicni kvadrat\n", *nn);

    for (int i = 0; i < *nn; i++) { scanf_s("%d", &niz[i]); s += niz[i]; }
    return s;
}

//proverava da li elementi obrazuju aritmeticki niz
int arn(int** m, int* niz, int n, int nn) {
    int* ar = (int*)calloc(pow(n, 2), sizeof(int));
    int k = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (m[i][j]) ar[k++] = m[i][j];
        }
    } for (int i = 0; i < nn; i++) ar[k++] = niz[i];
    selectionSort(ar, k);
    int d = ar[1] - ar[0];
    for (int i = 1; i < k - 1; i++) {
        int dd = ar[i + 1] - ar[i];
        if (dd != d) { free(ar); return 0; }
    } free(ar); return 1;
}

//sortira niz
void selectionSort(int* v, int n) {
    for (int i = 0; i < n - 1; i++) {
        int mn = i;
        for (int j = i + 1; j < n; j++) {
            if (v[j] < v[mn]) mn = j;
        }
        int t = v[i]; v[i] = v[mn]; v[mn] = t;
    }
}

void levelorder(Cv* stablo, Red* r, int n) {
    push(r, stablo); int i = 0,k=0,l1=0,l2=0,l=0;
    Cl* clan = r->prvi; 
    Cl* tr=clan;
    printf("Nivo 0:\n\n");
    im(tr->mat,n); l++; 
    //matrice(r,n,&tr,&l);
    while (1) {
        i = 0; k=0;
        while (clan->cvor->deca[i]) {push(r, clan->cvor->deca[i++]); k=1;}
        l1=nivo(clan->mat,n);
        if (clan->sl) {l2=nivo(clan->sl->mat,n); 
        if (l2<l1) {tr=tr->sl;matrice(r,n,&tr,&l);}}
        clan = clan->sl;
        //if (k){tr=tr->sl;matrice(r,n,&tr,&l);}
        if (!clan) break;
    } while (!empty(r)) pop(r);
}

//iterativni postorder obilazak stabla
void postorder(Cv* koren, Red* r, int n) {
    int i = 0;
    push(r, koren);
    Cl* t = r->prvi;
    while (1) {
        i = 0;
        while (t->cvor->deca[i]) i++;
        while (i > 0) { push(r, t->cvor->deca[i - 1]); i--; }
        t = t->sl; if (!t) break;
    }
}

//provera da li je kvadrat savrsen
int savrsen(int** m, int n, int s) {
    s = s / n;
    for (int k = 1; k < n; k++) {
        int sd = 0;
        for (int j = k, i = 0; i < n; i++) {
            sd += m[i][j % n]; j++;
        }
        if (sd != s) return 0;
    }
    for (int k = 0; k < n - 1; k++) {
        int sd = 0;
        for (int j = n - k - 1, i = n - 1; i >= 0; i--) {
            sd += m[i][j % n]; j++;
        }
        if (sd != s) return 0;
    } return 1;
}

void err() {
    printf("Pogresan unos, pokusajte ponovo\n\n");
}

//ispisuje svasta nesto
void matrice(Red* r,int n, Cl** tr, int *l){
    printf("\nNivo %d:\n\n", (*l)++);
    Cl* t= *tr; Cl *p=*tr;
    for (int i=0;i<n;){
        for (int j=0;j<n;j++) printf("%d ", (*tr)->mat[i][j]);
        printf("\t"); p=*tr;
        *tr=(*tr)->sl; 
        if (!*tr) {*tr=t; printf("\n");i++; if (i==n) *tr=p;}
        
    } 
}

int nivo(int **m,int n){
    int c=0;
    for (int i=0;i<n;i++){
        for (int j=0;j<n;j++)
            if (!m[i][j]) c++;
    } return c;
}