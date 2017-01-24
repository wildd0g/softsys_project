package controller;

import java.util.Scanner;
import java.util.ArrayList;

public class Parser {

	Player player = null;
	int amountOfPlayers = 0;
	boolean roomSupport = false;
	int maxRoomDimensionX = 0;
	int maxRoomDimensionY = 0;
	int maxRoomDimensionZ = 0;
	int maxLengthToWin = 0;
	boolean chatSupport = false;
	String playerName = null;
	boolean autoRefresh = false;
	int roomID = 0;
	int playerID = 0;
	int turn = 0;
	int moveX = 0;
	int moveY = 0;
	
	public Parser() {

	}

	//@ require msg == valid protocol line
	//@ ensures msg is processed
	//TODO add check for valid time to process the sent data
	public void parse(Player plyr, String msg) {
		player = plyr;
		Scanner lineScanner = new Scanner(msg);
		if (lineScanner.hasNext()) {
			String word = lineScanner.next();

			switch (word) {

			case "serverCapabilities":
				//parse string and store the variables
				amountOfPlayers = Integer.parseInt(lineScanner.next());
				if (lineScanner.next().equals("1")) {
					roomSupport = true;
				}
				maxRoomDimensionX = Integer.parseInt(lineScanner.next());
				maxRoomDimensionY = Integer.parseInt(lineScanner.next());
				maxRoomDimensionZ = Integer.parseInt(lineScanner.next());
				maxLengthToWin = Integer.parseInt(lineScanner.next());
				if (lineScanner.next().equals("1")) {
					chatSupport = true;
				}
				endOverflowCatcher(msg, lineScanner);
				
				//TODO process client based on these capabilities
				
				break;

			case "sendCapabilities":
				//parse string and store the variables
				amountOfPlayers = Integer.parseInt(lineScanner.next());
				playerName = lineScanner.next();
				if (lineScanner.next().equals("1")) {
					roomSupport = true;
				}
				maxRoomDimensionX = Integer.parseInt(lineScanner.next());
				maxRoomDimensionY = Integer.parseInt(lineScanner.next());
				maxRoomDimensionZ = Integer.parseInt(lineScanner.next());
				maxLengthToWin = Integer.parseInt(lineScanner.next());
				if (lineScanner.next().equals("1")) {
					chatSupport = true;
				}
				if (lineScanner.next().equals("1")) {
					autoRefresh = true;
				}
				endOverflowCatcher(msg, lineScanner);
				
				//TODO process this to capabilities
				
				break;

			case "sendListRooms":
				//parse string and store the variables
				String room = null;
				ArrayList<int[]> lobbyData = new ArrayList<int[]>();
				while (lineScanner.hasNext()) {
					room = lineScanner.next();
					int[] roomData = new int[6];
					Scanner roomScanner = new Scanner(room);
					roomScanner.useDelimiter("|");
					try {
						for (int i = 0; i < 6; i++) {
							roomData[i] = Integer.parseInt(roomScanner.next());
						}
						endOverflowCatcher(msg, roomScanner);
					} catch (NumberFormatException e) {
						//TODO send error 7
					}
					lobbyData.add(roomData);
				}
				
				//TODO process this to rooms
				
				break;
				
				
			case "getRoomList":
				//TODO let server send rooms
				break;
			
			case "joinRoom":
				//parse string and store the variables
				roomID = Integer.parseInt(lineScanner.next());
				
				endOverflowCatcher(msg, lineScanner);
				
				//TODO let server assign socket to specefied room
				
				break;	
				
			case "error":
				System.out.println("recieved error " + lineScanner.next());
				
				endOverflowCatcher(msg, lineScanner);
				
				break;
				
			case "assignID":
				playerID = Integer.parseInt(lineScanner.next());
				
				endOverflowCatcher(msg, lineScanner);
				
				//TODO create player with ID, Name stc.
				
				
				break;
				
			case "leaveRoom":
				
				endOverflowCatcher(msg, lineScanner);
				
				//TODO cmd player leave room
				
				
				break;
				
			case "startGame":
				
				Scanner roomScanner = new Scanner(lineScanner.next());
				roomScanner.useDelimiter("|");
				maxRoomDimensionX = Integer.parseInt(roomScanner.next());
				maxRoomDimensionY = Integer.parseInt(roomScanner.next());
				maxRoomDimensionZ = Integer.parseInt(roomScanner.next());
				maxLengthToWin = Integer.parseInt(roomScanner.next());
				endOverflowCatcher(msg, roomScanner);
				
				ArrayList<String[]> playerList = new ArrayList<String[]>();
				while (lineScanner.hasNext()) {
					playerList.add(parsePlayerInfo(lineScanner.next()));
				}
				
				//TODO create game with these players
				
				break;
				
			case "makeMove":
				
				moveX = Integer.parseInt(lineScanner.next());
				moveY = Integer.parseInt(lineScanner.next());
				
				endOverflowCatcher(msg, lineScanner);
				
				//TODO make this move on local board
				//TODO server: apply move to local board and send along if valid
				
				break;
				
			case "playerTurn":
				
				turn = Integer.parseInt(lineScanner.next());
				
				endOverflowCatcher(msg, lineScanner);
				
				//TODO if turn 
				
				break;
				
			default:
				try {
					throw new InvalidCommandException(player);
				} catch (InvalidCommandException e) {
				}
				
			}

		} else {
			lineScanner.close();
			try {
				throw new InvalidCommandException(player);
			} catch (InvalidCommandException e) {
			}
			
		}
	}

	private void endOverflowCatcher(String msg, Scanner scanner) {
		try {
			if (scanner.hasNext()) {
				String faultyString = scanner.next();
				throw new InvalidPipedDataException(msg, faultyString, player);
				
			}
		} catch (InvalidPipedDataException e) {
		}
		scanner.close();
	}
	
	private String[] parsePlayerInfo(String info) {
		String[] tmpPlayer = new String[3];
		Scanner roomScanner = new Scanner(info);
		roomScanner.useDelimiter("|");
		try {
			for (int i = 0; i < 3; i++) {
				tmpPlayer[i] = roomScanner.next();
			}
			if (roomScanner.hasNext()) {
				roomScanner.close();
				throw new InvalidPipedDataException("", info, player);
			}
			roomScanner.close();
		} catch (InvalidPipedDataException e) {
		}
		
		return tmpPlayer;
	}
	
}
