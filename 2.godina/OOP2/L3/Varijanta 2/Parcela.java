package parcele;

import java.awt.*;
import java.awt.event.*;

public class Parcela extends Label{
	
	private static Parcela izabrana=null;
	
	protected char oznaka;
	protected Color boja;
	protected Font font= new Font("Serif", Font.BOLD, 14);
	
	
	public Parcela(String o, Color b) { 
		this.oznaka=o.charAt(0);
		this.boja=b;
		setFont(font);
		setText(o);
		setForeground(Color.WHITE);
		setBackground(b);
		setAlignment(CENTER);
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (izabrana!=null) izabrana.setFont(font);
				izabrana=Parcela.this;
				izabrana.setFont(new Font("Serif", Font.BOLD, 20));
				getParent().dispatchEvent(e);
			}
		});
		
	}
	
	public void promeniPozadinu(Color b) {
		boja=b;
		this.setBackground(b);
		getParent().repaint(); //treba li mi
	}
	

}
