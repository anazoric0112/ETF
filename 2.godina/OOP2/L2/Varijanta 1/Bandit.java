package banditi;

public class Bandit {

	public enum Tim {
		A,B;
	}
	private Tim tim;
	private int z;
	
	public Bandit(Tim tim) {
		this.tim=tim; 
		z=50;
	}
	public Tim dohvatiTim() {
		return tim;
	}
	public int dohvatiBrojZlatnika() {
		return z;
	}
	public void promeniBrojZlatnika(int a) {
		z+=a;
	}
	
	public String toString() {
		return tim.toString()+z;
	}
}
