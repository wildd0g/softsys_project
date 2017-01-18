package connect4.model;

/**
 * Game student for the Tic Tac Toe game. Module 2 lab assignment.
 *
 * @author Theo Ruys en Arend Rensink
 * @version $Revision: 1.4 $
 */
public class Board {
	public static int DIMROW = 4;
	public static int DIMCOL = 4;
	public static int DIMLVL = 4;
	/*private static final String[] NUMBERING = {" 0 | 1 | 2 ", "---+---+---",
        " 3 | 4 | 5 ", "---+---+---", " 6 | 7 | 8 "};
    private static final String LINE = NUMBERING[1];
    private static final String DELIM = "     ";*/

	/**
	 * The DIM by DIM fields of the Tic Tac Toe student. See NUMBERING for the
	 * coding of the fields.
	 */
	//@ private invariant fields.length == DIM*DIM;
	/*@ invariant (\forall int i; 0 <= i & i < DIM*DIM;
        getField(i) == Mark.EMPTY || getField(i) == Mark.XX || getField(i) == Mark.OO); */

	private Mark[] fields = new Mark[DIMROW * DIMCOL * DIMLVL];

	// -- Constructors -----------------------------------------------

	/**
	 * Creates an empty student.
	 */
	//@ ensures (\forall int i; 0 <= i & i < DIMROW * DIMCOL * DIMLVL; this.getField(i) == Mark.EMPTY);
	public Board() {
		reset();
	}

