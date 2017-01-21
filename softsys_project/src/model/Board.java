package model;

/**
 * Game student for the Tic Tac Toe game. Module 2 lab assignment.
 *
 * @author Theo Ruys en Arend Rensink
 * @version $Revision: 1.4 $
 */
public class Board {
	public static int dimRow = 4;
	public static int dimCol = 4;
	public static int dimLvl = 4;
	public static int winLength = 4;
	public static int players = 2;
	/**
	 * The dimRow by dimCol by dimLvl fields of the Tic Tac Toe student.
	 */
	//@ private invariant fields.length == dimRow*dimCol*dimLvl;
	/*@ invariant (\forall int i; 0 <= i & i < dimRow*dimCol*dimLvl;
        getField(i) == Mark.EMPTY || getField(i) == Mark.AA || getField(i) == Mark.BB ||
        getField(i) == Mark.CC || getField(i) == Mark.DD || getField(i) == Mark.EE ||
        getField(i) == Mark.FF || getField(i) == Mark.GG || getField(i) == Mark.HH); */

	private Mark[] fields = new Mark[dimRow * dimCol * dimLvl];

	// -- Constructors -----------------------------------------------

	/**
	 * Creates an empty student.
	 */
	//@ ensures (\forall int i; 0 <= i & i < DIMROW * DIMCOL * DIMLVL;
	/*@ this.getField(i) == Mark.EMPTY); */
	public Board() {
		reset();
	}

	public Board(int row, int col, int lvl, int win, int playnr) {
		dimRow = row;
		dimCol = col;
		dimLvl = lvl;
		winLength = win;
		fields = new Mark[dimRow * dimCol * dimLvl];
		players = playnr;
		reset();
	}
	/**
	 * Creates a deep copy of this field.
	 */
	/*@ ensures \result != this;
        ensures (\forall int i; 0 <= i & i < DIMROW * DIMCOL * DIMLVL;
                                \result.getField(i) == this.getField(i));
      @*/
	public Board deepCopy() {
		Board copy = new Board(dimRow, dimCol, dimLvl, winLength, players);
		for (int i = 0; i < dimRow * dimCol * dimLvl; i++) {
			copy.fields[i] = this.fields[i];
		}
		return copy;
	}

	/**
	 * Calculates the index in the linear array of fields from a (row, col)
	 * pair.
	 * @return the index belonging to the (row,col)-field
	 */
	//@ requires 0 <= row & row < DIMROW;
	//@ requires 0 <= col & col < DIMCOL;
	//@ requires 0 <= lvl & lvl < DIMLVL;
	//@ ensures \result == (dimCol * (dimRow * lvl + row)) + col;
	//@ ensures this.isField(\result);
	/*@pure*/
	public int index(int row, int col, int lvl) throws InvalidFieldException {
		if (!isField(row, col, lvl)) {
			throw new InvalidFieldException();
		} 
		return (dimCol * (dimRow * lvl + row)) + col;
	}

	/**
	 * Returns true if ix is a valid index of a field on the student.
	 * @return true if 0 <= index < DIM*DIM
	 */
	//@ ensures \result == (0 <= index && index < DIMROW * DIMCOL * DIMLVL);
	/*@pure*/
	public boolean isField(int index) {
		return 0 <= index && index < dimRow * dimCol * dimLvl;
	}

	/**
	 * Returns true of the (row,col) pair refers to a valid field on the student.
	 *
	 * @return true if 0 <= row < DIM && 0 <= col < DIM
	 */
	//@ ensures \result == (0 <= row && row < dimRow && 0 <= col && col < dimCol &&
	/*@ 0 <= lvl && lvl <= dimLvl);
	/*@pure*/
	public boolean isField(int row, int col, int lvl) {
		return 0 <= row && row < dimRow
				&& 0 <= col && col < dimCol
				&& 0 <= lvl && lvl < dimLvl;
	}

	/**
	 * Returns the content of the field i.
	 *
	 * @param i
	 *            the number of the field (see NUMBERING)
	 * @return the mark on the field
	 */
	//@ requires this.isField(i);
	//@ ensures \result == Mark.EMPTY || \result == Mark.XX || \result == Mark.OO;
	/*@pure*/
	public Mark getField(int i) {
		Mark result = fields[i];
		return result;
	}

	/**
	 * Returns the content of the field referred to by the (row,col) pair.
	 *
	 * @param row
	 *            the row of the field
	 * @param col
	 *            the column of the field
	 * @return the mark on the field
	 */
	//@ requires this.isField(row,col);
	//@ ensures \result == Mark.EMPTY || \result == Mark.XX || \result == Mark.OO;
	/*@pure*/
	public Mark getField(int row, int col, int lvl) throws InvalidFieldException {
		return getField(index(row, col, lvl));
	}

