package model;

public class InvalidFieldException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidFieldException() {
		super("error 3: the called field is not valid");
	}
	
	
	public InvalidFieldException(String errorMessage) {
		super(errorMessage);
	}

}
