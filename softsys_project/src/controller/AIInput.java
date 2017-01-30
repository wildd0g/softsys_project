package controller;

import model.Board;
import model.Mark;
import aistrategies.*;


public class AIInput extends ClientInput {

	private Strategy strat;
	
	AIInput() {
		strat = new NaiveStrategy();
	}
	
	@Override
	public void determineMove(ClientGame game) {
		Board board = game.getBoard();
		Mark m = game.getMark(Client.getID());
		
		// TODO Auto-generated method stub

	}

}
