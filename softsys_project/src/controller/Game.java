package controller;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//TODO assure Client Threadsafety

public class Game implements Runnable {
	
	public int currentPlaying = 0;
	public Player[] players;
	public int gameID;
	public long timeout; 
	private BufferedReader[] receivers;
	public boolean running = false;
	private Parser parser;
	
	public Game(int id, int playerNum, int dimX, int dimY, int dimZ, int winLength) {
		players = new Player[playerNum];
		receivers = new BufferedReader[playerNum];
		parser = new Parser();
		this.gameID = id;
		timeout = System.currentTimeMillis();
	}
	
	//method that adds a player to this room
	//action for command joinRoom
	public void addPlayer(Player newPlayer) {
		players[currentPlaying] = newPlayer; 
		
		//set up communication with the new player
		try {
			
			receivers[currentPlaying] = 
				new BufferedReader(new InputStreamReader(
						players[currentPlaying].getSocket().getInputStream()
						));
		
		} catch (IOException io) {
			//TODO add exception handle
			System.out.println(io.getMessage());
		}
		currentPlaying = currentPlaying + 1;
		
	}
	
	//method that removes a player from the room
	//action for command leaveRoom
	public void removePlayer(Player gonePlayer) {
		
		// for loop browses through the list of players logged in this room 
		// (no need for those who added later 
		// because they can't remove themselves until after they've been added)
		for (int i = 0; i <= currentPlaying; i++) {
			
			//if the specified player is found remove it and set currentPlaying to one less
			if (players[i].equals(gonePlayer)) {
			
				players[i] = null;
				try {
					receivers[i].close();
					receivers[i] = null;
				} catch (IOException io) {
					//TODO add exception handle
					System.out.println(io.getMessage());
				}
				currentPlaying = currentPlaying - 1;
				
				break;
				
			} 
		}
	}
	
	//method to test if a player timed out their turn
	public Boolean timeViolation() {
		//returnable boolean
		boolean violation = false;
		
		//takes present time
		long now = System.currentTimeMillis();
		
		//checks if 2 minutes have passed since last player was given turn
		if ((now - timeout) > 120000) {
			violation  = true;
		} 
		
		return violation;
	}
	
	//method to shut down the game as a whole
	public Boolean shutDown() {
		boolean result = true;
		try {
			//close all open BufferedReaders
			//doesn't close the socket
			for (int i = 0; i < receivers.length; i++) {
				receivers[i].close();
			}
		} catch (IOException e) {
			//TODO properly handle exception
			result = false;
		}
		return result;
	}
	
	//run method to allow communication with the clients
	public void run() {
		running = true;
		while (running) {
			String input = null;
			
			//check all receivers continuously
			for (int i = 0; i < receivers.length; i++) {
				try {
					input = receivers[i].readLine();
				} catch (IOException io) {
					//TODO properly handle exception
					io.getMessage();
				}
				
				//process the input
				if (input != null) {
					parser.parse(input);
				}
				//discard if empty
				
			}
		}
	}
	
}
