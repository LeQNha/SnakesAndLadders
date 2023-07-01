package Main4;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import View4.InputNameView;

import javax.swing.JTextField;

public class Player extends JFrame implements ActionListener{
	int test=0;
	private String playerName;
	private String opponentName;
	private BufferedReader br;
	private BufferedWriter bw;
	private Socket socket;
	private boolean isYourTurn = true;
	private boolean isReady = false;
	private boolean enabledButton = false;
	
	JLabel backgroundLbl = new JLabel();
	
	ImageIcon playerIcon;
	ImageIcon opponentIcon;
	ImageIcon turnIcon;
	DoubleIcon doubleIcon;
	
	ImageIcon backgroundImage;
	ImageIcon diceIcon_1;
	ImageIcon diceIcon_2;
	ImageIcon diceIcon_3;
	ImageIcon diceIcon_4;
	ImageIcon diceIcon_5;
	ImageIcon diceIcon_6;
	ImageIcon[] diceIcons = new ImageIcon[6];
	int count = 0;
	
	JPanel squarePanel = new JPanel();
	JLabel[][] labels = new JLabel[5][6];
	JLabel[] o = new JLabel[30];
	JLabel mapLbl = new JLabel();
	JButton diceBtn = new JButton();
	
	JLabel playerNameLbl = new JLabel("New label");
	JLabel opponentNameLbl = new JLabel("Đang chờ...");
	JLabel notifyLbl = new JLabel();
	
	JLabel chatBoxLbl = new JLabel();
	JTextArea chatBoxTxtArea = new JTextArea();
	JLabel chatIconLbl = new JLabel();
	
	int dice;
	Random rand = new Random();
	Timer timer;
	Timer timer2;
	Timer timer3;
	
	int currentPosition = -1;
	int newPosition = -1;
	int opponentCurrentPosition = -1;
	int opponentNewPosition = -1;
	JTextField chatTxtF = new JTextField();
	JLabel sendLbl = new JLabel();
	
