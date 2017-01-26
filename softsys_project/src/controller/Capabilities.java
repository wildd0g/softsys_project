package controller;

//listing of all the current capabilities of the program, both client and server

public class Capabilities {

	//server listing
	public static class Server {
		public static final String AMMOUNTOFPLAYERS = "8";			//supports this many players
		public static final String ROOMSUPPORT = "0";				//supports rooms, 0 or 1
		public static final String MAXDIMENSIONS = 					//dimensions bound because
				"" + Math.floor(Math.cbrt(Integer.MAX_VALUE - 5));	//the int in board[int] 
		public static final String MAXDIMENSIONX = MAXDIMENSIONS;	//has a maximum.
		public static final String MAXDIMENSIONY = MAXDIMENSIONS;	//Dimension maximum
		public static final String MAXDIMENSIONZ = MAXDIMENSIONS;	//becomes the cube root of this.
		public static final String LENGTHTOWIN = MAXDIMENSIONS;		//^^
		public static final String CHATSUPPORT = "0";				//supports chat, 0 or 1

		//Generates pre-formated string for the serverCapabilities message,
		public static String get() {
			return  Protocol.Server.SERVERCAPABILITIES + " " + 
					AMMOUNTOFPLAYERS + " " + 
					ROOMSUPPORT + " " + 
					MAXDIMENSIONS + " " + 
					MAXDIMENSIONX + " " + 
					MAXDIMENSIONY + " " + 
					MAXDIMENSIONZ + " " + 
					LENGTHTOWIN + " " + 
					CHATSUPPORT;  
		}
	}

	//client listing
	public static class Client {
		public static final String AMMOUNTOFPLAYERS = "8";			//supports this many players
		public static final String ROOMSUPPORT = "0";				//supports rooms, 0 or 1
		public static final String MAXDIMENSIONS = 					//dimensions bound because
				"" + Math.floor(Math.cbrt(Integer.MAX_VALUE - 5));	//the int in board[int] 
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
					AMMOUNTOFPLAYERS + " " + 
					ROOMSUPPORT + " " + 
					MAXDIMENSIONS + " " + 
					MAXDIMENSIONX + " " + 
					MAXDIMENSIONY + " " + 
					MAXDIMENSIONZ + " " + 
					LENGTHTOWIN + " " + 
					CHATSUPPORT + " " +  
					refreshString;					
		}
	}

}
