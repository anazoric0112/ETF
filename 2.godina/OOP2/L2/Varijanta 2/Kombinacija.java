package revija;

public class Kombinacija {
	
	private int broj;
	private Nosivo[] stvari;
	private int d;
	
	public Kombinacija(int broj) {
		this.broj=broj;
		stvari=new Nosivo[broj];
		d=0;
	}
	
	public void dodaj(Nosivo n) throws GDodavanje {
		if (d==broj) throw new GDodavanje();
		stvari[d++]=n;
	}
	
	public int dohvBrStvari() {return d;}
	public int dohvMaxBrStvari() {return broj;}
	
	public Nosivo dohvStvar(int i) throws GIndeks{
		if (i>=d) throw new GIndeks();
		return stvari[i];
	}
	
	public String toString() {
		StringBuilder ret=new StringBuilder("[");
		for (int i=0;i<d;i++) {
			ret.append(stvari[i]);
			if (i!=d-1) ret.append(", ");
		} ret.append("]");
		return ret.toString();
	}
}
