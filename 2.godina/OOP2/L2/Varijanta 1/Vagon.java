package banditi;

public class Vagon {
	class Node{
		private Node next;
		private Bandit bandit;
		
		Node(Node n,Bandit b){
			next=n; bandit=b;
		}
	}
	
	private Node banditi=null;
	private Node posl=null;
	private int d=0;
	
	public Vagon() {}
	
	public void dodajBandita(Bandit b) {
		Node novi=new Node(null,b);
		if (d==0) banditi=novi; 
		else posl.next=novi;
		posl=novi; d++;
	}
	
	public boolean sadrziBandita(Bandit b) {
		for (Node t=banditi;t!=null;t=t.next) {
			//if (b.dohvatiBrojZlatnika()==t.bandit.dohvatiBrojZlatnika()
				//	&& b.dohvatiTim()==t.bandit.dohvatiTim()) return true;
			if (t.bandit==b) return true;
		}
		return false;
	}
	
	public void ukloniBandita(Bandit b) {
		Node prev=banditi;
		for (Node t=banditi;t!=null;t=t.next) {
			if (b.dohvatiBrojZlatnika()==t.bandit.dohvatiBrojZlatnika()
					&& b.dohvatiTim()==t.bandit.dohvatiTim()) {
				prev.next=t.next; d--;
				if (d==0) {
					banditi=null;
					posl=null;
				}
				break;
			} prev=t;
		}
	}
	
	public int dohvatiBrojBandita() {return d;}
	
	public Bandit dohvatiBandita(int poz) {
		if (poz>=d) return null;
		Node t=banditi;
		for (int i=0;i<poz && t!=null;i++,t=t.next);
		return t.bandit;
	}
	
	public String toString() {
		StringBuilder ret=new StringBuilder("[");
		for (Node t=banditi;t!=null;t=t.next) {
			ret.append(t.bandit);
			if (t.next!=null) ret.append(", ");
		}ret.append("]");
		return ret.toString();
	}
	
}
