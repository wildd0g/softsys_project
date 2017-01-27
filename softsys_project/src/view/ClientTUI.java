package view;

import java.util.Scanner;

public class ClientTUI {

	private Scanner scan;
	private Client client;
	public boolean active;
	public ClientParser parser;

	public ClientTUI(Client backend) {
		this.client = backend;
		parser = new ClientParser(client, this);
		active = true;
	}

	//main method to run the TUI
	public void start() {
		
		System.out.println("Hello and Welcome!");
		
		scan = new Scanner(System.in);

		while (active) {

			String input = readOut("Please, enter input:");
			parser.handle(input);

		}

		scan.close();

	}
	
	//method requesting player for the
	//default settings for a room
	public String getDefaults() {
		//TODO implement
		return null;
	}
	
	//method requesting player for the
	//IP adress and port number
	public void connectInfo() {
		String returnString = "CONNECT";
		//TODO implement
		
		parser.handle(returnString);
	}
	
	//method requesting player for the
	//room they want to join
	public void roomInfo() {
		//TODO implement
		readOut("Please enter roomID code: ");
	}
	
	//method requesting player for the 
	//specifics of createRoom
	public void createInfo() {
		int roomPlayers = -1;
		int roomWidth = -1;
		int roomDepth = -1;
		int roomHeight = -1;
		int roomWin = -1;
		try {
			roomPlayers = getVal("Please enter desired amount of players: ");
			roomWidth = getVal("Please enter desired board width: ");
			roomDepth = getVal("Please enter desired board depth: ");
			roomHeight = getVal("Please enter desired board height: ");
			roomWin = getVal("Please enter desired win length: ");
		} catch (MaliciousInputException mie) {
			//TODO add consequence
			mie.getMessage();
			System.exit(0);
		}
		
		
		
		client.createRoom(roomPlayers, roomWidth, roomDepth, roomHeight, roomWin);
		
	}
	
	//method requesting player for the
	//specifics of makeMove
	public void moveInfo() {
		//TODO implement
	}
	
	//method to print message to the set output
	public void printMessage(String message) {
		//TODO implement
	}
	
	//method to assure proper integer value from input
	public int getVal(String stringy) throws MaliciousInputException{
		
		int value = -1;
		boolean succes;
		
		
		
		//one attempt to parse integer
		try {
			value = Integer.parseInt(readOut(stringy));
		} catch (NumberFormatException nfe1) {
			
			//TODO properly handle Exception
			nfe1.getMessage();
			
			//if it doesn't parse, repeat the question 22 times
			for(int i = 0; i < 22; i++){
				
				//succes to potentially true
				succes = true;
				
				try {
					value = Integer.parseInt(readOut(stringy));
				} catch (NumberFormatException nfe2) {
					//confirm failure
					succes = false;
					//TODO properly handle exception
					nfe2.getMessage();
					throw new MaliciousInputException();
				}
				
				if(succes) break;
				
			}
		}
		return value;
	}

	//method to translate console input into a string
	public String readOut(String instruction) {

		//initialise empty string
		String input = "";
		//initialise boolean to false, so it states the input has not been read yet. 
		boolean inputRead = false;		
		//store string message
		String prompt = instruction;

		do {
			//give instruction prompt
			System.out.print("Please enter" + prompt);

			//input is separated into individual lines that can be read
			try (Scanner scannerLine = new Scanner(scan.nextLine());) {

				//give while loop command to stop after this round
				inputRead = true;

				//give the first word to input
				input = scannerLine.next();

				//add more words to input seperated by spaces if there are more.
				while (scannerLine.hasNext()) {
					input = input + " " + scannerLine.next();
				}
			}
		} while (!inputRead);

		//After while loop closed return all content that was retrieved.
		return input.trim();
	}
	
	

}
