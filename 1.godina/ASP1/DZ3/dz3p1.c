#include <stdlib.h>
#include <stdio.h>
#include <string.h>

typedef struct graf{
    int edg[100];
    int ind[31];
    char cv[30];
    int n;
}g;
typedef struct Par{
    int potez;
    int indeks;
    int tata;
}p;

g* ks(int n);
void ds(g*graf);
void dc(g* graf,char a);
void dg(g* graf,char a,char b);
void ug(g* graf,char a,char b);
void uc(g* graf,char a);
void ispis(g* graf);
void err();
void meni();
p* maja(g*graf,char s, char e,p*niz);
p* sanja(g*graf,char s,char e,p*niz);
void pp(p*rez,p*niz,g* graf);
void pp1(p*rez,p*niz,g* graf);

g* ks(int n){
    g* novi=(g*)calloc(1,sizeof(g));
    if (!novi) {err();return NULL ;}
    for (int i=0;i<32;i++) novi->ind[i]=-1;
    novi->n=n;
    for (int i=0;i<100;i++) novi->edg[i]=-1;
    for (int i=0;i<n;i++) {
        novi->ind[i]=-1;
        char x;
        printf("Uneti karakter kao ime %d. polja\n",i+1);
        scanf(" %c",&x); 
        novi->cv[i]=x;
    }
    return novi;
}

void ds(g*graf){
    if (!graf) return;
    free(graf); return;
}

void dc(g* graf,char a){
    graf->cv[graf->n]=a;
    graf->ind[graf->n+1]=graf->ind[graf->n];
    (graf->n)++; return;
}

void dg(g* graf,char a,char b){
    int ai=-1,bi=-1;
    for (int i=0;i<graf->n;i++) {
        if (graf->cv[i]==a) ai=i;
        if (graf->cv[i]==b) bi=i; 
        if (ai>=0 && bi>=0) break;
    }
    int d=graf->ind[ai+1];
    if (d<0) d++;
    int kraj=graf->ind[graf->n];
    for (int i=kraj-1;i>=d;i--){
        graf->edg[i+1]=graf->edg[i];
    } 
    graf->edg[d]=bi;
    
    if (graf->ind[ai]==-1) graf->ind[ai]=0;
    for (int i=ai+1;i<=graf->n;i++){
        if (graf->ind[i]==-1) graf->ind[i]=0;
        (graf->ind[i])++;
    } 
    return;
}

void ug(g* graf,char a,char b){
    int p,q,bi,ai;
    for (int i=0;i<graf->n;i++){
        if (graf->cv[i]==b) {bi=i; break;}
    }
    for (int i=0;i<graf->n;i++){
        if (graf->cv[i]==a) {
            ai=i;
            p=graf->ind[i];
            q=graf->ind[i+1];
            if (p==q) return;
            break;
        }
    } int f=0;
    int kraj=graf->ind[graf->n];
    for (int i=p;i<=kraj;i++){
        if (graf->edg[i]==bi) f=1;
        if (f) graf->edg[i]=graf->edg[i+1];
    } if (!f) return;
    for (int i=ai+1; i<=graf->n;i++){
        (graf->ind[i])--;
    }
    return;
}

void uc(g* graf,char a){
    for (int i=0;i<graf->n;i++){
        if (graf->cv[i]==a){
            for (int k=0;k<graf->n;k++){
                if (k==i) continue;
                ug(graf,graf->cv[k],graf->cv[i]);
            }
            int j=graf->ind[i];
            int jj=graf->ind[i+1];
            int d;
            if (j==-1 || jj==-1) d=0;
            else d=jj-j;
            int kraj=graf->ind[graf->n]; j--;
            while (kraj!=j++) 
                graf->edg[j]=graf->edg[j+d];

            for (j=i;j<=graf->n;j++){
                graf->ind[j]=graf->ind[j+1]-d;
                if (j<graf->n-1) 
                    graf->cv[j]=graf->cv[j+1];
            } 
            for (j=0;j<kraj;j++)
                if (graf->edg[j]>i) (graf->edg[j])--;
            (graf->n)--;
            break;
        }
    } return;
}

void ispis(g* graf){
    printf("Grane: "); int i=0;
    while  (graf->edg[i]!=-1){
        printf("%d ",graf->edg[i++]);
    } printf("\n");
    printf("Indeksi: ");
    for (i=0;i<=graf->n;i++){
        printf("%d ",graf->ind[i]);
    } printf("\n");
    printf("Cvorovi: ");
    for (i=0;i<graf->n;i++){
        printf("%c, ",graf->cv[i]);
    } printf("\n\n");
    return;
}

