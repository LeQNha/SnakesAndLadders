package View4;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Main4.DBConnection;
import Main4.Player;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;


public class LoginForm extends JFrame {
	public static JTextField userTxtF = new JTextField();
    public static JPasswordField passwordPF = new JPasswordField();
    
    JButton loginBtn = new JButton("OK");	
    
    JLabel userLbl = new JLabel("Tên đăng nhập");
    JLabel passwordLbl = new JLabel("Password:");
    JLabel titleLbl = new JLabel("LOG IN");
	
    public LoginForm() {
    	this.createLoginView();
    }
    
    public void createLoginView() {
    	this.setSize(463, 313);
    	this.setTitle("LOG IN");
    	this.setLocationRelativeTo(null);
    	getContentPane().setLayout(null);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	loginBtn.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			String u = userTxtF.getText();
    			String p = passwordPF.getText();
    			if(u.isBlank() || p.isBlank())
    				JOptionPane.showMessageDialog(null, "Không được để trống!",null, JOptionPane.ERROR_MESSAGE);
    			else {
    				Connection con = DBConnection.getConnection();
    				String sql = "SELECT *FROM account "+
							  	 "WHERE USERNAME='"+u+"' and PASSWORD ='"+p+"';";
    				ResultSet rs = null;
    				try {
						Statement st = con.createStatement();
						rs = st.executeQuery(sql);
						if(rs.next()) {
							Socket soc;
							try {
								soc = new Socket("localhost", 1111);
								Player player = new Player(soc, u);
								player.ListenToMsg();
								dispose();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else
							JOptionPane.showMessageDialog(null	, "Không tìm thấy tài khoản!","ALERT", JOptionPane.WARNING_MESSAGE);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    				
    				DBConnection.closeConnection(con);
    				
    			}
    		}
    	});
    	
    	
    	getContentPane().add(loginBtn);
    	getContentPane().add(userTxtF);
    	getContentPane().add(passwordPF);
    	userLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	getContentPane().add(userLbl);
    	getContentPane().add(passwordLbl);
    	getContentPane().add(titleLbl);
    	titleLbl.setFont(new Font("Tahoma", Font.BOLD, 14));
    	titleLbl.setHorizontalAlignment(JLabel.CENTER);
    	titleLbl.setBounds(70,20,295,20);
    	userLbl.setBounds(27,84,101,20);
    	passwordLbl.setBounds(27,133,68,20);
    	loginBtn.setBounds(165,167,116,32);
    	loginBtn.setBackground(Color.white);
    	loginBtn.setBorder(BorderFactory.createEmptyBorder());
    	userTxtF.setBounds(138,83,227,26);
    	passwordPF.setBounds(138,131,227,26);
    	
    	loginBtn.setFocusable(false);
    	loginBtn.setForeground(Color.blue);
    	loginBtn.setBackground(Color.white);
    	
    	
    	
    	Border bd = BorderFactory.createLineBorder(Color.black, 2);
    	userTxtF.setBorder(bd);
    	passwordPF.setBorder(bd);
    	
    	JLabel lblNewLabel = new JLabel("Chưa có tài khoản?");
    	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	lblNewLabel.setBounds(113, 230, 123, 26);
    	getContentPane().add(lblNewLabel);
    	
    	JButton signUpButton = new JButton("Đăng ký");
    	signUpButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SignUpForm suf = new SignUpForm();
    			dispose();
    		}
    	});
    	signUpButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
    	signUpButton.setBounds(246, 234, 107, 20);
    	getContentPane().add(signUpButton);
    	
    	
    	
    	this.setVisible(true);
    }
}
