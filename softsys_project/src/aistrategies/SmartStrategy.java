package aistrategies;

import java.util.Set;

import model.Board;
import model.InvalidFieldException;
import model.Mark;
import java.util.Arrays;

public class SmartStrategy extends Strategy {


	@Override
	public String getName() {
		return "Smart";
	}

	@Override
	public int[] determineMove(Board board, Mark m) {
		// TODO Auto-generated method stub
		//get a default random picked move 
		Set<Integer[]> valid = validMoves(board);

		Object[] validArray = valid.toArray();
		int input = (int) Math.floor(Math.random() * valid.size());
		Integer[] move = (Integer[]) validArray[input];

		//check if there is a winning move (either for XX or for OO)
		Integer[] winMoveSelf = winningMove(board.deepCopy(), m, validArray);
		Integer[] winMoveOther = winningMove(board.deepCopy(), 
				m.cycleReverse(board.players), validArray);
		
		//if it exists, chose that move for the computer, or to block it.
		Integer[] base = {-1, -1};
		if (winMoveSelf.equals(base)) {
			move = winMoveSelf;
		} else if (winMoveOther.equals(base)) {
			move = winMoveOther;
		}

		int[] intMove = Arrays.stream(move).mapToInt(Integer::intValue).toArray();
		return intMove;
	}

	private Integer[] winningMove(Board b, Mark m, Object[] validArray) {
		Integer[] move = {-1, -1};
		for (int i = 0; i < validArray.length; i++) {
			Board testB = b.deepCopy();
			
			Integer[] moveSet = (Integer[]) validArray[i];
			testMove(testB, moveSet[0], moveSet[1], m);
			if (testB.hasWinner()) {
				move = moveSet;
				break;
			}
		}
		return move;
		
			
	}

	private void testMove(Board board, int x, int y, Mark m) {
		int testZ = 0;
		boolean empty = false;
		while (board.isField(x, y, testZ) && !empty) {
			try {
				empty = board.isEmptyField(x, y, testZ);
			} catch (InvalidFieldException e) {
				empty = true;
			}
			if (!empty) {
				testZ++;
			}
		} //returned z if z is not a field or is empty
		try {
			board.setField(x, y, testZ, m);
		} catch (InvalidFieldException e) {
			e.getMessage();
		}
	}
	
	
}
