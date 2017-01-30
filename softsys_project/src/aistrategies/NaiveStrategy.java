package aistrategies;

import java.util.Set;
import java.util.HashSet;

import controller.Client;
import model.Board;
import model.InvalidFieldException;
import model.Mark;

public class NaiveStrategy implements Strategy {

	@Override
	public String getName() {
		return "Naive";
	}

	@Override
	public void determineMove(Board board, Mark m) {
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

		Object[] validArray = valid.toArray();
		int input = (int) Math.floor(Math.random() * valid.size());
		Integer[] moveSet = (Integer[]) validArray[input];
		Client.makeMove(moveSet[0], moveSet[1]);
	}

}
