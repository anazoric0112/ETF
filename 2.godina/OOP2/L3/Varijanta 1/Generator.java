package svemir;

public class Generator extends Thread {
	
	private Svemir svemir;
	
	public Generator(Svemir svemir) {
		this.svemir=svemir;
	}
	
	public void run() {

		try {
			while (true) {
				int x=(int)(Math.random()*200);
				int y=0;
				int r=(int)(Math.random()*20+10);
				
				svemir.dodajTelo(new Kometa(x,y,r));
				Thread.sleep(900);
				}
		} catch (InterruptedException e) {}
	}

	public synchronized void finish() {
		if(this != null) interrupt();
	}
}
