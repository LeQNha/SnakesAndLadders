package Main4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class PlayerHandler implements Runnable{
	public static ArrayList<PlayerHandler> playerHandlers = new ArrayList<>();
	public static String inputName = null;
	public static boolean hasName = false;
	private String playerName;
	private PlayerHandler opponentHandler;
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;
	
	public PlayerHandler(Socket socket) {
		try {
			this.socket = socket;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.playerName = br.readLine();
			playerHandlers.add(this);
			
			for(PlayerHandler ph : playerHandlers) {
				if(ph.opponentHandler == null && ph != this) {
					ph.opponentHandler = this;
					this.opponentHandler = ph;
					this.opponentHandler.BroadcastMessage("join,"+this.opponentHandler.playerName);
					this.bw.write("second,turn");
					this.bw.newLine();
					this.bw.flush();
					this.opponentHandler.bw.write("first,turn");
					this.opponentHandler.bw.newLine();
					this.opponentHandler.bw.flush();
				}
			}
			
			BroadcastMessage("join,"+playerName);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CloseEverything(br, bw, socket);
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg;
		try {
		while(!socket.isClosed()) {
			
				msg = br.readLine();
				
				if(msg.equals("bye,bye")) {
					this.opponentHandler.opponentHandler = null;
					CloseEverything(br, bw, socket);
//					System.exit(0);
					System.out.println("gb");
				}	
					BroadcastMessage(msg);
					
			
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CloseEverything(br, bw, socket);
		}
	}
	public void BroadcastMessage(String msg) {
		try {
		for(PlayerHandler ph : playerHandlers) {
//			if(!ph.playerName.equals(playerName)) {
//				
//					ph.bw.write(msg);
//					ph.bw.newLine();
//					ph.bw.flush();
//			}
			if(this.opponentHandler != null) {
				opponentHandler.bw.write(msg);
				opponentHandler.bw.newLine();
				opponentHandler.bw.flush();
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			CloseEverything(br, bw, socket);
		}
	}
	public void CloseEverything(BufferedReader br, BufferedWriter bw, Socket socket) {
		playerHandlers.remove(this);
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

}
