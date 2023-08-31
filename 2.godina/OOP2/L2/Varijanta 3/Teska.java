package skijanje;

public class Teska extends Deonica{
	
	public Teska(double duzina, double nagib) {
		super(duzina, nagib);
	}
	@Override
	public char oznaka() {return 'T';}
	
	@Override
	public double ubrzanje() {
		return 9.81*Math.sin(nagib*Math.PI/180);
	}
}
