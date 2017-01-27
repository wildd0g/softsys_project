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

			String input = readOut(scan);
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
	
	//method requestion player for the
	//room they want to join
	public void roomInfo() {
		//TODO implement
		readOut
	}
	
	//method requesting player for the 
	//specifics of createRoom
	public void createInfo() {
		//TODO implement
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

	//method to translate console input into a string
	public String readOut(Scanner scanner, String instruction) {

		//initialise empty string
		String input = "";
		//initialise boolean to false, so it states the input has not been read yet. 
		boolean inputRead = false;		
		//store string message
		String prompt = instruction;

		do {
			//give instruction prompt
			System.out.print(prompt);

			//input is separated into individual lines that can be read
			try (Scanner scannerLine = new Scanner(scanner.nextLine());) {

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
		return input;
	}
	
	

}
