package view;

public class MaliciousInputException extends Exception{

	public MaliciousInputException() {
		super("error: Input was unreadable for too many times.");
	}
	
	private static final long serialVersionUID = 11L;
	
}
