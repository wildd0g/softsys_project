package controller;

import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Server {
	
	List<Player> nonPlaying = new ArrayList<>();
	Map<Game, Player> games = new HashMap<Game, Player>();

	private static boolean active = true;
	
	//method to neatly shut down server
	//closes all games
	//disconnects all Clients
	public void serverShutDown() {
		
	}
	
	public static void main(String[] args) {
		//TODO handle System.exit(0);
		if (args.length != 2) {
			System.exit(0);
		}
		
		String name = args[0];
		int port = 0;
		ServerSocket servSock = null;
		
		// assign server port number
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Argument port:" + args[1] 
					+ "is not an integer");
			System.exit(0);
		}
		
		try {
			servSock = new ServerSocket(port);
		} catch (IOException io) {
			System.out.println("Couldn't create a socket on this port");
			System.out.println(io.getMessage());
		}
		
		while (active) {
			Socket sock = null;
			try {
				sock = servSock.accept();
			} catch (IOException io1) {
				//TODO handle exception properly
				io1.getMessage();
			}
			
			//method to set up communication before getting to game
			//create player, assign id, add to list
		}
		
	}

}
