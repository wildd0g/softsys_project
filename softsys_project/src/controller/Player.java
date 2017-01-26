package controller;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Player {
	
	private int playerID;
	private String playerName;
	private Socket playerSocket;
	private Game currentGame;
<<<<<<< HEAD
	public Sender send;
=======
	private BufferedReader input;
	private BufferedWriter output;
	
>>>>>>> branch 'master' of https://github.com/wildd0g/softsys_project
	//constructor of a player object that can contain information about a player
	public Player(int id, String name, Socket sock) {
		this.playerID = id;
		this.playerName = name;
		this.playerSocket = sock;
<<<<<<< HEAD
		this.send = new Sender(sock);
=======
		
		try {
			this.input = new BufferedReader(
					new InputStreamReader(playerSocket.getInputStream()));
			this.output = new BufferedWriter(
					new OutputStreamWriter(playerSocket.getOutputStream()));
		} catch (IOException io1) {
			io1.getMessage();
		}
>>>>>>> branch 'master' of https://github.com/wildd0g/softsys_project
	}
	
	
	public void setDefaultCapabilities() {
		setCapabilities();
	}
	
	public void setCapabilities() {
		
	}
	
	public void setGame(Game game) {
		this.currentGame = game;
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
	
	public BufferedReader getIn() {
		return input;
	}
	
	public BufferedWriter getOut() {
		return output;
	}

	public Game getGame() {
		return this.currentGame;
	}
	
	public void activate() {
		
	}
}
