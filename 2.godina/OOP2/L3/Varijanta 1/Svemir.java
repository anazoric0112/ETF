package svemir;

import java.awt.*;
import java.util.ArrayList;

public class Svemir extends Canvas{
	
	private Color pozadina=Color.BLACK;
	private ArrayList<NebeskoTelo> tela=new ArrayList<>();;
	private Nit nit=new Nit();
	
	public void dodajTelo(NebeskoTelo n) {
		tela.add(n);
	}

	public synchronized void start() {
		nit.start();
	}
	
	public synchronized void finish() {
		if (nit != null) nit.interrupt();
	}
	
	public Color dohvBoju() {return pozadina;}
	
	public class Nit extends Thread{
		
		public void run() {
			try {
				for (int i=0;i<tela.size();i++) {
					tela.get(i).paint(getGraphics());
					Thread.sleep(100);
				}
				repaint();
				
				while (true) {
					ArrayList<Integer> indeksi=new ArrayList<>();
					for (int i=0;i<tela.size();i++) {
						tela.get(i).promeniY(5);
						tela.get(i).paint(getGraphics());
						if (tela.get(i).dohvY()>400) indeksi.add(i);
					}
					for (int i=0;i<indeksi.size();i++) {
						tela.remove(tela.get(indeksi.get(i)));
					}
					repaint();
					Thread.sleep(100);
				}
			}catch(InterruptedException e) {}
		}
	}
	
}