int main(){
    g* tabla; int ima=0;
    p* rez1, *rez2, *niz1, *niz2;
    char s,e;
    while (1){
        meni();
        int w; scanf(" %d",&w);
        if (w==1){ int pl;
            if (ima) {
                printf("Tabla vec postoji.\n");
                printf("Pokusajte da je obrisete i zatim napravite novu\n");
            } else{
                printf("Koliko polja zelite da tabla ima inicijalno?\n");
                scanf(" %d", &pl);
                tabla=ks(pl); ima=1;
                rez1=(p*)calloc(20,sizeof(p));
                rez2=(p*)calloc(20,sizeof(p));
                printf("Odaberite pocetak i cilj:\n");
                printf("Pocetak:\n"); scanf(" %c",&s);
                printf("Cilj:\n"); scanf(" %c", &e);
                printf("\n\n");
            }
        }
        else if (w==2){int flag=1;
            while (flag){
                char x;
                printf("Uneti karakter kao ime polja koje zelite dodati\n");
                printf("Vec postojeca imena polja:\n");
                for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
                scanf(" %c",&x); 
                for (int i=0;i<tabla->n;i++){
                    if (tabla->cv[i]==x) {
                        printf("Polje sa tim imenom vec postoji. Pokusajte ponovo\n\n");
                        flag=1; break;
                    } else flag=0;
                } printf("\n\n");
                if (!flag) dc(tabla,x);
            }
        }
        else if (w==3){ char p1,p2;
            printf("Postojeca polja:\n");
            for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
            printf("Unesite ime polja od kog zelite da se napravi veza\n");
            scanf(" %c",&p1);
            printf("Unesite ime polja do kog zelite da se napravi veza\n");
            scanf(" %c",&p2); printf("\n\n");
            dg(tabla,p1,p2);
        }
        else if (w==4){ char x;
            printf("Postojeca polja:\n");
            for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
            printf("Unesite ime polja koje zelite da izbrisete\n");
            scanf(" %c",&x); uc(tabla,x); printf("\n\n");
        }
        else if (w==5){ char p1,p2;
            printf("Postojeca polja:\n");
            for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
            printf("Unesite ime polja od kog zelite da izbrisete vezu\n");
            scanf(" %c",&p1);
            printf("Unesite ime polja do kog zelite da izbrisete vezu\n");
            scanf(" %c",&p2);
            ug(tabla,p1,p2); printf("\n\n");
        }
        else if (w==6){
            if (!ima) printf("Tabla ne postoji\n");
            else {
                ds(tabla); ima=0; 
                free(rez1);free(niz1);
                free(rez2);free(niz2);
            }  printf("\n\n");
        }
        else if (w==7){
            printf("Postojeca polja:\n");
            for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
            printf("Odaberite novo pocetno polje\n");
            scanf(" %c",&s);  printf("\n\n");
        }
        else if (w==8){
            printf("Postojeca polja:\n");
            for (int i=0;i<tabla->n;) printf("%c ",tabla->cv[i++]); printf("\n");
            printf("Odaberite novo ciljno polje\n");
            scanf(" %c",&e);  printf("\n\n");
        }
        else if (w==9) ispis(tabla);
        else if (w==10){
            niz1=(p*)calloc(1000,sizeof(p));
            rez1=maja(tabla,s,e,niz1);
            int xm=rez1[0].potez;
            niz2=(p*)calloc(1000,sizeof(p));
            rez2=sanja(tabla,s,e,niz2);
            int xs=rez2[0].potez;
            if (xs%3==1){
                xs=xs-xs/3;
            } else xs= xs*2/3;
            printf("Najmanji broj poteza koji je potreban Maji je %d, a Sanji %d\n",xm,xs);
            if (xs>xm) printf("Pobednik je Maja\n\n");
            else if (xm>xs) printf("Pobednik je Sanja\n\n");
            else printf("Nereseno je\n\n");
        }
        else if (w==11){
            niz1=(p*)calloc(1000,sizeof(p));
            rez1=maja(tabla,s,e,niz1);
            int xm=rez1[0].potez;
            printf("Majin put: \n");
            pp1(rez1,niz1,tabla);
            
            niz2=(p*)calloc(1000,sizeof(p));
            rez2=sanja(tabla,s,e,niz2);
            int xs=rez2[0].potez;
            if (xs%3==1){
                xs=xs-xs/3;
            } else xs= xs*2/3;
            
            printf("Sanjin put: \n");
            pp1(rez2,niz2,tabla);
            printf("Najmanji broj poteza koji je potreban Maji je %d, a Sanji %d\n\n",xm,xs);
        }
        else if (w==12){
            niz1=(p*)calloc(1000,sizeof(p));
            rez1=maja(tabla,s,e,niz1);
            int xm=rez1[0].potez;
            printf("Majini putevi: \n");
            pp(rez1,niz1,tabla);
            niz2=(p*)calloc(1000,sizeof(p));
            rez2=sanja(tabla,s,e,niz2);
            int xs=rez2[0].potez;
            if (xs%3==1){
                xs=xs-xs/3;
            } else xs= xs*2/3;
            printf("Sanjini putevi: \n");
            pp(rez2,niz2,tabla);
            printf("Najmanji broj poteza koji je potreban Maji je %d, a Sanji %d\n\n",xm,xs);
        }
        else if (w==13) return 0;
    }
    return 0;
}

