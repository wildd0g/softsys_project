package supportclasses;

import controller.Client;
import controller.Player;

public class InvalidCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
		super("error 4: Input to parser not recognized or not allowed at this time.");
	}
		
	public InvalidCommandException(Player player) {
		player.send.error(4);
	}
	
	public InvalidCommandException(controller.Client client) {
		Client.sender.error(4);
	}
}
