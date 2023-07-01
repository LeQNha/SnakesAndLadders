package View_test;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map_2 extends JFrame{
	MyPanel panel;
	public Map_2() {
		panel = new MyPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(panel);
		this.pack();
		
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
