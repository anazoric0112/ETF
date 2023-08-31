package banditi;

public abstract class Akcija {
	Kompozicija k;
	
	public Akcija(Kompozicija k) {
		this.k=k;
	}
	
	public abstract void izvrsi(Bandit b);
}
