package controller;

import model.Board;
import model.InvalidFieldException;
import model.Mark;

import java.util.HashMap;
import java.util.Map;

//TODO assure Client Threadsafety

public class Game {
	
	
	protected int[] playerIDs; 
	public boolean running = false;
	protected Board board = null;
	public int maxRoomDimensionX = 4;
	public int maxRoomDimensionY = 4;
	public int maxRoomDimensionZ = 4;
	public int lengthToWin = 4;
	int currentTurn;
	public boolean defaultGame; 
	protected Map<Integer, Mark> playerMarks = new HashMap<Integer, Mark>();
	
	
	public Game(int playerNum, int dimX, int dimY, int dimZ, int winLength) {
		playerIDs = new int[playerNum];
		board = new Board(dimX, dimY, dimZ, winLength, playerNum);
		maxRoomDimensionX = dimX;
		maxRoomDimensionY = dimY;
		maxRoomDimensionZ = dimZ;
		lengthToWin = winLength;
		
		if (playerNum == 2 
				&& maxRoomDimensionX == 4 
				&& maxRoomDimensionY == 4 
				&& maxRoomDimensionY == 4
				&& lengthToWin == 4) {
			defaultGame = true;
		} else {
			defaultGame = false;
		}
	}
	
	//method to start the game once the room is full
	public void startGame() {
		running = true;
		board.reset();
		for (int i = 0; i < playerIDs.length; i++) {
			//assign marks to players, +1 because empty is at 0
			playerMarks.put(playerIDs[i], Mark.values()[i + 1]);
		} 
	}
	
	//calculate on what Level (z) a move on x and y goes, does not care about max dimension z
	protected int calcMoveLvl(int x, int y) {
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
	
	public Board getBoard() {
		return board.deepCopy();
	}
	
	public Mark getMark(int playerID) {
		return playerMarks.get(playerID);	
	}
	
}
