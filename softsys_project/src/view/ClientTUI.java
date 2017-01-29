package view;

import java.util.Scanner;

import controller.Capabilities;

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
	//IP address and port number
	public void connectInfo() {
		
		//set invalid values
		String simpleIP = null;
		int portNum = -1;
		
		/**Readout function was used
		 * reason: variables are uncheckable
		 */
		
		// get required IP adress
		simpleIP = readOut("Please enter the server's simple IP: ");
		
		// get required port number
		try {
			portNum = Integer.parseInt(readOut("Please enter the port number: "));
		} catch (NumberFormatException nfe1) {
			//properly handle Exception
			nfe1.getMessage();
		}
		
		client.getConnected(simpleIP, portNum);
		
	}
	
	//method requesting player for the
	//room they want to join
	public void roomInfo() {
		//set invalid roomID
		int roomID = -1;
		
		//get required roomID
		try {
			roomID = getVal("Please enter the room ID of the room you want to join: ", "roomID");
		} catch (MaliciousInputException mie3) {
			//Exception exits all programs on this virtual machine
			//TODO properly handle Exception
			
			mie3.getMessage();
			
		}
		
		client.joinRoom(roomID);
		
	}
	
	//method requesting player for the 
	//specifics of createRoom
	public void createInfo() {
		
		//set invalid values
		int roomPlayers = -1;
		int roomWidth = -1;
		int roomDepth = -1;
		int roomHeight = -1;
		int roomWin = -1;
		
		//get required, player amount, room dimensions and win length
		try {
			roomPlayers = getVal("Please enter desired amount of players: ", "players");
			roomWidth = getVal("Please enter desired board width: ", "width");
			roomDepth = getVal("Please enter desired board depth: ", "depth");
			roomHeight = getVal("Please enter desired board height: ", "height");
			roomWin = getVal("Please enter desired win length: ", "win");
			
		} catch (MaliciousInputException mie4) {
			//Exception exits all programs on this virtual machine
			//TODO properly handle Exception
			mie4.getMessage();
			
		}
		
		client.createRoom(roomPlayers, roomWidth, roomDepth, roomHeight, roomWin);
		
	}
	
	//method requesting player for the
	//specifics of makeMove
	public void moveInfo() {
		
		//set invalid values;
		int moveX = -1;
		int moveY = -1;
		
		//get required x and y coordinates
		try {
			moveX = getVal("Please enter column in which you want to place your tile: ", "width");
			moveY = getVal("Please enter row in which you want to place your tile: ", "depth");
		} catch (MaliciousInputException mie5) {
			//Exception exits all programs on this virtual machine
			//TODO properly handle Exception
			mie5.getMessage();
			
		}
		
		client.makeMove(moveX, moveY);
		
	}
	
	//method to print message to the set output
	public void printMessage(String message) {
		//TODO implement
	}
	
	//method to assure proper integer value from input
	public int getVal(String stringy, String checker) throws MaliciousInputException{
		
		int value = -1;
		boolean succes;
		int capability = -1;
		
		//Switch statement uses input to identify for which values 
		switch (checker){
		case "players": 
			try {
				capability = Integer.parseInt(Capabilities.Client.AMOUNTOFPLAYERS);
			} catch (NumberFormatException nfes1) {
				System.out.println(nfes1.getMessage());
			} break;
			
		case "width":
			try {
				capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONX);
			} catch (NumberFormatException nfes2) {
				System.out.println(nfes2.getMessage());
			}
			
		case "depth":
			try {
				capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONY);
			} catch (NumberFormatException nfes3) {
				System.out.println(nfes3.getMessage());
			}
			
		case "height":
			try {
				capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONZ);
			} catch (NumberFormatException nfes4) {
				System.out.println(nfes4.getMessage());
			}
			
		case "win":
			try {
				capability = Integer.parseInt(Capabilities.Client.LENGTHTOWIN);
			} catch (NumberFormatException nfes5) {
				System.out.println(nfes5.getMessage());
			}
		
			
		}
		
		
		//one attempt to parse integer
		try {
			value = Integer.parseInt(readOut(stringy));
			if (!(value > 0) || !(value <= capability)) value = -1;	
		} catch (NumberFormatException nfe1) {
			
			//TODO properly handle Exception
			nfe1.getMessage();
			
			//if it doesn't parse, repeat the question 22 times
			for(int i = 0; i < 22; i++){
				
				//succes to potentially true
				succes = true;
				
				try {
					value = Integer.parseInt(readOut(stringy));
					if (!(value > 0) || !(value <= capability)) value = -1;						
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
