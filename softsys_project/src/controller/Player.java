package controller;

import java.net.Socket;

import supportclasses.Receiver;
import supportclasses.Sender;

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
	public Sender send;
	public Receiver receiver;
	
	//constructor of a player object that can contain information about a player
	public Player(int id, String name, Socket sock) {
		this.playerID = id;
		this.playerName = name;
		this.playerSocket = sock;
		this.send = new Sender(playerSocket);
		this.receiver = new Receiver(playerSocket);
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

	public Game getGame() {
		return this.currentGame;
	}
	
	public void activate() {
		
	}
}
