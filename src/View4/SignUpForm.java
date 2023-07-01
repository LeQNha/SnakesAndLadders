package View4;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Main4.DBConnection;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class SignUpForm extends JFrame{
	private JTextField userNameTxtF;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	public SignUpForm() {
		this.setTitle("ĐĂNG KÝ");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(539,406);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ĐĂNG KÝ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(135, 20, 226, 43);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên tài khoản");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 70, 95, 32);
		getContentPane().add(lblNewLabel_1);
		
		userNameTxtF = new JTextField();
		userNameTxtF.setBounds(170, 73, 288, 32);
		getContentPane().add(userNameTxtF);
		userNameTxtF.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(170, 130, 288, 32);
		getContentPane().add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(170, 194, 288, 32);
		getContentPane().add(confirmPasswordField);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 128, 95, 32);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Xác nhận mật khẩu");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 192, 141, 32);
		getContentPane().add(lblNewLabel_3);
		
		JButton confirmButton = new JButton("Đăng ký");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userNameTxtF.getText().isBlank() || passwordField.getText().isBlank() || confirmPasswordField.getText().isBlank()) 
					JOptionPane.showConfirmDialog(null, "Không được để trống!",null, JOptionPane.ERROR_MESSAGE);
				else if(!passwordField.getText().equals(confirmPasswordField.getText()))
					JOptionPane.showConfirmDialog(null, "Xác nhận lại mật khẩu!",null, JOptionPane.ERROR_MESSAGE);
				else {
					String u = userNameTxtF.getText();
					String p = passwordField.getText();
					String sql;	
					ResultSet rs = null;
					Connection con = DBConnection.getConnection();
					try {
						Statement st = con.createStatement();
						sql = "SELECT *FROM account "+
							  "WHERE USERNAME='"+u+"' and PASSWORD ='"+p+"';";
						rs = st.executeQuery(sql);
						if(rs.next())
							JOptionPane.showConfirmDialog(null, "Tài khoản đã tồn tại!",null, JOptionPane.ERROR_MESSAGE);
						else {
							sql = "INSERT INTO account (USERNAME, PASSWORD) "+
								  "VALUES ('"+u+"','"+p+"');";
							int	r = st.executeUpdate(sql);
							JOptionPane.showMessageDialog(null, "Đăng ký thành công");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DBConnection.closeConnection(con);
				}
			}
		});
		confirmButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		confirmButton.setBounds(200, 248, 103, 43);
		getContentPane().add(confirmButton);
		
		JLabel lblNewLabel_4 = new JLabel("Đã có tài khoản?");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(150, 307, 109, 32);
		getContentPane().add(lblNewLabel_4);
		
		JButton loginButton = new JButton("Đăng nhập");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginForm lf = new LoginForm();
				dispose();
			}
		});
	
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		loginButton.setBounds(269, 311, 123, 25);
		getContentPane().add(loginButton);
		CreateSignUpForm();
		
		this.setVisible(true);
	}
	public void CreateSignUpForm() {
		
	}
}
