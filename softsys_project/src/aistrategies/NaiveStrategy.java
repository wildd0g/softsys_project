package aistrategies;

import java.util.Set;

import controller.Client;
import model.Board;
import model.Mark;

public class NaiveStrategy extends Strategy {

	@Override
	public String getName() {
		return "Naive";
	}

	@Override
	public void determineMove(Board board, Mark m) {
		Set<Integer[]> valid = validMoves(board);
		Object[] validArray = valid.toArray();
		int input = (int) Math.floor(Math.random() * valid.size());
		Integer[] moveSet = (Integer[]) validArray[input];
		Client.makeMove(moveSet[0], moveSet[1]);
	}

}
