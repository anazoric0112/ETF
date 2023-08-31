package revija;

public class Velicina {
	public enum Oznaka{S,M,L;}
	
	private Oznaka o;
	public Velicina(Oznaka o) {
		this.o=o;
	}
	
	public Oznaka dohvOznaku() {return o;}
	
	public boolean manja(Velicina v) {
		if (o==Oznaka.S && (v.o==Oznaka.M || v.o==Oznaka.L)) return true;
		if (o==Oznaka.M && v.o==Oznaka.L) return true;
		return false;
	}
	
	public String toString() {return o.toString();}
}
