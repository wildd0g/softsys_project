package model;

/**
 * Represents a mark in the Tic Tac Toe game. There three possible values:
 * Mark.XX, Mark.OO and Mark.EMPTY.
 * Module 2 lab assignment
 * 
 * @author Theo Ruys
 * @version $Revision: 1.4 $
 */
public enum Mark {
    
    EMPTY, AA, BB, CC, DD, EE, FF, GG, HH;

    /*@
       ensures this == Mark.XX ==> \result == Mark.OO;
       ensures this == Mark.OO ==> \result == Mark.XX;
       ensures this == Mark.EMPTY ==> \result == Mark.EMPTY;
     */
    /**
     * Returns the other mark.
     * 
     * @return the other mark is this mark is not EMPTY or EMPTY
     */
    public Mark cycle() {
        if (this == EMPTY) {
            return AA;
        } else if (this == AA) {
            return BB;
        } else if (this == BB) {
            return CC;
        } else if (this == CC) {
            return DD;
        } else if (this == DD) {
            return EE;
        } else if (this == EE) {
            return FF;
        } else if (this == FF) {
            return GG;
        } else if (this == GG) {
            return HH;
        } else {
            return EMPTY;
        }
    }
}
