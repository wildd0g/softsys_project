package controller;

import model.Board;
import model.Mark;
import aistrategies.*;


public class AIInput extends ClientInput {

	private Strategy strat;
	
	AIInput() {
		strat = new SmartStrategy();
	}
	
	@Override
	public void determineMove(ClientGame game) {
		Board board = game.getBoard();
		Mark m = game.getMark(Client.getID());
		int[] move = strat.determineMove(board, m);
		Client.makeMove(move[0], move[1]);
	}

}
