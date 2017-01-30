package controller;

import java.util.Scanner;

import supportClasses.Capabilities;
import supportClasses.MaliciousInputException;

public class ServerTUI {

	private Scanner scan;
	private Server server;
	public boolean active;
	public ServerParser parser;

	public ServerTUI(Server backend) {
		this.server = backend;
		parser = new ServerParser(server, this);
		active = true;
		
		// initialise the new scanner
		scan = new Scanner(System.in);
		
	}

	public void start() {
		
		//set default port integer
		int port = 1212;
		
		try {
			
			port = Integer.parseInt(readOut(
					"Please, enter 4 number port number to start the server on"));
			
			// check if entered integer is 
			if(!(port > 999) || !(port < 10000)) { 
				throw new NumberFormatException();
			}
			
		} catch (NumberFormatException nfe1) {
			
			for(int i = 0; i < 22; i++) {
				
				
				
			}
			
			//TODO properly handle Exception
			nfe1.getMessage();
		}
		
		return port;

	}
	
	public void continuous() {
		
		while (active) {

			String input = readOut("Please, enter input");
			parser.handle(input);

		}
		
	}
	

	//method to translate console input into a string
	// same as ClientTUI
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

				//add more words to input separated by spaces if there are more.
				while (scannerLine.hasNext()) {
					input = input + " " + scannerLine.next();
				}
			}
		} while (!inputRead);

		//After while loop closed return all content that was retrieved.
		return input.trim();
	}

}
