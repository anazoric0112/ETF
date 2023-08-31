package svemir;

import java.awt.*;

public abstract class Objekat {
	
	protected int x;
	protected int y;
	protected Color boja;
	
	public Objekat(int x, int y, Color boja) {
		this.x=x;
		this.y=y;
		this.boja=boja;
	}
	
	public int dohvX() {return x;}
	public int dohvY() {return y;}
	
	public void promeniX(int x) {this.x+=x;}
	public void promeniY(int x) {this.y+=x;}

	public abstract void paint(Graphics g);
}
