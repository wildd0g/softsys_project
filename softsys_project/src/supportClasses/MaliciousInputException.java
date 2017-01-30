package supportClasses;

public class MaliciousInputException extends Exception{

	public MaliciousInputException() {
		super("error: Input was unreadable for too many times.");
		System.exit(11);
	}
	
	private static final long serialVersionUID = 11L;
	
}
