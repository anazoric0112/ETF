package revija;

public class Odeca implements Nosivo{
	
	private String naziv;
	private Velicina v;
	
	public Odeca (String naziv, Velicina v) {
		this.naziv=naziv;
		this.v=v;
	}
	
	public String dohvNaziv() {return naziv;}
	public Velicina dohvVelicinu() {return v;}
	
	@Override
	public boolean odgovara(Model m) {
		if (!v.manja(m.dohvVelicinu())) return true;
		return false;
	}
	
	public String toString() {
		return "odeća "+naziv+" "+v;
	}
	
}
