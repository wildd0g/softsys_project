package view;

import java.util.Scanner;

//class that allows for no code duplication between ServerTUI and ClientTUI

public class TUI { 
	
	Scanner scan;
	
	public TUI() {
		scan = new Scanner(System.in);
	}
	
	//method to translate console input into a string
	public synchronized String readOut(String instruction) {

		//initialise empty string
		String input = "";
		//initialise boolean to false, so it states the input has not been read yet. 
		boolean inputRead = false;		
		//store string message
		String prompt = instruction;
		
		System.out.print(prompt);
		String read = null;
		
		while (!inputRead) {
			read = scan.nextLine();
			if (read != null) {
				Scanner scannerLine = new Scanner(read);

				//give while loop command to stop after this round
				inputRead = true;

				//give the first word to input
				input = "";

				//add more words to input separated by spaces if there are more.
				while (scannerLine.hasNext()) {
					input = input + " " + scannerLine.next();
				}
				scannerLine.close();

			}
		}
		
		//After while loop closed return all content that was retrieved.
		return input.trim();
	}

}
