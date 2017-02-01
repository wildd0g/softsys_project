package supportclasses;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;

import controller.Parser;
import controller.Player;

public class Receiver implements Runnable {
	
	protected Player player = null;
	protected Socket socket;
	protected InputStreamReader receiver;
	protected BufferedReader buffer;
	protected boolean active;
	private Parser parser;
	
	public Receiver(Socket sock, Player plyr) {
		this(sock);
		player = plyr;
	}
	
	public Receiver(Socket sock) {
		socket = sock;
		
		try {
			receiver = new InputStreamReader(sock.getInputStream());
		} catch (IOException io1) {
			//TODO properly handle Exception
			io1.getMessage();
		}
		
		buffer = new BufferedReader(receiver);
		
		active = true;
		
	}

	public void shutDown() {
		active = false;
	}

	@Override
	public void run() {
		parser = new Parser(player);
		String line;
		while (active) {

			line = null;
			try {
				line = buffer.readLine();
			} catch (IOException io3) {
				//properly handle Exception
				io3.getMessage();
			}

			if (line != null) {
				if (player != null) {
					System.out.println(player.getName() + " " + line);
				} else {
					System.out.println(line);
				}
				parser.parse(line);
			}	
		}
		try {
			buffer.close();
		} catch (IOException io4) {
			//properly handle Exception
			io4.getMessage();
		}

	}
	
}