void err(){
    printf("Doslo je do greske pri alokaciji\n\n");
}

void meni(){
    printf("Izaberite jednu od opcija iz menija:\n\n");
    printf("1. Kreiranje table\n");
    printf("2. Dodavanje polja u postojecu tablu\n");
    printf("3. Dodavanje veze izmedju 2 polja\n");
    printf("4. Brisanje polja\n");
    printf("5. Brisanje veze izmedju 2 polja\n");
    printf("6. Brisanje postojece table\n");
    printf("7. Promeniti pocetno polje\n");
    printf("8. Promeniti ciljno polje\n");
    printf("9. Ispisati izgled table u pogodnoj reprezentaciji\n");
    printf("10. Odrediti pobednika i najmanji broj poteza potreban Sanji i Maji\n");
    printf("11. Odrediti bar jedan put sa najmanjim brojem poteza koji vodi Sanju i Maju do cilja\n");
    printf("12. Odrediti sve puteve sa najmanjim brojem poteza koji vode Sanju i Maju do cilja\n");
    printf("13. Izaci iz programa\n\n");
    return;
}

p* maja(g*graf,char s, char e,p*niz){
    int si=-1,ei=-1;
    for (int i=0;i<graf->n;i++){
        if (graf->cv[i]==s) si=i;
        if (graf->cv[i]==e) ei=i;
        if (si>=0 && ei>=0) break;
    }int i=0;
    
    niz[i].potez=0;
    niz[i].indeks=si;
    niz[i].tata=-1;  i++;
    int tr=si;
    int tata=0;
    int koji=0;
    p *resenja=(p*)calloc(50,sizeof(p));
    int j=0;
    int nivo=0,nivo2=0;
    int flag=0;
    while (1){
        tr=niz[koji].indeks;
        int poc=graf->ind[tr];
        int kraj=graf->ind[tr+1];
        
        for (int k=poc;k<kraj;k++){
            niz[i].potez=niz[koji].potez+1;
            niz[i].indeks=graf->edg[k];
            niz[i].tata=koji;
            if (niz[i].indeks==ei) {
                if (!flag) nivo=niz[i].potez;
                flag=1;
                resenja[j++]=niz[i]; 
            } i++; 
        } koji++;
        if (flag && niz[i-1].potez>nivo) {
            resenja[j].tata=10000;
            break;
        }
    } return resenja;
}

void pp(p*rez,p*niz,g* graf){
    int i=0;
    while (rez[i].tata<10000){
        char *potezi=(char*)calloc(50,sizeof(char));
        p tr=rez[i];
        for (int j=rez[i].potez;j>=0;j--){
            potezi[j]=graf->cv[tr.indeks];
            if (tr.tata>=0) tr=niz[tr.tata];
            else break;
        }
        printf("Resenje %d:\n",i+1);
        for (int j=0;j<=rez[i].potez;j++){
            printf("%c --> ",potezi[j]);
        }  i++; printf("kraj\n\n");
    }
    return;
}
void pp1(p*rez,p*niz,g* graf){
    char *potezi=(char*)calloc(50,sizeof(char));
    p tr=rez[0];
    for (int j=rez[0].potez;j>=0;j--){
        potezi[j]=graf->cv[tr.indeks];
        if (tr.tata>=0) tr=niz[tr.tata];
        else break;
    }
    for (int j=0;j<=rez[0].potez;j++){
         printf("%c --> ",potezi[j]);
    } printf("kraj\n\n");
    
    return;
}

p* sanja(g*graf,char s,char e,p*niz){
    int si=-1,ei=-1;
    for (int i=0;i<graf->n;i++){
        if (graf->cv[i]==s) si=i;
        if (graf->cv[i]==e) ei=i;
        if (si>=0 && ei>=0) break;
    }int i=0;
    
    niz[i].potez=0;
    niz[i].indeks=si;
    niz[i].tata=-1;  i++;
    int tr=si;
    int tata=0;
    int koji=0;
    p *resenja=(p*)calloc(50,sizeof(p));
    int j=0;
    int nivo=0,nivo2=0;
    int flag=0;
    while (1){
        tr=niz[koji].indeks;
        int poc=graf->ind[tr];
        int kraj=graf->ind[tr+1];
        
        for (int k=poc;k<kraj;k++){
            niz[i].potez=niz[koji].potez+1;
            niz[i].indeks=graf->edg[k];
            niz[i].tata=koji;
            if (niz[i].indeks==ei && niz[i].potez%3!=2) {
                if (!flag) nivo=niz[i].potez;
                flag=1;
                resenja[j++]=niz[i]; 
            } i++; 
        } koji++;
        if (flag && niz[i-1].potez>nivo) {
            resenja[j].tata=10000;
            break;
        }
    } return resenja;
}