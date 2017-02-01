package view;

import java.util.Scanner;

import controller.Client;

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
		Scanner commandScanner = new Scanner(content);
		if (commandScanner.hasNext()) {
			String command = commandScanner.next();

			switch (command) {

			case "CONNECT":
				if (!client.connected) {
				if (commandScanner.hasNext()) {
					String simpleIP = commandScanner.next();
					int portNum = Integer.parseInt(commandScanner.next());
					client.getConnected(simpleIP, portNum);
				} else {
					tui.connectInfo();
				}
				} else {
					System.out.println("Sorry, you are already connected and cannot perform this action again.");
				}
				break;
			case "REFRESH":
				client.requestRooms();
				break;
			case "JOINROOM":
				if (commandScanner.hasNext()) {
					int roomID = Integer.parseInt(commandScanner.next());
					client.joinRoom(roomID);
				} else {
					tui.roomInfo();
				}
				break;
			case "CREATEROOM":
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
				break;
			case "LEAVE":
				client.leaveRoom();
				break;
			case "MAKEMOVE":
				if (commandScanner.hasNext()) {
					int xPos = Integer.parseInt(commandScanner.next());
					int yPos = Integer.parseInt(commandScanner.next());
					client.makeMove(xPos, yPos);
				} else {
					tui.moveInfo();
				}
				break;
			case "EXIT": 
				client.shutDown();
				break;
			case "START":
				if(client.connected) {
					client.requestRooms();
				} else {
					System.out.println("Sorry, you are not connected and cannot do that at this moment.");
				}
				break;

			default:
				System.out.println("Sorry, I don't recognise that command");
			}

		} 
		
		commandScanner.close();


	}

}
