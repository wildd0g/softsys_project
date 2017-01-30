package aistrategies;

import model.Board;
import model.Mark;

public interface Strategy {

	public String getName(); //return strategy name
	
	//return a next legal move for Board b and player with Mark m.
	public void determineMove(Board b, Mark m); 
	
}
