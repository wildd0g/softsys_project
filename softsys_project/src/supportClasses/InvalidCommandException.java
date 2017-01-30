package supportClasses;

import controller.Player;

public class InvalidCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommandException(Player player) {
		super("error 4: Input to parser not recognized or not allowed at this time.");
		player.send.error(4);
	}
	
}
