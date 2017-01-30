package aistrategies;

import java.util.Set;
import java.util.HashSet;

import model.Board;
import model.Mark;

public class NaiveStrategy implements Strategy {

	@Override
	public String getName() {
		return "Naive";
	}

	@Override
	public int determineMove(Board b, Mark m) {
		Set<Integer> valid = new HashSet<Integer>();
		for (int i = 0; i < (Board.DIM * Board.DIM); i++){
			if (b.isEmptyField(i)){
				valid.add(i);
			}
		}

		Object[] validArray = valid.toArray();
		int input = (int)Math.floor(Math.random() * valid.size());

		return (int)validArray[input];
	}

}
