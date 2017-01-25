package controller;

import java.net.Socket;

public class Player {
	
	private int playerID;
	private String playerName;
	private Socket playerSocket;
	private Game playerGame;
	//constructor of a player object that can contain information about a player
	public Player(int id, String name, Socket sock) {
		this.playerID = id;
		this.playerName = name;
		this.playerSocket = sock;
	}
	
	
	public void setDefaultCapabilities() {
		setCapabilities();
	}
	
	public void setCapabilities() {
		
	}
	
	public void setGame(Game game) {
		this.playerGame = game;
	}
	
	public Socket getSocket() {
		return playerSocket;
	}
	
	public String getName() {
		return playerName;
	}
	
	public int getID() {
		return playerID;
	}

	public Game getGame() {
		return this.playerGame;
	}
}
