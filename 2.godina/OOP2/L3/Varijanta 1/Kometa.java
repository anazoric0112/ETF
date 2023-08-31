package svemir;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Kometa extends NebeskoTelo{
	
	private int orijentacija=(int) Math.random()*90;;
	public Kometa(int x, int y,int r) {
		super(x, y, Color.GRAY, r);
	}
	
	@Override
	public void paint(Graphics g) {
		ArrayList<Integer> xs=new ArrayList<>();
		ArrayList<Integer> ys=new ArrayList<>();
		
		for (int i=0;i<5;i++) {
			Double xc=this.r*Math.cos(Math.PI/180*(orijentacija+72*i))+this.x;
			Double yc=this.r*Math.sin(Math.PI/180*(orijentacija+72*i))+this.y;
			xs.add(xc.intValue()); ys.add(yc.intValue());
		}
		xs.add(xs.get(0)); ys.add(ys.get(0));

		Color stara=g.getColor();
		g.setColor(this.boja);
		
		GeneralPath petougao = new GeneralPath();
		petougao.moveTo(xs.get(0), ys.get(0));
		for (int i=1;i<6;i++) {
			petougao.lineTo(xs.get(i), ys.get(i));
		}
		petougao.closePath();
		Graphics2D g2=(Graphics2D) g;
		g2.fill(petougao);
		
		g.setColor(stara);
		
	}
	
}
