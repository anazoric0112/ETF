package svemir;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Simulator extends Frame{
	
	private Svemir svemir= new Svemir();
	private Generator generator= new Generator(svemir);
	private Panel panel=new Panel();
	
	public Simulator() {
		
		setBounds(500, 200, 200, 400);
		setResizable(false);
		setTitle("Svemir");
		
		napravi();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setVisible(true);
	}
	
	public void napravi() {
		svemir.setBackground(svemir.dohvBoju());
		svemir.setPreferredSize(new Dimension(200, 330));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(svemir);
		Button dugme=new Button("Pokreni!");
		Button dugmeD=new Button("Stani!");
		
		dugme.addActionListener((ie)->{
			svemir.start();
			generator.start();
			dugme.disable();
		});
		
		dugmeD.addActionListener((ie)->{
			svemir.finish();
			generator.finish();
			dugmeD.disable();
		});
		panel.add(dugme, BorderLayout.WEST);
		panel.add(dugmeD, BorderLayout.EAST);
		add(panel);
	}

	public static void main(String[] args) {
		Simulator s=new Simulator();
	}
}