	/**
	 * Returns true if the field i is empty.
	 *
	 * @param i
	 *            the index of the field (see NUMBERING)
	 * @return true if the field is empty
	 */
	//@ requires this.isField(i);
	//@ ensures \result == (this.getField(i) == Mark.EMPTY);
	/*@pure*/
	public boolean isEmptyField(int i) {
		return getField(i) == Mark.EMPTY;
	}

	/**
	 * Returns true if the field referred to by the (row,col) pair it empty.
	 *
	 * @param row
	 *            the row of the field
	 * @param col
	 *            the column of the field
	 * @return true if the field is empty
	 */
	//@ requires this.isField(row, col, lvl);
	//@ ensures \result == (this.getField(row, col, lvl) == Mark.EMPTY);
	/*@pure*/
	public boolean isEmptyField(int row, int col, int lvl) throws InvalidFieldException {
		return isEmptyField(index(row, col, lvl));
	}

	/**
	 * Tests if the whole student is full.
	 *
	 * @return true if all fields are occupied
	 */
	//@ ensures \result == (\forall int i; i <= 0 & i < DIMROW * DIMCOL * DIMLVL;
	/*@ this.getField(i) != Mark.EMPTY); */
	/*@pure*/
	public boolean isFull() {
		boolean result = true;
		for (int i = 0; i < dimRow * dimCol * dimLvl; i++) {
			if (!isEmptyField(i)) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * Returns true if the game is over. The game is over when there is a winner
	 * or the whole student is full.
	 *
	 * @return true if the game is over
	 */
	//@ ensures \result == this.isFull() || this.hasWinner();
	/*@pure*/
	public boolean gameOver() {
		return isFull() || hasWinner();
	}

	/**
	 * Checks whether there is a line of a given length which only contains the mark
	 * m.
	 *
	 * @param dir[]
	 * 				the direction to search in, defined by an array of 3 integers,
	 * 				ranging from -1 to 1. index 0 is row direction,
	 * 				index 1 is column direction and index 2 is level direction.
	 *            
	 * @param m
	 * 				the mark of interest
	 * @param length
	 * 				the length of the line there needs to be checked for
	 * @return true if there is a row controlled by m
	 */
	//Checks in the direction indicated by dir (1 , 0 or -1) for the mark m
	//during the specified length
	/*@ pure */
	//TODO: eventually change to streams, or if () return?
	private boolean checkConnect(int[] dir, Mark m, int length) {
		try {
			if (dir.length != 3 || 
					(dir[0] == 0 && dir[1] == 0 && dir[2] == 0) ||
					dir[0] < -1 || dir[0] > 1 ||
					dir[1] < -1 || dir[1] > 1 ||
					dir[2] < -1 || dir[2] > 1) {
				throw new InvalidFieldException("How the fuck did this thest fail?"
						+ "Who the fuck called this?" + dir);
			}
		} catch (InvalidFieldException e) {
			e.printStackTrace();
		}
		int rowMin = Math.max(0, 0 - dir[0] * length);
		int rowMax = Math.min(dimRow, dimRow - dir[0] * length);
		int colMin = Math.max(0, 0 - dir[1] * length);
		int colMax = Math.min(dimCol, dimCol - dir[1] * length);
		int lvlMin = Math.max(0, 0 - dir[2] * length);
		int lvlMax = Math.min(dimLvl, dimLvl - dir[2] * length);
		
		boolean result = false;
		
		try {
			for (int i = rowMin; i < rowMax; i++) {
				for (int j = colMin; j < colMax; j++) { 
					for (int k = lvlMin; k < lvlMax; k++) {
						boolean resultL = true;
						for (int l = 0; l < length; l++) {
							resultL = getField(index(
									i + dir[0] * l, j + dir[1] * l, k + dir[2] * l
									)) == m;
							if (!resultL) {
								break;
							}
						}
						result = resultL;
						if (result) { 
							break;
						}
					}
					if (result) {
						break;
					}
				}
				if (result) {
					break;
				} 
			}
		} catch (InvalidFieldException e) {
			System.out.println("This should NOT be able to fail due to the check at the beginning,"
					+ " how did it?");
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * Checks whether there is a row which is full and only contains the mark
	 * m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a row controlled by m
	 */
	/*@ pure */
	public boolean hasRow(Mark m) {
		int[] dir = {1, 0, 0};
		return checkConnect(dir, m, winLength);
	}

	
	
	/**
	 * Checks whether there is a column which is full and only contains the mark
	 * m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a column controlled by m
	 */
	/*@ pure */
	public boolean hasColumn(Mark m) {
		int[] dir = {0, 1, 0};
		return checkConnect(dir, m, winLength);
	}

	/**
	 * Checks whether there is a column which is full and only contains the mark
	 * m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a column controlled by m
	 */
	/*@ pure */
	public boolean hasLevels(Mark m) {
		int[] dir = {0, 0, 1};
		return checkConnect(dir, m, winLength);
	}
	
	/**
	 * Checks whether there is a face diagonal which only contains the
	 * mark m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a diagonal controlled by m
	 */
	/*@ pure */
	
	public boolean hasFaceDiagonal(Mark m) {
		boolean result = false;
		int[] dirOnRow1 = {0, 1, 1};
		int[] dirOnRow2 = {0, 1, -1};
		int[] dirOnCol1 = {1, 0, 1};
		int[] dirOnCol2 = {1, 0, -1};
		int[] dirOnLvl1 = {1, 1, 0};
		int[] dirOnLvl2 = {1, -1, 0};
		int[][] directions = {dirOnRow1, dirOnRow2, dirOnCol1, dirOnCol2, dirOnLvl1, dirOnLvl2};
		for (int i = 0; i < 6; i++) {
			result = checkConnect(directions[i], m, winLength);	
			if (result) {
				break;
			}
		}
		return result;
	}
	
	/**
	 * Checks whether there is a space diagonal which only contains the
	 * mark m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a diagonal controlled by m
	 */
	/*@ pure */
	
	public boolean hasSpaceDiagonal(Mark m) {
		boolean result = false;
		int[] dir1 = {1, 1, 1};
		int[] dir2 = {1, 1, -1};
		int[] dir3 = {1, -1, 1};
		int[] dir4 = {1, -1, -1};
		int[][] directions = {dir1, dir2, dir3, dir4};
		for (int i = 0; i < 4; i++) {
			result = checkConnect(directions[i], m, winLength);	
			if (result) {
				break;
			}
		}
		return result;
	}

	/**
	 * Checks if the mark m has won. A mark wins if has a line
	 * along a row, column, across levels, in the direction of any face diagonal
	 * or in the direction of any space diagonal of the set win length.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if the mark has won
	 */
	//@requires m;
	//@ ensures \result == this.hasRow(m) || this.hasColumn(m) | this.hasDiagonal(m);
	/*@ pure */
	public boolean isWinner(Mark m) {
		return hasRow(m) || hasColumn(m) || hasLevels(m)
				|| hasFaceDiagonal(m) || hasSpaceDiagonal(m);
	}

	/**
	 * Returns true if the game has a winner. This is the case when one of the
	 * marks controls at least one row, column or diagonal.
	 *
	 * @return true if the student has a winner.
	 */
	//@ ensures \result == isWinner(Mark.XX) | \result == isWinner(Mark.OO);
	/*@pure*/
	public boolean hasWinner() {
		Mark m = Mark.EMPTY;
		Boolean hasWinner = false;
		for (int i = 0; i < players; i++) {
			m = m.cycle();
			isWinner(m);
			if (hasWinner) {
				break;
			}
		}
		return hasWinner;
	}

	/**
	 * Returns a String representation of this student. In addition to the current
	 * situation, the String also shows the numbering of the fields.
	 *
	 * @return the game situation as String
	 */
	public String toString() {
		//TODO make a TUI representation constructed by toString
		return null;
	}

	/**
	 * Empties all fields of this student (i.e., let them refer to the value
	 * Mark.EMPTY).
	 */
	/*@ ensures (\forall int i; 0 <= i & i < DIMROW * DIMCOL * DIMLVL;
                                this.getField(i) == Mark.EMPTY); @*/
	public void reset() {
		for (int i = 0; i < dimRow * dimCol * dimLvl; i++) {
			setField(i, Mark.EMPTY);
		}
	}

	/**
	 * Sets the content of field i to the mark m.
	 *
	 * @param i
	 *            the field number (see NUMBERING)
	 * @param m
	 *            the mark to be placed
	 */
	//@ requires this.isField(i);
	//@ ensures this.getField(i) == m;
	public void setField(int i, Mark m) {
		fields[i] = m;
	}

	/**
	 * Sets the content of the field represented by the (row,col) pair to the
	 * mark m.
	 *
	 * @param row
	 *            the field's row
	 * @param col
	 *            the field's column
	 * @param m
	 *            the mark to be placed
	 */
	//@ requires this.isField(row,col);
	//@ ensures this.getField(row,col) == m;
	public void setField(int row, int col, int lvl, Mark m) throws InvalidFieldException {
		setField(index(row, col, lvl), m);
	}
}
