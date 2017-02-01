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
        if (this == HH) {
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
    
    public Mark cycleReverse() {
        if (this == BB) {
            return AA;
        } else if (this == CC) {
            return BB;
        } else if (this == DD) {
            return CC;
        } else if (this == EE) {
            return DD;
        } else if (this == FF) {
            return EE;
        } else if (this == GG) {
            return FF;
        } else if (this == HH) {
            return GG;
        } else if (this == AA) {
            return HH;
        } else {
            return EMPTY;
        }
    }
    
    //@override
    public String toString() {
    	if (this == AA) {
            return "AA";
        } else if (this == BB) {
            return "BB";
        } else if (this == CC) {
            return "DD";
        } else if (this == DD) {
            return "DD";
        } else if (this == EE) {
            return "EE";
        } else if (this == FF) {
            return "FF";
        } else if (this == GG) {
            return "GG";
        } else if (this == HH) {
        	return "HH";
        } else {
            return "EMPTY";
        }
    }
}
