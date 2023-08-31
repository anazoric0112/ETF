package skijanje;

public abstract class Deonica {
	protected double duzina;
	protected double nagib;
	
	public Deonica(double duzina, double nagib) {
		this.duzina=duzina;
		this.nagib=nagib;
	}
	
	public double duzina() {
		return duzina;
	}
	public double nagib() {
		return nagib;
	}
	
	public abstract double ubrzanje();
	public abstract char oznaka();
	
	public double vreme(double vp) {
		return (brzina(vp)-vp)/ubrzanje();
	}
	
	public double brzina(double vp) {
		return Math.sqrt(2*ubrzanje()*duzina+vp*vp);
	}
	public String toString() {
		return oznaka()+"("+duzina+","+nagib+")";
	}
}
