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
    public Mark cycle(int players) {
        if (this == EMPTY) {
            return AA;
        } else if (this == AA) {
            return BB;
        } else if (this == BB) {
        	if (players > 2) {
        		return CC;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == CC) {
        	if (players > 3) {
        		return DD;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == DD) {
        	if (players > 4) {
        		return EE;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == EE) {
        	if (players > 5) {
        		return FF;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == FF) {
        	if (players > 6) {
        		return GG;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == GG) {
        	if (players > 7) {
        		return HH;
        	} else {
        		return EMPTY.cycle(players);
        	}
        } else if (this == HH) {
        	return EMPTY.cycle(players);
        } else {
            return EMPTY;
        }
    }
    
    public Mark cycleReverse(int players) {
        if (this == BB) {
            return AA;
        } else if (this == CC) {
            return BB;
        } else if (this == DD) {
        	if (players > 2) {
        		return CC;
        	} else {
        		return CC.cycleReverse(players);
        	}
        } else if (this == EE) {
        	if (players > 3) {
        		return DD;
        	} else {
        		return DD.cycleReverse(players);
        	}
        } else if (this == FF) {
        	if (players > 4) {
        		return EE;
        	} else {
        		return EE.cycleReverse(players);
        	}
        } else if (this == GG) {
        	if (players > 5) {
        		return FF;
        	} else {
        		return FF.cycleReverse(players);
        	}
        } else if (this == HH) {
        	if (players > 6) {
        		return GG;
        	} else {
        		return GG.cycleReverse(players);
        	}
        } else if (this == AA) {
        	if (players > 7) {
        		return HH;
        	} else {
        		return HH.cycleReverse(players);
        	}
        } else {
            return EMPTY;
        }
    }
    
    @Override
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
