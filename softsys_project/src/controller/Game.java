package controller;

import model.Board;
import model.InvalidFieldException;
import model.Mark;
import view.Client;

import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Map;

//TODO assure Client Threadsafety

public class Game {
	
	public int currentPlaying = 0;
	public Player[] players;
	public int gameID;
	public long timeout; 
	public boolean running = false;
	private Board board = null;
	int maxRoomDimensionX = 4;
	int maxRoomDimensionY = 4;
	int maxRoomDimensionZ = 4;
	int lengthToWin = 4;
	private Map<Integer, Mark> playerMarks = new HashMap<Integer, Mark>();
	private Client client;
	
	public Game(int id, int playerNum, int dimX, int dimY, int dimZ, int winLength, Client parent) {
		this(id, playerNum, dimX, dimY, dimZ, winLength);
		client = parent;
	}
	
	public Game(int id, int playerNum, int dimX, int dimY, int dimZ, int winLength) {
		players = new Player[playerNum];
		this.gameID = id;
		timeout = System.currentTimeMillis();
		board = new Board(dimX, dimY, dimZ, winLength, playerNum);
		maxRoomDimensionX = dimX;
		maxRoomDimensionY = dimY;
		maxRoomDimensionZ = dimZ;
		lengthToWin = winLength;
	}

	//method that adds a player to this room
	//action for command joinRoom
	public void addPlayer(Player newPlayer) {
		players[currentPlaying] = newPlayer; 
		currentPlaying = currentPlaying + 1;
	}

	//method that removes a player from the room
	//action for command leaveRoom
	public void removePlayer(Player gonePlayer) {
		if (!running) {
			// for loop browses through the list of players logged in this room 
			// (no need for those who added later 
			// because they can't remove themselves until after they've been added)
			for (int i = 0; i <= currentPlaying; i++) {

				//if the specified player is found remove it and set currentPlaying to one less
				if (players[i].equals(gonePlayer)) {	
					players[i] = null;
					currentPlaying = currentPlaying - 1;

					break;

				} 
			}
			// fix the array to plug a hole.
			Player[] tempPlayers = new Player[players.length];
			int j = 0;
			for (int i = 0; i <= currentPlaying; i++) {
				if (!players[i].equals(null)) {
					tempPlayers[j] = players[i];
				}
				j++;
			}
			players = tempPlayers;
		} else {
			gonePlayer.send.error(6);
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

	//server's method to start the game
	public void serverStartGame() {
		startGame();
		//get a random starting value for current playing
		int randomNum = ThreadLocalRandom.current().nextInt(0, players.length);
		currentPlaying = randomNum;
		//start the game by triggering the next turn.
		nextTurn();
	}
	
	//method to start the game once the room is full
	public void startGame() {
		running = true;
		board.reset();
		for (int i = 0; i < players.length; i++) {
			//assign marks to players, +1 because empty is at 0
			playerMarks.put(players[i].getID(), Mark.values()[i + 1]);
		} 
	}
	
	//server: notify the turn switches to the next player
	public void nextTurn() {
		currentPlaying = (currentPlaying + 1) % players.length;
		for (int i = 0; i < players.length; i++) {
			players[i].send.turn(players[currentPlaying].getID());
		}
	}
	
	//make a move based on x and y, tested for the max dimensions, and test if this end the game.
	//if it doesn't end, go to nex turn.
	public void makeMove(int x, int y, int playerID) {
		Mark m = playerMarks.get(playerID);
		int z = calcMoveLvl(x, y);
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ) {
			try {
				board.setField(x, y, z, m);
				for (int i = 0; i < players.length; i++) {
					players[i].send.notifyMove(playerID, x, y);
				}
			} catch (InvalidFieldException e) {
				players[currentPlaying].send.error(5);
			}
			if (checkEnd(playerID) == false) {
				nextTurn();	
			} else {
				shutDown();
			}
			
		} else {
			players[currentPlaying].send.error(5);
		}
	}
	
	//client: test a input move and send to server
	public void suggestMove(int x, int y) {
		int z = calcMoveLvl(x, y);
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ) {
			client.send.makeMove(x, y);
		} else {
			//TODO invalid move, same response as recieving error 5
		}
	}
	
	//apply the move received from the server.
	public void notefiedMove(int x, int y, int playerID) {
		Mark m = playerMarks.get(playerID);
		int z = calcMoveLvl(x, y);
		//valid move check
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ) {
			try {
				board.setField(x, y, z, m);
			} catch (InvalidFieldException e) {
				System.out.println(e.getMessage());
				System.out.println("How in FUCK did it get here past the check if it isn;t valid?");
				e.printStackTrace();
			}
		} else {
			System.out.println("error due to notifyMove recieved being invalid");
			client.send.error(5);
		}
	}
	
	//calculate on what Level (z) a move on x and y goes, does not care about max dimension z
	private int calcMoveLvl(int x, int y) {
		int testZ = 0;
		boolean empty = false;
		while (board.isField(x, y, testZ) && !empty) {
			try {
				empty = board.isEmptyField(x, y, testZ);
			} catch (InvalidFieldException e) {
				empty = true;
			}
			if (!empty) {
				testZ++;
			}
		} //returned z if z is not a field or is empty
		return testZ;
	}
	
	//checks if the game is over, and sends the appropriate notefies
	private boolean checkEnd(int playerID) {
		Mark m = playerMarks.get(playerID);
		boolean result = board.isWinner(m);
		if (result) {
			for (int i = 0; i < players.length; i++) {
				players[i].send.notifyEnd(1, playerID);
			}
		} else if (board.isFull()) {
			result = true;
			for (int i = 0; i < players.length; i++) {
				players[i].send.notifyEnd(2, 0);
			}
		}
		return result;
	}
	
	
	//method to shut down the game as a whole
	public Boolean shutDown() {
		boolean result = true;
		
		return result;
	}
	
}
