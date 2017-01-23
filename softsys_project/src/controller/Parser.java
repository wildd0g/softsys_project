package controller;

import java.util.Scanner;
import java.util.ArrayList;

public class Parser {

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
	
	public Parser() {

	}

	//@ require msg == valid protocol line
	//TODO add check for valid time to process the sent data
	public void parse(String msg) {
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
				try {
					if (lineScanner.hasNext()) {
						throw new InvalidPipedDataException(msg, lineScanner.next());
					}
				} catch (InvalidPipedDataException e) {
					//TODO error 7
				}
				
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
				try {
					if (lineScanner.hasNext()) {
						throw new InvalidPipedDataException(msg, lineScanner.next());
					}
				} catch (InvalidPipedDataException e) {
					//TODO error 7
				}
				
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
						for (int i = 1; i < 7; i++) {
							roomData[i] = Integer.parseInt(roomScanner.next());
						}
						if (roomScanner.hasNext()) {
							roomScanner.close();
							throw new InvalidPipedDataException(msg, room);
						}
						roomScanner.close();
					} catch (NumberFormatException e) {
						//TODO send error 7
					} catch (InvalidPipedDataException e) {
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
				try {
					if (lineScanner.hasNext()) {
						throw new InvalidPipedDataException(msg, lineScanner.next());
					}
				} catch (InvalidPipedDataException e) {
					//TODO error 7
				}
				
				//TODO let server assign socket to specefied room
				
				break;	
				
			case "error":
				System.out.println("recieved error " + lineScanner.next());
				try {
					if (lineScanner.hasNext()) {
						throw new InvalidPipedDataException(msg, lineScanner.next());
					}
				} catch (InvalidPipedDataException e) {
					//TODO error 7
				}
				break;
				
			case "assignID":
				playerID = Integer.parseInt(lineScanner.next());
				try {
					if (lineScanner.hasNext()) {
						throw new InvalidPipedDataException(msg, lineScanner.next());
					}
				} catch (InvalidPipedDataException e) {
					//TODO error 7
				}
				
				//TODO create player with ID, Name stc.
				
				break;
				
			default:
			}



		} else {
			//TODO send error 4
		}
	}

}
