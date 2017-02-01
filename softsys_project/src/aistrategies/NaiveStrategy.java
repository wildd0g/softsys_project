package aistrategies;

import java.util.Arrays;
import java.util.Set;

import model.Board;
import model.Mark;

public class NaiveStrategy extends Strategy {

	@Override
	public String getName() {
		return "Naive";
	}

	@Override
	public int[] determineMove(Board board, Mark m) {
		Set<Integer[]> valid = validMoves(board);
		Object[] validArray = valid.toArray();
		int input = (int) Math.floor(Math.random() * valid.size());
		Integer[] moveSet = (Integer[]) validArray[input];
		
		int[] intMove = Arrays.stream(moveSet).mapToInt(Integer::intValue).toArray();
		return intMove;
	}

}
