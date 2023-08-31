package revija;

public class Model {
	private static int id=0;
	private final int myid=id++;
	
	private Velicina v;
	
	public Model(Velicina v) {
		this.v=v;
	}
	
	public int dohvId() {return myid;}
	public Velicina dohvVelicinu() {return v;}
	
	public String toString() {
		return "Model "+myid+" ("+v+")";
	}
}
