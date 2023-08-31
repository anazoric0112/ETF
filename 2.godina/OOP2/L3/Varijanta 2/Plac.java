package parcele;

import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

public class Plac extends Panel{
	
	private static Parcela izabrana; //static izabrana 
	private int i,j; //koordinate izabrane
	
	private int n; //broj redova
	private int m; //broj kolona
	private int x; //sirina parcele
	private int y; //visina parcele

	private Parcela[][] resetka;
	
	public Plac(int n, int m) {
		setLayout(new GridLayout(n,m,3,3));
		this.n=n;
		this.m=m;
		resetka=new Parcela[n][m];
		x=500/n;
		y=500/m;
		
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				resetka[i][j]=Math.random()<0.7?new TravnataPovrs():new VodenaPovrs();
				add(resetka[i][j],i,j);
			}
		}
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Parcela p=(Parcela)e.getComponent();
				azurirajIzabranu(p);
			}
		});
		
	}
	public void zaustaviSve() {
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				if (jeLiPR(i,j)) 
					synchronized(resetka[i][j]) {((Proizvodjac)resetka[i][j]).interrupt();}
			}
		}
	}
	public void pauzirajSve() {
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				if (jeLiPR(i,j)) 
					synchronized(resetka[i][j]) {((Proizvodjac)resetka[i][j]).pause();}
			}
		}
	}
	public void nastaviSve() {
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				if (jeLiPR(i,j)) 
					synchronized(resetka[i][j]) {((Proizvodjac)resetka[i][j]).resume();}
			}
		}
	}
	
	public Parcela dohv(int i, int j) {
		if (i<0 || j<0 || i>=n || j>=m) return null;
		return resetka[i][j];
	}
	public boolean jeLiVP(int i, int j) {
		if (i<0 || j<0 || i>=n || j>=m) return false;
		else return resetka[i][j] instanceof VodenaPovrs;
	}
	public boolean jeLiHE(int i, int j) {
		if (i<0 || j<0 || i>=n || j>=m) return false;
		else return resetka[i][j] instanceof Hidroelektrana;
	}
	public boolean jeLiPR(int i, int j) {
		if (i<0 || j<0 || i>=n || j>=m) return false;
		else return resetka[i][j] instanceof Proizvodjac;
	}
	
	public void dodajParcele() {
		for (int i=0;i<n;i++) {
			for (int j=0;j<m;j++) {
				add(resetka[i][j],i,j);
			} 
		}
	}
	
	public void azurirajIzabranu(Parcela p) {
		if (p instanceof Proizvodjac) {
			resetka[i][j]=p;
			izabrana=null; return;
		} else izabrana=p;
		for(int ii=0;ii<n;ii++) {
			for (int jj=0;jj<m;jj++) {
				if(resetka[ii][jj]==p) {
					this.i=ii; this.j=jj;
					return;
				}
			}
		}
	}
	public Parcela izabrana() {
		return izabrana;
	}
	
	public void prBat() {
		((EnergetskiSistem) getParent()).prBat();
	}
}
