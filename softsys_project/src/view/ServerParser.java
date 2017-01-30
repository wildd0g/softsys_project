package view;

import java.util.Scanner;

import controller.Server;

public class ServerParser {
	
	Server server;
	ServerTUI tui;

	public ServerParser(Server parserServer, ServerTUI serverTui) {
		this.server = parserServer;
		this.tui = serverTui;
	}
	
	/** !! WARNING !!
	 * 
	 * this document is not checkstyle appliant 
	 * Therefore minor checkstyle violations are also not recognised
	 * 
	 */
	
	//method to process the given input
	public void handle(String input) {
		
		String content = input;
		Scanner commandScanner = new Scanner(content);
		
		if (commandScanner.hasNext()) {
			String command = commandScanner.next();
			
			switch (command) {
			
			case "EXIT":
				server.serverShutDown();
				break;
			default:
				System.out.println("Sorry, I don't recognise that command");
				
			}
			
		} 
		
		commandScanner.close();
		
	}
	
}
