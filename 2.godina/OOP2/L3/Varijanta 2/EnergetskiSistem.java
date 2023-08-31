package parcele;

import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;

public class EnergetskiSistem extends Frame{

	private Plac plac;
	private Panel panelDugmad=new Panel();
	
	private Baterija baterija;
	private int n;
	private int m;
	private Label btr;
	
	public EnergetskiSistem (int n,int m, int kap) {
		plac=new Plac(n,m);
		baterija=new Baterija(kap);
		this.n=n;
		this.m=m;
		baterija.isprazni(); //ovo obrisi posle, sad sluzi za debug
		
		setBounds(500,200,500,500);
		setResizable(false);
		setTitle("Energetski sistem");
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				plac.zaustaviSve();
				dispose();
			}
		});
		
		napraviDugme();
		
		btr=new Label("Baterija: "+baterija.tren());
		panelDugmad.add(btr);
		
		add(panelDugmad, BorderLayout.NORTH);
		add(plac, BorderLayout.CENTER);
		
		repaint();
	}
	
	
	public void napraviDugme() {
		Button dugme=new Button("Dodaj");
		
		dugme.addActionListener((ie)->{
			if (plac.izabrana()==null) return;
			Izbor izb=new Izbor(EnergetskiSistem.this);
			Proizvodjac he=izb.izbor();
			if (he==null) return;
			plac.azurirajIzabranu(he);
			
			for (int i=0;i<n;i++) {
				for (int j=0;j<m;j++){
					if (plac.jeLiHE(i, j)) {
						int broj=0;
						if (plac.jeLiVP(i-1,j)) broj++;
						if (plac.jeLiVP(i-1,j+1)) broj++;
						if (plac.jeLiVP(i-1,j-1)) broj++;
						if (plac.jeLiVP(i+1,j)) broj++;
						if (plac.jeLiVP(i+1,j+1)) broj++;
						if (plac.jeLiVP(i+1,j-1)) broj++;
						if (plac.jeLiVP(i,j+1)) broj++;
						if (plac.jeLiVP(i,j-1)) broj++;
						((Hidroelektrana)plac.dohv(i, j)).postaviBrojVP(broj);
					}
				}
			}
			plac.removeAll();
			plac.dodajParcele();
			plac.revalidate();
			plac.repaint();
			EnergetskiSistem.this.repaint();
			
			he.start();
		});
		
		
		Button pauza=new Button("pauza");
		pauza.addActionListener((ie)->{
			plac.pauzirajSve();
		});
		Button nastavi=new Button("nastavi");
		nastavi.addActionListener((ie)->{
			plac.nastaviSve();
		});
		Button zaustavi=new Button("zaustavi");
		zaustavi.addActionListener((ie)->{
			plac.zaustaviSve();
		});

		panelDugmad.add(dugme);
		panelDugmad.add(pauza);
		panelDugmad.add(nastavi);
		panelDugmad.add(zaustavi);
		
	}
	
	public static void main(String args[]) {
		new EnergetskiSistem(6,6,1000);
	}

	public void prBat() {
		btr.setText("Baterija: "+baterija.tren());
		repaint();
	}
	
	public class Izbor extends Dialog{
		
		private CheckboxGroup izbori = new CheckboxGroup();
		
		public Izbor(Frame owner) {
			super(owner);
			setTitle("Izbor");
			int xc=owner.getX()+owner.getWidth()/2-150;
			int yc=owner.getY() + owner.getHeight() / 2-100;
			setBounds(xc,yc, 300, 200);
			setResizable(false);
			setModalityType(ModalityType.APPLICATION_MODAL);
			
			Panel tekstP=new Panel();
			Label tekst=new Label("Izaberite tip proizvodjaca");
			tekstP.add(tekst);
			add(tekstP,BorderLayout.NORTH);
			
			Checkbox he = new Checkbox("Hidroelektrana", true, izbori);
			Checkbox drugo = new Checkbox("Drugo", false, izbori);
			Panel cb=new Panel();
			cb.add(he);
			cb.add(drugo);
			add(cb,BorderLayout.CENTER);
			
			Panel dugmici = new Panel();
			Button ok=new Button("Izabrano");
			ok.addActionListener((ae) -> {
				dispose();
			});
			dugmici.add(ok);
			add(dugmici, BorderLayout.SOUTH);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
			
			setVisible(true);
		}
		
		public Proizvodjac izbor() {
			Proizvodjac p;
			String izabrano=izbori.getSelectedCheckbox().getLabel();
			if (izabrano=="Hidroelektrana") return new Hidroelektrana(baterija);
			else return null;
		}
		
	}
}
