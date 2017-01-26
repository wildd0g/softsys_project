package view;

import java.util.Scanner;

public class ClientTUI {

	private Scanner scan;
	private Client client;
	public boolean active;
	public ClientParser parser;

	public ClientTUI(Client backend) {
		this.client = backend;
		parser = new ClientParser();
		active = true;
	}

	public void start() {
		
		System.out.println("Hello and Welcome!");
		
		scan = new Scanner(System.in);

		while (active) {

			String input = readOut(scan);
			parser.handle(input);

		}

		scan.close();

	}
	
	public String getDefaults

	//method to translate console input into a string
	public String readOut(Scanner scanner) {

		//initialise empty string
		String input = "";
		//initialise boolean to false, so it states the input has not been read yet. 
		boolean inputRead = false;		

		do {
			//give instruction prompt
			System.out.print("Please provide input: ");

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
