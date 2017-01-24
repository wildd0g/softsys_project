package controller;

public class InvalidPipedDataException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidPipedDataException(String msg, String pipedData, Player player) {
		this(msg, pipedData);
		//TODO: send error 7
	}
	
	public InvalidPipedDataException(String msg, String pipedData) {
		super("error 7: the piped data recieved did not conform to the protocoll.\n" 
				+ "line: " + msg + "\n"
				+ "Error in: " + pipedData);
		System.out.println("error 7 generated: faulty command recieved: " + msg + "\n"
				+ "error in: " + pipedData + "\n"
				+ "sending error 7");
	}
	
	
	public InvalidPipedDataException(String errorMessage) {
		super(errorMessage);
	}

}
