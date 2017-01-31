package controller;

public abstract class ClientInput {
	
	// -- Instance variables -----------------------------------------

	// -- Constructors -----------------------------------------------

	public ClientInput() {
	}


	/*@
	       requires board != null & !board.isFull();
	       ensures board.isField(\result) & board.isEmptyField(\result);

	 */
	/**
	 * Determines the field for the next move.
	 * 
	 * @param board
	 *            the current game board
	 * @return the player's choice
	 */
	public abstract void determineMove(ClientGame game);

	// -- Commands ---------------------------------------------------

}
