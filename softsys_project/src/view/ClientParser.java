package view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import controller.Client;
import controller.ClientGame;

public class ClientParser {

	private static Client client;
	private static ClientTUI tui;

	public ClientParser(Client parserClient, ClientTUI clientTui) {
		client = parserClient;
		tui = clientTui;
	}

	/** !! WARNING !!
	 * 
	 * this document is not checkstyle appliant 
	 * Therefore minor checkstyle violations are also not recognised
	 * 
	 */


	public void handle(String input) {
		String content = input;
		boolean possible = true;
		Scanner commandScanner = new Scanner(content);
		if (commandScanner.hasNext()) {
			String command = commandScanner.next();

			switch (command) {

				case "CONNECT":
					if (!Client.connected) {
						if (commandScanner.hasNext()) {
							String simpleIP = null;
							try {
								simpleIP = commandScanner.next();
								InetAddress.getByName(simpleIP);
							} catch (UnknownHostException unknown) {
								possible = false;
							}
							if (commandScanner.hasNext() && possible) {
								int portNum = Integer.parseInt(commandScanner.next());
								if (portNum < 10000 && portNum > 999) {
									client.getConnected(simpleIP, portNum);
								}
							} else {
								tui.connectInfo();
							}
						} else {
							tui.connectInfo();
						}
					} else {
						System.out.println(
								"Sorry, you are already connected" + 
								" and cannot perform this action again.");
					}
					break;
				
				case "REFRESH":
					client.requestRooms();
					break;
				
				case "JOINROOM":
					if (client.getGame() != null) {
						if (commandScanner.hasNext()) {
							int roomID = Integer.parseInt(commandScanner.next());
							client.joinRoom(roomID);
						} else {
							tui.roomInfo();
						}
					} else {
						System.out.println("Sorry, you can't do that at this moment");
					}
					break;
				
				case "CREATEROOM":
					ClientGame game = Client.client.getGame();
					if (game.equals(null)) {
						if (commandScanner.hasNext()) {
							int players = Integer.parseInt(commandScanner.next());
							int xDim = Integer.parseInt(commandScanner.next());
							int yDim = Integer.parseInt(commandScanner.next());
							int zDim = Integer.parseInt(commandScanner.next());
							int winLength = Integer.parseInt(commandScanner.next());
							client.createRoom(players, xDim, yDim, zDim, winLength);
						} else {
							tui.createInfo();
						}
					} else {
						System.out.println("Sorry, you can't do that at this moment");
					}
					break;
				
				case "LEAVE":
					if(client.getGame() != null){
						client.leaveRoom();
					} else {
						
					}
					break;
				
				case "MAKEMOVE":
					if (commandScanner.hasNext()) {
						int xPos = Integer.parseInt(commandScanner.next());
						if (commandScanner.hasNext()) {
							int yPos = Integer.parseInt(commandScanner.next());
							if (xPos >= 0 && xPos < client.getGame().maxRoomDimensionX 
									&& yPos >= 0 && yPos < client.getGame().maxRoomDimensionY) { 
								Client.makeMove(xPos, yPos);
							} else {
								tui.moveInfo();
							}
						} else {
							tui.moveInfo();
						}
					} else {
						tui.moveInfo();
					}
					break;
				
				case "EXIT": 
					Client.shutDown();
					break;
				
				case "START":
					if (Client.connected) {
						System.out.println(
								"You can use the following commands: \n" +
								"REFRESH allows you to see open rooms on the server \n" +
								"CREATEROOM create a new room \n" +
								"JOINROOM if you know the id " + 
								"of the room you want to participate in \n" +
								"LEAVE to leave the room you are presently in \n" +
								"MAKEMOVE during a game to set your move \n" +
								"EXIT to shut down the system"
								);
					} else {
						System.out.println("Sorry, you are not connected" + 
								" please use CONNECT first before continuing.");
					}
					break;
	
				default:
					System.out.println("Sorry, I don't recognise that command");
			}

		} 
		
		commandScanner.close();


	}

}
