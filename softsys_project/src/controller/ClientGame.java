package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.InvalidFieldException;
import model.Mark;

public class ClientGame extends Game {

	private Map<Integer, String> playerNames = new HashMap<Integer, String>();
	private Map<Integer, String> playerColours = new HashMap<Integer, String>();

	public ClientGame(int dimX, int dimY, int dimZ, int winLength,
			ArrayList<String[]> playerList) {
		super(playerList.size(), dimX, dimY, dimZ, winLength);
		for (int i = 0; i < playerList.size(); i++) {
			String[] tempPlayer = playerList.get(i);
			playerIDs[i] = Integer.parseInt(tempPlayer[0]);
			playerNames.put(playerIDs[i], tempPlayer[1]);
			playerColours.put(playerIDs[i], tempPlayer[2]);
		}
	}

	//client: test a input move and send to server
	public void suggestMove(int x, int y) {
		int z = calcMoveLvl(x, y);
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ) {
			Client.makeMove(x, y);
		} else {
			Client.tui.printWrite("invalid move, please try again");
			Client.client.isTurn();
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
			Client.sender.error(5);
		}
	}

}
