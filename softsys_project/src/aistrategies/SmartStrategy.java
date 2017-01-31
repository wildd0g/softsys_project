package aistrategies;

import java.util.HashSet;
import java.util.Set;

import model.Board;
import model.Mark;

public class SmartStrategy implements Strategy {


	@Override
	public String getName() {
		return "Smart";
	}

	@Override
	public int determineMove(Board b, Mark m) {
		// TODO Auto-generated method stub
		//get a default random picked move 
		Set<Integer> valid = new HashSet<Integer>();
		for (int i = 0; i < (Board.DIM * Board.DIM); i++){
			if (b.isEmptyField(i)){
				valid.add(i);
			}
		}

		Object[] validArray = valid.toArray();
		int input = (int)Math.floor(Math.random() * valid.size());
		int move = (int)validArray[input];

		//check if there is a winning move (either for XX or for OO)
		int winMoveSelf = winningMove(b.deepCopy(), m, validArray);
		int winMoveOther = winningMove(b.deepCopy(), m.other(), validArray);
		
		//if it exists, chose that move for the computer, or to block it.
		if (valid.contains(4)){
			move = 4;
		}else if (winMoveSelf != -1){
			move = winMoveSelf;
		}else if (winMoveOther != -1){
			move = winMoveOther;
		}

		return move;
	}

	int winningMove(Board b, Mark m, Object[] validArray){
		int move = -1;
		for (int i = 0; i < validArray.length; i++){
			Board testB = b.deepCopy();
			testB.setField((int)validArray[i], m);
			if (testB.hasWinner()){
				move = (int)validArray[i];
				break;
			}
		}
		
		
		return move;	
	}

}
