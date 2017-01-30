package aistrategies;

import model.Board;
import model.Mark;

public interface Strategy {

	public String getName(); //return strategy name
	
	public int determineMove(Board b, Mark m); //return a next legal mive for Board b and player with Mark m.
	
}
