package controller;

import java.net.Socket;

import java.util.ArrayList;

import supportclasses.Receiver;
import supportclasses.Sender;

import java.io.IOException;

public class Player {
	
	private int playerID;
	private String playerName;
	private Socket playerSocket;
	private ServerGame currentGame;
	public Sender send;
	public Receiver receiver;
	
	//Capabilities of player
	private int maxPlayers;
	private int maxX;
	private int maxY;
	private int maxZ;
	private int maxWin;
	private boolean chat;
	private boolean refresh;
	
	//constructor of a player object that can contain information about a player
	public Player(int id, String name, Socket sock) {
		this.currentGame = null;
		this.playerID = id;
		this.playerName = name;
		this.playerSocket = sock;
		this.send = new Sender(playerSocket);
		this.receiver = new Receiver(playerSocket, this);
		Thread streamInputHandler = new Thread(this.receiver);
        streamInputHandler.start();
        
        System.out.println("player one: is active!");
		
		//set default capabilities
		maxPlayers = 2;
		maxX = 4;
		maxY = 4;
		maxZ = 4;
		maxWin = 4;
		chat = false;
		refresh = false;
	}
	
	public void setCapabilities(int amountOfPlayers, 
			String name, 
			int maxDimX,
			int maxDimY,
			int maxDimZ,
			int maxLength,
			boolean chatSup,
			boolean refreshOn) {
		
		this.playerName = name;
		this.maxPlayers = amountOfPlayers;
		this.maxX = maxDimX;
		this.maxY = maxDimY;
		this.maxZ = maxDimZ;
		this.maxWin = maxLength;
		this.chat = chatSup;
		this.refresh = refreshOn;
	}
		
	public void setGame(ServerGame game) {
		this.currentGame = game;
	}
	
	public Socket getSocket() {
		return playerSocket;
	}
	
	public ArrayList<Object> getCapabilities() {
		ArrayList<Object> capabilities = new ArrayList<Object>();
		capabilities.add(maxPlayers);
		capabilities.add(maxX);
		capabilities.add(maxY);
		capabilities.add(maxZ);
		capabilities.add(maxWin);
		capabilities.add(chat);
		capabilities.add(refresh);
		return capabilities;
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getID() {
		return playerID;
	}

	public ServerGame getGame() {
		return this.currentGame;
	}
	
	public void activate() {
		
	}
	
	public void shutDown() {
		// if player is in an active game shut it down
		if (getGame() != null) {
			getGame().shutDown(playerID, true);
		} 
		
		send.shutDown();
		receiver.shutDown();
		try {
			playerSocket.close();
		} catch (IOException io1) {
			// TODO Auto-generated catch block
			io1.getMessage();
		}
	}
}
