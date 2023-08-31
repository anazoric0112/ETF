package parcele;

import java.awt.*;
import java.util.ArrayList;

public class Hidroelektrana extends Proizvodjac {
	
	private int vode=0;
	
	public Hidroelektrana(Baterija bat) {
		super("H",Color.BLUE,bat,1500);
	}
	public void postaviBrojVP(int b) {
		vode=b;
	}

	@Override
	public boolean uspesno() {
		return vode>0;
	}
	@Override
	public int kolicina() {
		return vode;
	}
}