	public Player(Socket socket, String playerName) {
		this.socket = socket;
		this.playerName = playerName;
		
		try {
			
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(playerName);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CloseEverything(br, bw, socket);
		}			
		PlayerView();
	}
	public void PlayerView() {
		this.setSize(655,775);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		URL chatBoxURL = Player.class.getResource("Snk LĐ/chat.png");
		ImageIcon chatBoxIcon = new ImageIcon(chatBoxURL);
		
		backgroundLbl.add(chatBoxTxtArea);
		chatBoxTxtArea.setBounds(434,570, 128, 100);
		chatBoxTxtArea.setVisible(true);
		chatBoxTxtArea.setEditable(false);
		chatBoxTxtArea.setLineWrap(true);
		chatBoxTxtArea.setForeground(Color.WHITE);
		chatBoxTxtArea.setFont(new Font("Tahoma", Font.BOLD, 15));
		chatBoxTxtArea.setBackground(new Color(59, 212, 74));
		chatBoxTxtArea.setVisible(false);
		
		backgroundLbl.add(chatBoxLbl);
		chatBoxLbl.setBounds(434, 593, 128, 100);
		chatBoxLbl.setVisible(true);
		chatBoxLbl.setHorizontalAlignment(JLabel.CENTER);
		chatBoxLbl.setIcon(chatBoxIcon);
		chatBoxLbl.setVisible(false);
		
		getContentPane().add(backgroundLbl);
		backgroundLbl.setBounds(0,10,641,730);
		backgroundLbl.setLayout(null);
		
		mapLbl.setBounds(10, 10, 620, 600);
		backgroundLbl.add(mapLbl);
		URL mapImageURL = Player.class.getResource("Snk LĐ/map.jpg");
	    ImageIcon mapImage = new ImageIcon(mapImageURL);
	    Image image = mapImage.getImage(); // Lấy đối tượng hình ảnh từ mapImage
	    Image newImage = image.getScaledInstance(620, 600, java.awt.Image.SCALE_SMOOTH); // Tạo mới hình ảnh với kích thước 50x50
	    mapImage = new ImageIcon(newImage); // Tạo mới Icon từ hình ảnh mới.
	    mapLbl.setIcon(mapImage);
	    mapLbl.setLayout(null);
		mapLbl.add(squarePanel);
		
		URL backgroundImageURL = Player.class.getResource("Snk LĐ/background.png");
	    backgroundImage = new ImageIcon(backgroundImageURL);
	    Image image2 = backgroundImage.getImage(); // Lấy đối tượng hình ảnh từ backgroundImage
	    Image newImage2 = image2.getScaledInstance(635, 730, java.awt.Image.SCALE_SMOOTH); // Tạo mới hình ảnh với kích thước 50x50
	    backgroundImage = new ImageIcon(newImage2); // Tạo mới Icon từ hình ảnh mới.
	    backgroundLbl.setIcon(backgroundImage);
		
		squarePanel.setBounds(0, 0, 620, 600);
		squarePanel.setLayout(new GridLayout(5, 6));
		squarePanel.setOpaque(false); // Tắt tính năng chắn sáng của panel
		Color transparentColor = new Color(255, 255, 255, 100); // Alpha channel của màu là 100
		squarePanel.setBackground(transparentColor);
		
		diceBtn.setOpaque(false);
		diceBtn.setBackground(transparentColor);
		diceBtn.setBorder(BorderFactory.createEmptyBorder());
		
		
		playerNameLbl.setBounds(45, 679, 158, 29);
		backgroundLbl.add(playerNameLbl);
		playerNameLbl.setText(playerName);
		playerNameLbl.setHorizontalAlignment(JLabel.CENTER);
		playerNameLbl.setForeground(Color.CYAN);
		playerNameLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		diceBtn.setBounds(247, 663, 119, 61);
		backgroundLbl.add(diceBtn);
		diceBtn.setFocusable(false);
		
		opponentNameLbl.setBounds(423, 679, 158, 29);
		backgroundLbl.add(opponentNameLbl);
		opponentNameLbl.setHorizontalAlignment(JLabel.CENTER);
		opponentNameLbl.setForeground(Color.CYAN);
		opponentNameLbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		for(int i = 0; i<5; i++) {
			for(int j = 0; j<6; j++) {
				JLabel lbl = new JLabel(i+" "+j);
				labels[i][j] = lbl;
				squarePanel.add(lbl);
			}
		}
		//add cac labels vao o[30];
		int os = 0;
		for(int i = 4; i>=0; i--) {
			if(i%2 == 0) {
				for(int j = 0; j < 6; j++) {
					o[os] = labels[i][j];
					o[os].setText(++os+"");		
				}
			}else {
				for(int j = 5; j >= 0; j--) {
					o[os] = labels[i][j];
					o[os].setText(++os+"");
				}
			}
		}
		this.setVisible(true);
		
//		diceBtn.setEnabled(false);
		
		
		notifyLbl.setBounds(229, 632, 158, 29);
		backgroundLbl.add(notifyLbl);
		notifyLbl.setForeground(Color.yellow);
		notifyLbl.setFont(new Font("Serif", Font.BOLD, 25));
		notifyLbl.setHorizontalAlignment(JLabel.CENTER);
		
		diceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(enabledButton == true) {
					dice = rand.nextInt(6) + 1;
					System.out.println(dice);				
					if(isYourTurn) {
							newPosition = currentPosition + dice;
							if(newPosition > 29)
								newPosition = 29;
							
							String str = "roll,"+dice;
	//						notifyLbl.setText(dice+"");
						try {
							bw.write("roll,"+dice);
							bw.newLine();
							bw.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
//						diceBtn.setEnabled(false);
						enabledButton = false;
						timer2.start();
	//					timer.start();
					}
				}
			}
		});
		
		
		URL yellowCarImageURL = Player.class.getResource("Snk LĐ/yellowCar.png");
	    playerIcon = new ImageIcon(yellowCarImageURL);
	    
	    URL redCarImageURL = Player.class.getResource("Snk LĐ/redCar.png");
	    opponentIcon = new ImageIcon(redCarImageURL);
	    
	    doubleIcon = new DoubleIcon(playerIcon, opponentIcon);
	    
	    URL dice1IconURL = Player.class.getResource("Snk LĐ/dice_1.png");
	    diceIcon_1 = new ImageIcon(dice1IconURL);
	    diceIcons[0] =(diceIcon_1);
	    diceBtn.setIcon(diceIcon_1);
	    diceBtn.setVerticalAlignment(JButton.CENTER);
		diceBtn.setHorizontalAlignment(JButton.CENTER);
		
		chatTxtF.setBounds(10, 647, 197, 27);
		backgroundLbl.add(chatTxtF);
		chatTxtF.setColumns(10);
		chatTxtF.setVisible(false);
		
		
		
		chatIconLbl.setBounds(199, 685, 45, 20);
		backgroundLbl.add(chatIconLbl);
		
		URL chatIconURL = Player.class.getResource("Snk LĐ/chatIcon.png");
		ImageIcon chatIcon = new ImageIcon(chatIconURL);
		chatIconLbl.setIcon(chatIcon);
		
		URL sendIconURL = Player.class.getResource("Snk LĐ/sendIcon.png");
		ImageIcon sendIcon = new ImageIcon(sendIconURL);
		backgroundLbl.add(sendLbl);
		sendLbl.setBounds(210, 647, 45, 29);
		sendLbl.setIcon(sendIcon);
		sendLbl.setVisible(false);
		sendLbl.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(sendLbl.isVisible()) {
					chatTxtF.setVisible(false);
					sendLbl.setVisible(false);
					try {
						if(!chatTxtF.getText().isBlank()) {
							bw.write(" "+chatTxtF.getText());
							bw.newLine();
							bw.flush();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					chatTxtF.setText("");
				}
				
			}
		});
		
		chatIconLbl.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!chatTxtF.isVisible()) {
					chatTxtF.setVisible(true);
					sendLbl.setVisible(true);
				}
				else {
					chatTxtF.setVisible(false);
					sendLbl.setVisible(false);
				}
			}
		});
		
		
		
	    URL dice2IconURL = Player.class.getResource("Snk LĐ/dice_2.png");
	    diceIcon_2 = new ImageIcon(dice2IconURL);
	    diceIcons[1] = (diceIcon_2);
	    URL dice3IconURL = Player.class.getResource("Snk LĐ/dice_3.png");
	    diceIcon_3 = new ImageIcon(dice3IconURL);
	    diceIcons[2] = (diceIcon_3);
	    URL dice4IconURL = Player.class.getResource("Snk LĐ/dice_4.png");
	    diceIcon_4 = new ImageIcon(dice4IconURL);
	    diceIcons[3] = (diceIcon_4);
	    URL dice5IconURL = Player.class.getResource("Snk LĐ/dice_5.png");
	    diceIcon_5 = new ImageIcon(dice5IconURL);
	    diceIcons[4] = (diceIcon_5);
	    URL dice6IconURL = Player.class.getResource("Snk LĐ/dice_6.png");
	    diceIcon_6 = new ImageIcon(dice6IconURL);
	    diceIcons[5] = (diceIcon_6);
	    
	    
		timer = new Timer(500, this);
		timer2 = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				count++;
				switch(count) {
					case 1: diceBtn.setIcon(diceIcon_2); break;
					case 2: diceBtn.setIcon(diceIcon_5); break;
					case 3: diceBtn.setIcon(diceIcon_1); break;
					case 4: diceBtn.setIcon(diceIcon_3); break;
					case 5: diceBtn.setIcon(diceIcon_4); break;
					case 6: diceBtn.setIcon(diceIcon_6); break;
					case 7:
						diceBtn.setIcon(diceIcons[dice-1]);
						count = 1;
						notifyLbl.setText(dice+"");
						timer2.stop();
						timer.start();
						break;
				}
				
			}
		});
		timer3 = new Timer(4000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				if(!chatBoxTxtArea.isVisible()) {
//					chatBoxTxtArea.setVisible(true);
//					chatBoxLbl.setVisible(true);
//					
//				}else {
//					chatBoxTxtArea.setVisible(false);
//					chatBoxLbl.setVisible(false);
//					timer3.stop();
//					
//				}
				chatBoxTxtArea.setVisible(false);
				chatBoxLbl.setVisible(false);
				timer3.stop();
				
			}
		});
	}
	public void setAlignment(int x) {
		o[x].setVerticalAlignment(JLabel.CENTER);
		o[x].setHorizontalAlignment(JLabel.CENTER);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(isYourTurn) {
					o[++currentPosition].setIcon(playerIcon);
					setAlignment(currentPosition);
					if(currentPosition != 0) {
						o[currentPosition - 1].setIcon(null);
						StandSamePosition();
					}
					if(currentPosition == newPosition) {
						StandSamePosition();
						jump();
					 	notifyLbl.setText("Lượt đối thủ");
					 	isYourTurn = false;
					 	enabledButton = false;
					 	checkWin();
					 	timer.stop();
					}
				
			
		 }else {
			 
				 o[++opponentCurrentPosition].setIcon(opponentIcon);
				 setAlignment(opponentCurrentPosition);
				 if(opponentCurrentPosition != 0) {
					o[opponentCurrentPosition - 1].setIcon(null);
					StandSamePosition();
				 }
				 if(opponentCurrentPosition == opponentNewPosition) { 
					 StandSamePosition();
					 jump2();
					notifyLbl.setText("Lượt của bạn");
//					diceBtn.setEnabled(true);
					isYourTurn = true; 
					enabledButton = true;
					checkWin();
					timer.stop();
				}
			 }
			 	
		 
	}
	public void StandSamePosition() {
		if(isYourTurn) {
			if(currentPosition == opponentCurrentPosition) {
				o[currentPosition].setIcon(doubleIcon);
				setAlignment(currentPosition);
			}else if(currentPosition == (opponentCurrentPosition + 1) && (opponentCurrentPosition != -1)) {
				o[opponentCurrentPosition].setIcon(opponentIcon);
				setAlignment(opponentCurrentPosition);
			}
				
		}else {
			if(opponentCurrentPosition == currentPosition) {
				o[opponentCurrentPosition].setIcon(doubleIcon);
				setAlignment(opponentCurrentPosition);
			}else if(opponentCurrentPosition == (currentPosition + 1) && (currentPosition != -1)) {
				o[currentPosition].setIcon(playerIcon);
				setAlignment(currentPosition);
			}
		}
	}
	 public void jump() {
		
			switch(newPosition) {
				case 2:
					newPosition = 15; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 4:
					newPosition = 7; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 10:
					newPosition = 25; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 19:
					newPosition = 28; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 16:
					newPosition = 3; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 18:
					newPosition = 6;
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;	
				case 20:
					newPosition = 8;
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 24:
					newPosition = 12; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
				case 26:
					newPosition = 0; 
					o[currentPosition].setIcon(null);
					o[newPosition].setIcon(playerIcon);
					setAlignment(newPosition);
					currentPosition = newPosition;
					StandSamePosition();
					checkWin();
					break;
					
			}
		}
	 public void checkWin() {
			int c=-10;
			if(currentPosition >= 29) {
				isYourTurn = true;
//				diceBtn.setEnabled(false);
				enabledButton = false;
				opponentNameLbl.setText("Đang chờ...");
				notifyLbl.setText("");
//				WinView wv = new WinView();
//				wv.setLocationRelativeTo(squarePanel);
				c =	JOptionPane.showConfirmDialog(squarePanel,"BẠN THẮNG! Bạn có muốn tiếp tục?",null, JOptionPane.YES_NO_OPTION);
					if(c == JOptionPane.YES_OPTION) {
						o[currentPosition].setIcon(null);
						o[opponentCurrentPosition].setIcon(null);
						currentPosition = -1;
						newPosition = -1;
						opponentCurrentPosition = -1;
						opponentNewPosition = -1;
						try {
							bw.write("join,"+playerName);
							bw.newLine();
							bw.flush();
							bw.write("first,turn");
							bw.newLine();
							bw.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(c == JOptionPane.NO_OPTION) {
						try {
							bw.write("bye,bye");
							bw.newLine();
							bw.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						CloseEverything(br, bw, socket);
						dispose();
						System.exit(0);		
					}
			}else if(opponentCurrentPosition >= 29) {
//				diceBtn.setEnabled(false);
				enabledButton = false;
				opponentNameLbl.setText("Đang chờ...");
				notifyLbl.setText("");
				isYourTurn = true;
				c =	JOptionPane.showConfirmDialog(squarePanel,"BẠN THUA! Bạn có muốn tiếp tục?",null, JOptionPane.YES_NO_OPTION);
					if(c == 0) {
						o[currentPosition].setIcon(null);
						o[opponentCurrentPosition].setIcon(null);
						currentPosition = -1;
						newPosition = -1;
						opponentCurrentPosition = -1;
						opponentNewPosition = -1;
						opponentCurrentPosition = -1;
						opponentNewPosition = -1;
						isYourTurn = true;
						try {
							bw.write("join,"+playerName);
							bw.newLine();
							bw.flush();
							bw.write("second,turn");
							bw.newLine();
							bw.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(!opponentNameLbl.getText().equals("Đang chờ...")) {
//							diceBtn.setEnabled(true);
							enabledButton = true;
							isYourTurn = true;
						}
					}
					if(c == JOptionPane.NO_OPTION) {
						try {
							bw.write("bye,bye");
							bw.newLine();
							bw.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						CloseEverything(br, bw, socket);
						dispose();
						System.exit(0);		
					}
			}
		}
	 public void jump2() {
			switch(opponentNewPosition) {
				case 2:
					opponentNewPosition = 15; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 4:
					opponentNewPosition = 7; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 10:
					opponentNewPosition = 25; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 19:
					opponentNewPosition = 28; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 16:
					opponentNewPosition = 3; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 18:
					opponentNewPosition = 6; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;	
				case 20:
					opponentNewPosition = 8; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 24:
					opponentNewPosition = 12; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
				case 26:
					opponentNewPosition = 0; 
					o[opponentCurrentPosition].setIcon(null);
					o[opponentNewPosition].setIcon(opponentIcon);
					opponentCurrentPosition = opponentNewPosition;
					StandSamePosition();
					checkWin();
					break;
					
			}
		}
		
		
//	public void SendMsg() {
//		Scanner sc = new Scanner(System.in);
//		String msg;
//		try {
//			while(true) {
//				msg = sc.nextLine();
//				bw.write(playerName+": "+msg);
//				bw.newLine();
//				bw.flush();
//			
//				if(msg.trim().equalsIgnoreCase("bye")) {
//					break;
//			}
//			
//		}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			CloseEverything(br, bw, socket);
//		}
//	}
	public void ListenToMsg() {
		new Thread(new Runnable() {
			String msg;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(socket.isConnected()) {
				try {
					
					msg = br.readLine();
					System.out.println(msg);
					String[] data = msg.split(",");
					if(data[0].equals("roll")) {
						opponentNewPosition = opponentCurrentPosition + Integer.parseInt(data[1]);
						if(opponentNewPosition >29 )
							opponentNewPosition = 29;
						dice = Integer.parseInt(data[1]);
//						notifyLbl.setText(data[1]+"");
//						timer.start();
						timer2.start();
					}else if(data[0].equals("join")) {
						opponentName = data[1];
						opponentNameLbl.setText(opponentName);
					}else if(data[0].equals("second")) {
						isYourTurn = false;
						notifyLbl.setText("Lượt đối thủ");
						enabledButton = false;
//						diceBtn.setEnabled(false);
					}else if(data[0].equals("first") && isYourTurn == true) {
						isYourTurn = true;
						notifyLbl.setText("Lượt của bạn");
						enabledButton = true;
						diceBtn.setEnabled(true);
					}else if(data[0].equals("bye")) {
//						bw.write("opponentLeave,leave");
//						bw.newLine();
//						bw.flush();
						opponentName = "";
						opponentNameLbl.setText("Đang chờ...");
					}else {
						if(!msg.trim().isBlank()) {
							chatBoxTxtArea.setText(msg);
							chatBoxTxtArea.setVisible(true);
							chatBoxLbl.setVisible(true);
							timer3.start();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					CloseEverything(br, bw, socket);
				}
				}
			}
		}).start();
	}
		public void CloseEverything(BufferedReader br, BufferedWriter bw, Socket socket) {
			try {
			if(br != null)	
				br.close();
			if(bw != null)
				bw.close();
			if(socket != null)
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter name: ");
//		String name = null;
//		InputNameView inv = new InputNameView();
//		while(true) {
//			if(PlayerHandler.hasName)
//				break;
//			System.out.println("");
//		}
//		name = PlayerHandler.inputName;
//		PlayerHandler.hasName = false;
//		try {
//			Socket soc = new Socket("localhost", 1111);
//			Player player = new Player(soc, name);
//			player.ListenToMsg();
//			inv.dispose();
////			player.SendMsg();
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
