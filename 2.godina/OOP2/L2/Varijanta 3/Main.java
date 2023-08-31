package skijanje;

public class Main {
	public static void main(String args[]) {
		
		Staza staza=new Staza("Kopaonik");
		staza.dodaj(new Teska(40,5));
		staza.dodaj(new Deonica(100,20){

			@Override
			public double ubrzanje() {
				return 9.81*Math.sin(nagib*Math.PI/180);
			}

			@Override
			public char oznaka() {
				return 'I';
			}
		});
		staza.dodaj(new Deonica(100,20){

			@Override
			public double ubrzanje() {
				return 9.81*Math.sin(nagib*Math.PI/180);
			}

			@Override
			public char oznaka() {
				return 'M';
			}
		});
		
		staza.dodaj(new Deonica(100,20){

			@Override
			public double ubrzanje() {
				return 9.81*Math.sin(nagib*Math.PI/180);
			}

			@Override
			public char oznaka() {
				return 'M';
			}
		});

		//staza.dodaj(new Teska(600,7));
		staza.dodaj(new Deonica(100,20){

			@Override
			public double ubrzanje() {
				return 9.81*Math.sin(nagib*Math.PI/180);
			}

			@Override
			public char oznaka() {
				return 'M';
			}
		});
		
		try {
			System.out.println(staza.oznaka());
		} catch (GOznaka e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
