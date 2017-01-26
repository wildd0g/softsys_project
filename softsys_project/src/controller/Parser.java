package controller;

import java.util.Scanner;
import java.util.ArrayList;

public class Parser extends Protocol {

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
	String chatMsg = null;
	
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

				case Protocol.Server.SERVERCAPABILITIES:
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
	
				case Protocol.Client.SENDCAPABILITIES:
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
	
				case Protocol.Server.SENDLISTROOMS:
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
					
					
				case Protocol.Client.GETROOMLIST:
					
					endOverflowCatcher(msg, lineScanner);
					
					player.send.sendListRooms();
					
					break;
				
				case Protocol.Client.JOINROOM:
					//parse string and store the variables
					roomID = Integer.parseInt(lineScanner.next());
					
					endOverflowCatcher(msg, lineScanner);
					
					//TODO let server assign socket to specefied room
					
					break;	
					
				case Protocol.Server.ERROR:
					System.out.println(Protocol.getError(lineScanner.next()));
					
					endOverflowCatcher(msg, lineScanner);
					
					break;
					
				case Protocol.Server.ASSIGNID:
					playerID = Integer.parseInt(lineScanner.next());
					
					endOverflowCatcher(msg, lineScanner);
					
					//TODO create player with ID, Name stc.
					
					
					break;
					
				case Protocol.Client.LEAVEROOM:
					
					endOverflowCatcher(msg, lineScanner);
					
					
					//TODO cmd player leave room
					
					
					break;
					
				case Protocol.Server.STARTGAME:
					
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
					
				case Protocol.Server.TURNOFPLAYER:
					
					turn = Integer.parseInt(lineScanner.next());
					
					endOverflowCatcher(msg, lineScanner);
					
					//TODO if turn 
					
					break;
	
				case Protocol.Client.MAKEMOVE:
	
					moveX = Integer.parseInt(lineScanner.next());
					moveY = Integer.parseInt(lineScanner.next());
	
					endOverflowCatcher(msg, lineScanner);
	
					//TODO server: apply move to local board and send along if valid
	
					break;
					
				case Protocol.Server.NOTIFYMOVE:
					
					playerID = Integer.parseInt(lineScanner.next()); 
					moveX = Integer.parseInt(lineScanner.next());
					moveY = Integer.parseInt(lineScanner.next());
	
					endOverflowCatcher(msg, lineScanner);
	
					//TODO client: apply verefied move to local board
	
					break;
	
				case Protocol.Server.NOTIFYEND:
					
					String gameEnd = lineScanner.next();
					if (lineScanner.hasNext()){
						playerID = Integer.parseInt(lineScanner.next());
					}
					
					endOverflowCatcher(msg, lineScanner);
					
					//System.out.println(Protocol.getWin(lineScanner.next()));
					//TODO client: end game based on end condition and with winner ID
	
					break;
					
				case Protocol.Client.SENDMESSAGE:
					
					playerID = Integer.parseInt(lineScanner.next());
					chatMsg = lineScanner.nextLine();
					
					endOverflowCatcher(msg, lineScanner);
	
					//TODO server: send chatMsg FROM playerID to all
	
					break;
	
				case Protocol.Server.NOTIFYMESSAGE:
					
					String senderName = lineScanner.next();
					chatMsg = lineScanner.nextLine();
					
					endOverflowCatcher(msg, lineScanner);
	
					//TODO client: display to chat, senderName sent chatMsg
	
					break;
	
				case Protocol.Client.REQUESTLEADERBOARD:
					
					endOverflowCatcher(msg, lineScanner);
	
					player.send.sendLeaderboard();
	
					break;
					
				case Protocol.Server.SENDLEADERBOARD:
					
					ArrayList<String[]> leaderList = new ArrayList<String[]>();
					while (lineScanner.hasNext()) {
						leaderList.add(parseLeaderInfo(lineScanner.next()));
					}
					
					endOverflowCatcher(msg, lineScanner);
	
					//TODO client: display the leaderboard
	
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
		} finally {
			scanner.close();
		}
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
	
	private String[] parseLeaderInfo(String info) {
		String[] tmpPlayer = new String[4];
		Scanner leaderScanner = new Scanner(info);
		leaderScanner.useDelimiter("|");
		try {
			for (int i = 0; i < 4; i++) {
				tmpPlayer[i] = leaderScanner.next();
			}
			if (leaderScanner.hasNext()) {
				throw new InvalidPipedDataException("", info, player);
			}
		} catch (InvalidPipedDataException e) {
		} finally {
			leaderScanner.close();
		}
		
		return tmpPlayer;
	}
	
}
