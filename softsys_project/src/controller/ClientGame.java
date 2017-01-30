package controller;

import java.util.ArrayList;

import model.InvalidFieldException;
import model.Mark;
import view.Client;

public class ClientGame extends Game {

	private Client client;

	public ClientGame(int dimX, int dimY, int dimZ, int winLength, Client parent,
			ArrayList<String[]> playerList) {
		super(playerList.size(), dimX, dimY, dimZ, winLength);
		client = parent;
		for (int i = 0; i < playerList.size(); i++) {
			String[] tempPlayer = playerList.get(i);
			//TODO store player data
		}
	}

	// TODO move to client
	//client: test a input move and send to server
	public void suggestMove(int x, int y) {
		int z = calcMoveLvl(x, y);
		if (x < maxRoomDimensionX && y < maxRoomDimensionY && z < maxRoomDimensionZ) {
			client.makeMove(x, y);
		} else {
			//TODO invalid move, same response as recieving error 5
		}
	}

	// TODO move to client
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
			//TODO figure out what the hell these functions do...
			client.sender.error(5);
		}
	}

}
