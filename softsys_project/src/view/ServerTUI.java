package view;

import java.util.Scanner;

import controller.Server;
import supportclasses.MaliciousInputException;

public class ServerTUI extends TUI{

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

}
