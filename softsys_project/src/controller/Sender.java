package controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;



public class Sender {

	protected Socket playerSocket;
	protected OutputStreamWriter sender;
	
	public Sender(Socket sock) {
		playerSocket = sock;
		try {
			sender = new OutputStreamWriter(sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void turn(int playerID) {
		String msg = Protocol.Server.TURNOFPLAYER + " " + playerID + "/n";
		try {
			sender.write(msg);
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void error(int error) {
		String msg = Protocol.Server.ERROR + " " + error + "/n";
		try {
			sender.write(msg);
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
