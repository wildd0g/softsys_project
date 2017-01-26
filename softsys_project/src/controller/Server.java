package controller;

import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Server {
	
	private static List<Player> nonPlaying = new ArrayList<>();
	private static Map<Game, Player> games = new HashMap<Game, Player>();

	private static boolean receiving = true;
	private static boolean active = true;
	private static Socket registrationSocket = null;
	private static ServerSocket serverSocket = null;
	private static int playerCounter = 0;
	
	//method to neatly shut down server
	//closes all games
	//disconnects all Clients
	public void serverShutDown() {
		receiving  = false;
	}
	
	public static void main(String[] args) {
		//TODO handle System.exit(0);
		if (args.length != 2) {
			System.exit(0);
		}
		
		//assign server name and initialise port
		String name = args[0];
		int port = 0;
		
		// assign server port number
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Argument port:" + args[1] 
					+ "is not an integer");
			System.exit(0);
		}
		
		//assign server socket at listening port
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException io) {
			System.out.println("Couldn't create a socket on this port");
			System.out.println(io.getMessage());
		}
		
		//While loop to accept new clients wanting to link to the server
		while (receiving) {
			
			try {
				
				//socket reference is loaded with new attached socket
				registrationSocket = serverSocket.accept();
				
				//player object is created with this as reference. 
				//It is added to the list of players not yet in a game and is activated
				Player newPlayer = 
						new Player(playerCounter, "player" + playerCounter, registrationSocket);
				nonPlaying.add(newPlayer);
				nonPlaying.get(nonPlaying.size() - 1).activate();
				
				//Testcode
				System.out.println(newPlayer.getID());
				System.out.println(newPlayer.getName());
				
			} catch (IOException io1) {
				//TODO handle exception properly
				io1.getMessage();
			}
		}
		
	}

}
