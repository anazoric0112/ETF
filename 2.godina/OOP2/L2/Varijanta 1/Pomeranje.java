package banditi;

public class Pomeranje extends Akcija {
	
	private Smer smer;
	
	public Pomeranje(Kompozicija k, Smer s) {
		super(k);
		smer=s;
	}
	
	public void izvrsi(Bandit b)  {
		for (Vagon v:k.vagoni) {
			if (v.sadrziBandita(b)) {
				try {
					k.dohvatiSusedniVagon(v, smer).dodajBandita(b);
					v.ukloniBandita(b);
					break;
				} catch (GNepostojeciVagon e) {}
			}
		}
	}
	
	public String toString() {
		return "Pomeranje: "+smer.toString();
	}

}
