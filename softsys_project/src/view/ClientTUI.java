package view;

import java.util.Scanner;

import model.Board;
import model.Mark;
import aistrategies.NaiveStrategy;
import aistrategies.Strategy;
import controller.Client;
import controller.ClientGame;
import supportclasses.Capabilities;
import supportclasses.MaliciousInputException;

public class ClientTUI extends TUI {

//	private Scanner scan;
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
			
		//scan = new Scanner(System.in);
		

		String name = "";
		name = readOut("Please, enter a username: ");
		Client.name = name;
		System.out.println("Welcome, " + Client.name);
		String controling = readOut("Are you a 'HUMAN' or an 'AI'?");
		Client.client.setInput(controling);
		
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
			if (portNum < 1000 || portNum > 9999) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe1) {
			try {
				getVal("Please enter the port number: ", "port");
			} catch (MaliciousInputException mie2) {
				//malicious input exception shuts down system. 
				mie2.getMessage();
			}
		}
				
		client.getConnected(simpleIP, portNum);
		
	}
	
	public void printWrite(String stringy) {
		System.out.println(stringy);
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
		
		Client.makeMove(moveX, moveY);
		
	}
	
	//method to assure proper integer value from input
	public int getVal(String stringy, String checker) throws MaliciousInputException {
		
		int value = -1;
		boolean succes;
		int capability = -1;
		int minimum = -1;
		
		//Switch statement uses input to identify for which values
		//TODO handle all freaking errors
		switch (checker) {
			case "players": 
				try {
					capability = Integer.parseInt(Capabilities.Client.AMOUNTOFPLAYERS);
					minimum = 0;
				} catch (NumberFormatException nfes1) {
					System.out.println(nfes1.getMessage());
				} 
				break;
				
			case "width":
				try {
					capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONX);
					minimum = 0;
				} catch (NumberFormatException nfes2) {
					System.out.println(nfes2.getMessage());
				}
				break;
				
			case "depth":
				try {
					capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONY);
					minimum = 0;
				} catch (NumberFormatException nfes3) {
					System.out.println(nfes3.getMessage());
				}
				break;
				
			case "height":
				try {
					capability = Integer.parseInt(Capabilities.Client.MAXDIMENSIONZ);
					minimum = 0;
				} catch (NumberFormatException nfes4) {
					System.out.println(nfes4.getMessage());
				}
				break;
				
			case "win":
				try {
					capability = Integer.parseInt(Capabilities.Client.LENGTHTOWIN);
					minimum = 0;
				} catch (NumberFormatException nfes5) {
					System.out.println(nfes5.getMessage());
				}
				break;
				
			case "port":
				capability = 9999;
				minimum = 1000;
				
				break;	
				
		}
		
		
		//one attempt to parse integer
		String input = "";
		try {
			input = readOut(stringy);
			value = Integer.parseInt(input);
			if (!(value >= minimum) || !(value <= capability)) {
				value = -1;	
			}
		} catch (NumberFormatException nfe1) {
			
			//TODO properly handle Exception
			nfe1.getMessage();
			
			boolean playing = false;
			if (Client.client.getGame() != null) {
				playing = true;
			}
			
			//if it doesn't parse, repeat the question 22 times
			for (int i = 0; i < 22; i++) {
				
				if (playing) {
					if (input.equals("HINT")) {
						Strategy hint = new NaiveStrategy();
						ClientGame game = Client.client.getGame();
						Board b = game.getBoard();
						Mark m = game.getMark(Client.getID());
						int[] hintMove = hint.determineMove(b, m);
						this.printWrite("Try this one: hight: "
								+ hintMove[0] + ", Width: " + hintMove[1]);
					} else {
						this.printWrite("tip: use 'HINT'");
					}
				}
				
				
				//succes to potentially true
				succes = true;
				
				try {
					input = readOut(stringy);
					value = Integer.parseInt(input);
					if (!(value >= minimum) || !(value <= capability)) {
						value = -1;						
					}
				} catch (NumberFormatException nfe2) {
					//confirm failure
					succes = false;
					//TODO properly handle exception
					nfe2.getMessage();
					throw new MaliciousInputException();
				}
				
				if (succes) {
					break;
				}
				
			}
		}
		return value;
	}
	
	public void shutDown() {
		active = false;
	}
}
