package controller;

import java.util.concurrent.ThreadLocalRandom;

import model.InvalidFieldException;
import model.Mark;

public class ServerGame extends Game {

	public int currentPlaying = 0;
	public int currentTurnIndex = 0;
	public int gameID;
	public long timeout;
	public Player[] players;
	public String[] playerInfo;
	public String[] hexcolours;
	private int[] roomData;


	public ServerGame(int id, int playerNum, int dimX, int dimY, int dimZ, int winLength) {
		super(playerNum, dimX, dimY, dimZ, winLength);
		players = new Player[playerNum];
		this.gameID = id;
		timeout = System.currentTimeMillis();
		roomData = new int[]{dimX, dimY, dimZ, winLength};
		hexcolours = new String[]{"ff0000", "0000ff", "ffd700", "00ffff",
								  "00ff00", "800080", "ffa500", "ffc0cb"}; 
	}

	//method to retrieve string of information on this game
	public String getGame() {
		String returnString = "";
		returnString = returnString 
				+ " " + gameID
				+ "|" + players.length 
				+ "|" + maxRoomDimensionX
				+ "|" + maxRoomDimensionY
				+ "|" + maxRoomDimensionZ
				+ "|" + lengthToWin;
		return returnString;
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
		
		String[][] playerArray = new String[players.length][3];
		
		for (int i = 0; i < players.length; i++) {
			String[] playerData = new String[3];
			playerIDs[i] = players[i].getID();
			
			playerData[0] = "" + playerIDs[i];
			playerData[1] = players[i].getName();
			playerData[2] = hexcolours[i];
			playerArray[i] = playerData;
		}
		for (int i = 0; i < players.length; i++) {
			players[i].send.startGame(roomData, playerArray);
		}
		
		startGame();
		//get a random starting value for current playing
		int randomNum = ThreadLocalRandom.current().nextInt(0, players.length);
		currentTurnIndex = randomNum;
		//start the game by triggering the next turn.
		nextTurn();
	}

	//server: notify the turn switches to the next player
	public void nextTurn() {
		currentTurnIndex = (currentTurnIndex + 1) % players.length;
		currentTurn = players[currentTurnIndex].getID();
		for (int i = 0; i < players.length; i++) {
			players[i].send.turn(currentTurn);
		}
	}

	//make a move based on x and y, tested for the max dimensions, and test if this end the game.
	//if it doesn't end, go to next turn.
	public void makeMove(int x, int y, int playerID) {
		Mark m = playerMarks.get(playerID);
		int z = calcMoveLvl(x, y);
		boolean isTurn = playerID == currentTurn;
		//checks against current room sizes if move is valid
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ && isTurn) {

			//tries to change board status
			try {

				board.setField(x, y, z, m);

				//notify players of made move
				for (int i = 0; i < players.length; i++) {
					players[i].send.notifyMove(playerID, x, y);
				}

			} catch (InvalidFieldException e) {
				//should never be thrown, but catch anyway
				players[currentTurnIndex].send.error(5);
			}

			//check for win condition
			if (checkEnd(playerID) == false) {
				nextTurn();	
			} else {
				shutDown(playerID, false);
			}

		} else if (!isTurn) {
			players[currentTurnIndex].send.error(4);
		} else {
			players[currentTurnIndex].send.error(5);
		}
	}

	//checks if the game is over, and sends the appropriate notifies
	protected boolean checkEnd(int playerID) {
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
	
	public void shutDown(int playerID, boolean disconnect) {
		if (disconnect) {
			for (int i = 0; i < players.length; i++) {
				if (players[i].getID() != playerID) {
					players[i].send.notifyEnd(3, playerID);
					controller.Server.getNonPlaying().add(players[i]);
				} 
			}
		} 
		
		controller.Server.removeGame(this);
		
	}

}
