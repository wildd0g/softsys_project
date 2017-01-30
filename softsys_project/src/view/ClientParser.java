package view;

import java.util.Scanner;

import controller.Client;

public class ClientParser {

	Client client;
	ClientTUI tui;

	public ClientParser(Client parserClient, ClientTUI clientTui){
		this.client = parserClient;
		this.tui = clientTui;
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
				if (commandScanner.hasNext()) {
					String simpleIP = commandScanner.next();
					int portNum = Integer.parseInt(commandScanner.next());
					client.getConnected(simpleIP, portNum);
				} else {
					tui.connectInfo();
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

			default:
				System.out.println("Sorry, I don't recognise that command");
			}

		} 
		
		commandScanner.close();


	}

}
