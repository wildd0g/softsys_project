package view;

import java.util.Scanner;

//class that allows for no code duplication between ServerTUI and ClientTUI

public class TUI { 
	
	Scanner scan;
	
	public TUI() {
		scan = new Scanner(System.in);
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
			System.out.print(prompt);

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
