package view;

import java.util.Scanner;

import controller.Server;
import controller.ServerParser;
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
	
	//method to get information from the console 
	public int start() throws MaliciousInputException {
		
		//set default port integer
		int port = 1212;
		
		//define message to request for port number
		String message = 
				"Please, enter 4 number port number to start the server on";
		
		try {
			
			//request portnumber
			port = Integer.parseInt(readOut(message));
			
			// check if entered integer is exactly 4 long
			if (!(port > 999) || !(port < 10000)) { 
				throw new NumberFormatException();
			}
		
		//catch invalid	portnumbers
		} catch (NumberFormatException nfe1) {
			
			boolean succes;
			
			//setup a loop to request it at a maximum 22 more times.
			for (int i = 0; i < 22; i++) {
				
				succes = true;
				
				try {
					//request portnumber
					port  = Integer.parseInt(readOut(message));
					
					// check if entered integer is exactly 4 long
					if (!(port > 999) || !(port < 10000)) { 
						throw new NumberFormatException();
					}
					
				} catch (NumberFormatException nfe2) {
					succes = false;
					//Shut down server and all underlying programs on this virtual machine
					throw new MaliciousInputException();
				}
				
				if (succes) {
					break;
				}
				
			}
			
		}
		
		return port;

	}
	
	//method to handle console input while server is running
	public void continuous() {
		
		while (active) {

			String input = readOut("Please, enter input");
			parser.handle(input);

		}
		
		System.out.println("Goodbye");
		scan.close();
		
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
