package Main4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1111);
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				PlayerHandler playerHandler = new PlayerHandler(socket);
				Thread thread = new Thread(playerHandler);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
