package supportclasses;

//listing of all the current capabilities of the program, both client and server

public class Capabilities {

	//server listing
	public static class Server {
		public static final String AMOUNTOFPLAYERS = "8";			//supports this many players
		public static final String ROOMSUPPORT = "1";				//supports rooms, 0 or 1
		public static final String MAXDIMENSIONS = "" + 			//dimensions bound because
					((Double) Math.floor(
					Math.cbrt(Integer.MAX_VALUE - 5))).intValue();  //int in board[int]	 
		public static final String MAXDIMENSIONX = MAXDIMENSIONS;	//has a maximum.
		public static final String MAXDIMENSIONY = MAXDIMENSIONS;	//Dimension maximum
		public static final String MAXDIMENSIONZ = MAXDIMENSIONS;	//becomes the cube root of this.
		public static final String LENGTHTOWIN = MAXDIMENSIONS;		//^^
		public static final String CHATSUPPORT = "1";				//supports chat, 0 or 1

		//Generates pre-formated string for the serverCapabilities message,
		public static String get() {
			return  Protocol.Server.SERVERCAPABILITIES + " " + 
					AMOUNTOFPLAYERS + " " + 
					ROOMSUPPORT + " " +  
					MAXDIMENSIONX + " " + 
					MAXDIMENSIONY + " " + 
					MAXDIMENSIONZ + " " + 
					LENGTHTOWIN + " " + 
					CHATSUPPORT + "\n";  
		}
	}

	//client listing
	public static class Client {
		public static final String AMOUNTOFPLAYERS = "8";			//supports this many players
		public static final String ROOMSUPPORT = "1";				//supports rooms, 0 or 1
		public static final String MAXDIMENSIONS = "" + 			//dimensions bound because
				((Double) Math.floor(
				Math.cbrt(Integer.MAX_VALUE - 5))).intValue();		//int in board[int]	 
		public static final String MAXDIMENSIONX = MAXDIMENSIONS;	//has a maximum.
		public static final String MAXDIMENSIONY = MAXDIMENSIONS;	//Dimension maximum
		public static final String MAXDIMENSIONZ = MAXDIMENSIONS;	//becomes the cube root of this.
		public static final String LENGTHTOWIN = MAXDIMENSIONS;		//^^
		public static final String CHATSUPPORT = "0";				//supports chat, 0 or 1

		//Generates pre-formated string for the sendCapabilities message, 
		public static String get(String name, boolean autoRefresh) {
			String refreshString = "";
			if (autoRefresh) {
				refreshString = refreshString + 1;
			} else {
				refreshString = refreshString + 0;
			}
			return 	Protocol.Client.SENDCAPABILITIES + " " + 
					AMOUNTOFPLAYERS + " " + 
					name + " " +
					ROOMSUPPORT + " " + 
					MAXDIMENSIONX + " " + 
					MAXDIMENSIONY + " " + 
					MAXDIMENSIONZ + " " + 
					LENGTHTOWIN + " " + 
					CHATSUPPORT + " " +  
					refreshString + "\n";					
		}
	}

}
