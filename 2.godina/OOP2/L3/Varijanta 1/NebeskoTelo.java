package svemir;

import java.awt.*;

abstract public class NebeskoTelo extends Objekat{
	
	protected int r;
	
	public NebeskoTelo(int x, int y, Color boja,int r) {
		super(x, y, boja);
		this.r=r;
	}
	
}
