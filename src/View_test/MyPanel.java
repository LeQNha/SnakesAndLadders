package View_test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel{
	Image map;
	Image icon;
	public MyPanel() {
		this.setPreferredSize(new Dimension(500,500));
		
//		map = new ImageIcon("map2.jpg").getImage();
		try {
			map = ImageIO.read(getClass().getResourceAsStream("Snk LĐ/map2.jpg"));
			icon = ImageIO.read(getClass().getResourceAsStream("Snk LĐ/image.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawLine(0, 0, 500, 500);
		g2D.drawImage(map, 1, 1, 300, 300, null);
		g2D.drawImage(icon,50,50, 50,50, null);
	}
}
