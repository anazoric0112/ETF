package skijanje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Staza {
	private List<Deonica> deonice;
	private String naziv;
	
	public Staza (String naziv) {
		deonice=new ArrayList<>();
		this.naziv=naziv;
	}
	
	public void dodaj(Deonica d) {
		deonice.add(d);
	}
	
	public int broj() {
		return deonice.size();
	}
	
	public double duzina() {
		double sum=0;
		for (Deonica i: deonice) {
			sum+=i.duzina();
		}
		return sum;
	}
	
	public double nagib() {
		double max=0;
		for(Deonica i:deonice) {
			max=Math.max(max, i.nagib());
		}
		return max;
	}
	public char oznaka() throws GOznaka {
		if (deonice.size()==0) throw new GOznaka();
		
		HashMap <Character,Integer> chars=new HashMap<Character,Integer>();
		for (Deonica i: deonice) {
			if (!chars.containsKey(i.oznaka())) {
				chars.put(i.oznaka(),1);
			} else {
				int j=chars.get(i.oznaka());
				j++;
				chars.replace(i.oznaka(),j);
			}
		}
		char c=deonice.get(0).oznaka();
		int max=0;
		for (Map.Entry m : chars.entrySet()) {
			if ((int)m.getValue()>max) {
				//c=(char)m.getKey();
				max=(int)m.getValue();
			}
		}
		for (Deonica i: deonice) {
			if (chars.get(i.oznaka())==max) {
				return i.oznaka();
			}
		}
		return c;
	}
	
	public double brzina(double br) {
		double b=br;
		for (Deonica i: deonice) {
			b=i.brzina(b);
		}
		return b;
	}
	
	public double vreme(double br) {
		double b=br;
		double t=0;
		for (Deonica i:deonice) {
			t+=i.vreme(b);
			b=i.brzina(b);
		}
		return t;
	}
	
	public String toString() {
		StringBuilder ret=new StringBuilder(naziv+"|"+
				broj()+"|"+duzina()+"|"+nagib()+"\n[");
		for (int i=0;i<deonice.size();i++) {
			ret.append(deonice.get(i));
			if (i!=deonice.size()-1) ret.append(",");
		} ret.append("]");
		return ret.toString();
	}
}
