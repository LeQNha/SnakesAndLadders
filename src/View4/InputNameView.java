package View4;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Main4.Player;
import Main4.PlayerHandler;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

public class InputNameView extends JFrame {
	JLabel inputNameLbl = new JLabel("Tên: ");
	JTextField inputNameTxTF = new JTextField();
	JButton okBtn = new JButton("OK");
	Image image;
	
	public InputNameView() {
		this.setSize(300, 209);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		inputNameLbl.setBounds(10,50,40,30);
		inputNameTxTF.setBounds(60,51,200,30);
		
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerHandler.inputName = inputNameTxTF.getText();
				PlayerHandler.hasName = true;
//				System.out.println("có tên");
				
				Socket soc;
				try {
					soc = new Socket("localhost", 1111);
					Player player = new Player(soc, inputNameTxTF.getText());
					player.ListenToMsg();
					dispose();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		});
		
		okBtn.setBounds(107,105,71,39);
		getContentPane().add(inputNameLbl);
		getContentPane().add(inputNameTxTF);
		getContentPane().add(okBtn);
		
		okBtn.setBackground(Color.white);
		okBtn.setFocusable(false);
		inputNameLbl.setFont(new Font("Tahoma",Font.BOLD,15));
		
		this.setVisible(true);
		
		image = new ImageIcon("name.png").getImage();
		repaint();
		
	}
	@Override
	public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 20, 20, null);
    }
}
