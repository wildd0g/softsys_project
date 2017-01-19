package connect4.controller;

import java.net.Socket;

public class Player {
	
	private int playerID;
	private String playerName;
	private Socket playerSocket;
	
	//constructor of a player object that can contain information about a player
	public Player(int id, String name, Socket sock){
		this.playerID = id;
		this.playerName = name;
		this.playerSocket = sock;
	}
	
	
	public void setDefaultCapabilities(){
		setCapabilities();
	}
	
	public void setCapabilities(){
		
	}

}
