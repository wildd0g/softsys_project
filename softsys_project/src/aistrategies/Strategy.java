package aistrategies;

import java.util.HashSet;
import java.util.Set;

import model.Board;
import model.InvalidFieldException;
import model.Mark;

public abstract class Strategy {

	public abstract String getName(); //return strategy name
	
	//return a next legal move for Board b and player with Mark m.
	public abstract void determineMove(Board b, Mark m);
	
	protected Set<Integer[]> validMoves(Board board) {
		Set<Integer[]> valid = new HashSet<Integer[]>();
		int maxRow = board.dimRow;
		int maxCol = board.dimRow;
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				try {
					if (board.hasEmptyField(i, j)) {
						Integer[] validSet = {i, j};
						valid.add(validSet);
					}
				} catch (InvalidFieldException e) {
					System.out.println("This should NEVER have gone wrong!");
					e.printStackTrace();
				}
			}
		}
		return valid;
	}
	
}
