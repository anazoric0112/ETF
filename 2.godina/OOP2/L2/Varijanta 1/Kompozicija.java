package banditi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Kompozicija {
	
	List <Vagon> vagoni;
	
	public Kompozicija() {
		vagoni=new ArrayList<>();
	}
	
	public void dodajVagon(Vagon b) {
		vagoni.add(b);
	}
	
	public Vagon dohvatiVagon(Bandit b) throws GNepostojeciVagon{
		for (Vagon v:vagoni) {
			if (v.sadrziBandita(b)) return v;
		}
		throw new GNepostojeciVagon();
	}
	
	public Vagon dohvatiSusedniVagon(Vagon v, Smer s) throws GNepostojeciVagon {
		Vagon ret=null;
		boolean nadjen=false;
		if (s==Smer.ISPRED) {
			for (int i=1;i<vagoni.size();i++) {
				if (v==vagoni.get(i)) {
					ret=vagoni.get(i-1);
					nadjen=true;
					break;
				}
			} 
			if (!nadjen) throw new GNepostojeciVagon();
		} else if (s==Smer.IZA) {
			for (int i=0;i<vagoni.size()-1;i++) {
				if (v==vagoni.get(i)) {
					ret=vagoni.get(i+1);
					nadjen=true;
					break;
				}
			} 
			if (!nadjen) throw new GNepostojeciVagon();
		}
		return ret;
	}
	
	public String toString() {
		StringBuilder ret=new StringBuilder();
		for (int i=0;i<vagoni.size();i++) {
			ret.append(vagoni.get(i));
			if(i!=vagoni.size()-1) ret.append("_");
			
		}
		return ret.toString();
	}
}
