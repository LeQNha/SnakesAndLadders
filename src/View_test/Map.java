package View_test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Map extends JFrame {
	JLabel panel = new JLabel();
	Image image;
	public Map() {
		this.setSize(400, 600);
		this.setLayout(null);
		
		
		panel.setBounds(10, 10, 653, 410);
		getContentPane().add(panel);
		
		panel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Map.class.getResource("image.png"))));
	
		this.setVisible(true);
}
	
	  protected void paintComponent(Graphics g) {
	    super.paint(g);
	    g.drawImage(image, 0, 0, panel);

	  }
	  public static void main(String[] args) {
		new Map();
	}
}