	public Board(int row, int col, int lvl) {
		fields = new Mark[DIMROW * DIMCOL * DIMLVL];
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
		Board copy = new Board(DIMROW, DIMCOL, DIMLVL);
		for (int i = 0; i < DIMROW * DIMCOL * DIMLVL; i++) {
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
	/*@pure*/
	public int index(int row, int col, int lvl) {
		int i = (DIMCOL * (DIMROW * lvl + row)) + col;
		return i;
	}

	/**
	 * Returns true if ix is a valid index of a field on the student.
	 * @return true if 0 <= index < DIM*DIM
	 */
	//@ ensures \result == (0 <= index && index < DIMROW * DIMCOL * DIMLVL);
	/*@pure*/
	public boolean isField(int index) {
		return 0 <= index && index < DIMROW * DIMCOL * DIMLVL;
	}

	/**
	 * Returns true of the (row,col) pair refers to a valid field on the student.
	 *
	 * @return true if 0 <= row < DIM && 0 <= col < DIM
	 */
	//@ ensures \result == (0 <= row && row < DIM && 0 <= col && col < DIM);
	/*@pure*/
	public boolean isField(int row, int col, int lvl) {
		return isField(index(row, col, lvl));
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
	public Mark getField(int row, int col, int lvl) {
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
	//@ requires this.isField(row,col);
	//@ ensures \result == (this.getField(row,col) == Mark.EMPTY);
	/*@pure*/
	public boolean isEmptyField(int row, int col, int lvl) {
		return isEmptyField(index(row, col, lvl));
	}

	/**
	 * Tests if the whole student is full.
	 *
	 * @return true if all fields are occupied
	 */
	//@ ensures \result == (\forall int i; i <= 0 & i < DIMROW * DIMCOL * DIMLVL; this.getField(i) != Mark.EMPTY);
	/*@pure*/
	public boolean isFull() {
		boolean result = true;
		for (int i = 0; i < DIMROW * DIMCOL * DIMLVL; i++) {
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
	 * Checks whether there is a row which is full and only contains the mark
	 * m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a row controlled by m
	 */
	/*@ pure */
	//TODO: eventually change to streams, or if () return?
	public boolean hasRow(Mark m) {
		boolean result = false;
		for (int i = 0; i < DIMCOL; i++) {
			for (int j = 0; j < DIMLVL; j++) { 
				for (int k = 3; k < DIMROW; k++) {
					result = getField(index(i, j, k - 3)) == m &&
					getField(index(i, j, k - 2)) == m &&
					getField(index(i, j, k - 1)) == m &&
					getField(index(i, j, k)) == m;
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
		return result;
	}

	private boolean checkConnect(int[] dir, Mark m, int length) {
		if (direction.length != 3) {
			//TODO add exception, this is an error.
		}
		
		boolean result = false;
		for (int i = 0; i < DIMROW; i++) {
			for (int j = 0; j < DIMCOL; j++) { 
				for (int k = 0; k < DIMLVL; k++) {
					boolean resultL = true;
					for (int l = 0; l < length; l++) {
						resultL = getField(index(
								i + dir[0] * l, j + dir[1] * l, k + dir[2] * l)) == m;
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
		return result;
		
		
		return false;
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
	//TODO: eventually change to streams, or if () return?
	public boolean hasColumn(Mark m) {
		boolean result = false;
		for (int i = 0; i < DIMCOL; i++) {
			for (int j = 3; j < DIMLVL; j++) { 
				for (int k = 0; k < DIMROW; k++) {
					result = getField(index(i, j - 3, k)) == m &&
					getField(index(i, j - 2, k)) == m &&
					getField(index(i, j - 1, k)) == m &&
					getField(index(i, j, k)) == m;
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
		return result;
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
	//TODO: eventually change to streams, or if () return?
	public boolean hasLevels(Mark m) {
		boolean result = false;
		for (int i = 3; i < DIMCOL; i++) {
			for (int j = 0; j < DIMLVL; j++) { 
				for (int k = 0; k < DIMROW; k++) {
					result = getField(index(i - 3, j, k)) == m &&
					getField(index(i - 2, j, k)) == m &&
					getField(index(i - 1, j, k)) == m &&
					getField(index(i, j, k)) == m;
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
		return result;
	}
	
	/**
	 * Checks whether there is a diagonal which is full and only contains the
	 * mark m.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if there is a diagonal controlled by m
	 */
	/*@ pure */
	
	public boolean hasDiagonal(Mark m) {
		boolean result = false;
		boolean resultI = true;
		for (int i = 0; i < DIM; i++){
			resultI = (resultI && (getField(index(i,i)) == m));
		}
		result = (result || resultI);
		resultI = true;
		for (int i = 0; i < DIM; i++){
			resultI = (resultI && (getField(index(i,DIM-i-1)) == m));
		}
		result = (result || resultI);
		return result;
	}

	/**
	 * Checks if the mark m has won. A mark wins if it controls at
	 * least one row, column or diagonal.
	 *
	 * @param m
	 *            the mark of interest
	 * @return true if the mark has won
	 */
	//@requires m == Mark.XX | m == Mark.OO;
	//@ ensures \result == this.hasRow(m) || this.hasColumn(m) | this.hasDiagonal(m);
	/*@ pure */
	public boolean isWinner(Mark m) {
		return (hasRow(m) || hasColumn(m) || hasDiagonal(m));
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
		return (isWinner(Mark.XX) || isWinner(Mark.OO));
	}

	/**
	 * Returns a String representation of this student. In addition to the current
	 * situation, the String also shows the numbering of the fields.
	 *
	 * @return the game situation as String
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < DIM; i++) {
			String row = "";
			for (int j = 0; j < DIM; j++) {
				row = row + " " + getField(i, j).toString() + " ";
				if (j < DIM - 1) {
					row = row + "|";
				}
			}
			s = s + row + DELIM + NUMBERING[i * 2];
			if (i < DIM - 1) {
				s = s + "\n" + LINE + DELIM + NUMBERING[i * 2 + 1] + "\n";
			}
		}
		return s;
	}

	/**
	 * Empties all fields of this student (i.e., let them refer to the value
	 * Mark.EMPTY).
	 */
	/*@ ensures (\forall int i; 0 <= i & i < DIMROW * DIMCOL * DIMLVL;
                                this.getField(i) == Mark.EMPTY); @*/
	public void reset() {
		for (int i = 0; i < DIMROW*DIMCOL*DIMLVL; i++){
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
	public void setField(int row, int col, Mark m) {
		setField(index(row, col), m);
	}
}
