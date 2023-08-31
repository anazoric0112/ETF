package parcele;

public class Baterija {
		
	private int maks;
	private int trenutno;
	
	public Baterija(int k) {
		maks=k;
		trenutno=k;
	}
	
	public synchronized void dodajE(int e) {
		if (trenutno+e>maks) trenutno=maks;
		else trenutno+=e;
	}
	public synchronized void isprazni() {
		trenutno=0;
	}
	public synchronized boolean puna() {
		return maks==trenutno;
	}
	
	public synchronized int tren() {
		return trenutno;
	}
}
