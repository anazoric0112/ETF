package parcele;

import java.awt.*;
import java.awt.event.*;

abstract public class Proizvodjac extends Parcela{
	
	protected Baterija baterija;
	protected int osnovnoVreme;
	protected int ukupnoVreme;
	
	private static boolean radi=true;
	private Nit nit=new Nit();
	
	public Proizvodjac(String o, Color b, Baterija bat, int vreme) {
		super(o, b);
		baterija=bat;
		osnovnoVreme=vreme;
		ukupnoVreme=(int)(Math.random()*300)+osnovnoVreme;
	}
	
	public void start() {
		synchronized(nit){
			nit.start();
		}
	}
	public void pause() { 
		synchronized(nit){
			radi=false;
		}
	}
	public void resume() {
		 synchronized(nit) {
			 radi=true;
			 nit.notify();
		 }
	}
	public void interrupt() {
		synchronized(nit) {
			radi=false;
			nit.interrupt();
		}
	}	
	public abstract boolean uspesno();
	public abstract int kolicina();
	
	public class Nit extends Thread {
		
		public void run() {
			try {
				while (!isInterrupted()) {
					Proizvodjac.this.setForeground(Color.WHITE);
					Thread.sleep(ukupnoVreme);	
					synchronized (this){
						while (!radi) wait();
					}
					if (uspesno()) {
						Proizvodjac.this.setForeground(Color.RED);
						baterija.dodajE(kolicina());
					} 
					if (getParent()!=null)((Plac)getParent()).prBat();

					Thread.sleep(300);
					synchronized (this){
						while (!radi) wait();
					}
				}
			}catch (InterruptedException e) {}
		}
	}

}
