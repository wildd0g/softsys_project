package controller;

public class InvalidCommandException extends Exception {

	public InvalidCommandException(Player player) {
		super("error 4: Input to parser not recognized or not allowed at this time.");
		//TODO send error 4 to player.
	}
	
}
