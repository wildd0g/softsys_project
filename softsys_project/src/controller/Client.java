package controller;

import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import supportclasses.Receiver;
import supportclasses.Sender;
import view.ClientTUI;


import java.util.ArrayList;

public class Client {

	private static Socket sock;
	private static int id;
	public static String name;
	public static boolean connected;
	public static ClientTUI tui;
	public static Client client;
	public static Sender sender;
	private static Receiver receiver;
	private ClientGame currentGame = null;
	private ClientInput input;
	
	public static void main(String[] args) {
		connected  = false;
		
		client = new Client();
		tui = new ClientTUI(client);
		
		tui.start();
	}
	
	/**
	 * Establishing a connection to a server.
	 * @param simpleIP
	 * 				The ip adress of the connection, eiter by actual adress, symbolic name, or URL
	 * @param portNum
	 * 				The port number to connect on
	 */
	//@ requires 1000 <= portNum && portNum < 10000
	public void getConnected(String simpleIP, int portNum) {
		InetAddress address = null;

		//parse the given IP adress
		try {
			address = InetAddress.getByName(simpleIP);
		} catch (UnknownHostException unknown) {
			//TODO properly handle Exception
			unknown.getMessage();
		}

		//assign the selected port
		int port = portNum;


		try {
			sock = new Socket(address, port);
		} catch (IOException io1) {
			//TODO properly handle Exception
			io1.getMessage();
		}

		sender = new Sender(sock);
		receiver = new Receiver(sock);
		Thread streamInputHandler = new Thread(receiver);
        streamInputHandler.start();
        
        //autorefresh is set to false, can be changed if implemented
        sender.sendCapabilities(name, false);
        
        connected = true;

	}
	/**
	 * Returns the socket that the client is connected to 
	 * @return sock
	 * 				The socket
	 */
	//@ ensures \result == this.sock
	/*@pure@*/
	public Socket getSocket() {
		return sock;
	}
	
	/**
	 * Request the server sends the room list 
	 */
	/*@pure@*/
	public void requestRooms() {
		sender.getRoomList();
	}
	/**
	 * have the client join a room 
	 * @param roomID
	 * 				The ID of the room to join.
	 */
	/*@pure@*/
	public void joinRoom(int roomid) {
		sender.joinRoom(roomid);
	}

	/**
	 * Request the server to create a room with these specs
	 * @param players
	 * 				The number of player that can join the room
	 * @param xDim
	 * 				The size of the row dimension
	 * @param yDim
	 * 				The size of the column dimension
	 * @param zDim
	 * 				The size of the level dimension
	 * @param winLength
	 * 				The number of makrs you need to get in a row
	 */
	/*@pure@*/
	public void createRoom(int players, int xDim, int yDim, int zDim, int winLength) {
		sender.createRoom(players, xDim, yDim, zDim, winLength);
	}
	/**
	 * request the server to leave the room 
	 */
	/*@pure@*/
	public void leaveRoom() {
		sender.leaveRoom();
	}
	/**
	 * Send a move to the server. 
	 * @param xPos
	 * 				The row of the move
	 * @param yPos
	 * 				The column of the move
	 */
	/*@requires xPos < currentGame.getBoard().dimRow
	 * 		 && yPos < currentGame.getBoard().dimCol;
	 */	
	/*@pure@*/
	public static void makeMove(int xPos, int yPos) {
		sender.makeMove(xPos, yPos);
	}
	/**
	 * recieve and process a move from the server. 
	 * @param playerID
	 * 				The ID of the player that made the move
	 * @param xPos
	 * 				The row of the move
	 * @param yPos
	 * 				The column of the move
	 */
	/*@requires xPos < currentGame.getBoard().dimRow
	 * 		 && yPos < currentGame.getBoard().dimCol;
	 */	
	public void receiveMove(int playerID, int moveX, int moveY) {
		currentGame.notefiedMove(moveX, moveY, playerID);
	}
	
	/**
	 * Set the game the client is playing.
	 * @param game
	 * 				the game the client is now playing
	 */
	//@ensures this.currentGame = game;
	public void setGame(ClientGame game) {
		currentGame = game;
	}
	/**
	 * Returns the game the client is currently playing
	 * @return this.currentGame
	 * 							The game that is currently being played, null for not playing
	 */
	//@ensures \result == this.currentGame;
	/*@pure@*/
	public ClientGame getGame() {
		return currentGame;
	}
	
	/**
	 * Sets who's turn it currently is.
	 * @param playerID
	 * 					The player currently taking a turn, by ID
	 */
	//@ensures currentGame.currentTurn == playerID
	public void setTurn(int playerID) {
		currentGame.currentTurn = playerID;
		tui.printWrite("Wait for turn of: " + playerID);
		if (playerID == id) {
			tui.printWrite("That is you!");
			isTurn();
		}
	}
	
	/**
	 * Start the process for making a move.
	 */
	public void isTurn() {
		input.determineMove(currentGame);
	}
	
	/**
	 * Leave the current game because it is over
	 * @param condition
	 * 				String containing the reason for the ending
	 * @param winnerID
	 * 				The ID of reason for the end, either a player or 0 if non applicable
	 */		
	/*@pure*/
	public void endGame(String condition, int winnerID) {
		tui.printWrite("Game has ended because of " + winnerID);
		tui.printWrite(condition);
		currentGame = null;
	}
	/**
	 * Set the player ID of the client.
	 * @param setID
	 * 				The assigned client playerID
	 */
	//@ensures this.id == playerID
	public static void setID(int setID) {
		id = setID;
	}
	/**
	 * Set weather the client has a human player or is a bot.
	 * @param controling
	 * 					selecting the input method.
	 */
	public void setInput(String controling) {
		if (controling.equals("AI")) {
			input = new AIInput();
		} else if (controling.equals("HUMAN")) {
			input = new HumanInput();
		} else {
			tui.printWrite("Clearly you are a human, only humans make mistakes");
			input = new HumanInput();
		}
		
	}
	/**
	 * Returns the player Id of the client
	 * @return id
	 * 				The clients player ID
	 */
	//@ ensures \result == this.id
	public static int getID() {
		return id;
	}
	
	/**
	 * Stores the capabilities the server has, or it would, but it doesn't do anything with it at the moment.
	 * @param amountOfPlayers
	 * 						maximum amount of players
	 * @param roomSupport
	 * 						supports rooms or not
	 * @param maxRoomDimensionX
	 * 						maximum row size
	 * @param maxRoomDimensionY
	 * 						maximum column size
	 * @param maxRoomDimensionZ
	 * 						maximum level size
	 * @param maxLengthToWin
	 * 						maximum length to win
	 * @param chatSupport
	 * 						supports chat or not
	 */
	public static void receiveCapabilities(int amountOfPlayers,
			boolean roomSupport,
			int maxRoomDimensionX,
			int maxRoomDimensionY,
			int maxRoomDimensionZ,
			int maxLengthToWin,
			boolean chatSupport) {
		
		
		
	}
	
	/**
	 * Method that formats the printing of room information sent by server, and then prints it.
	 * @param rooms
	 * 				List with integer arrays containing all the room data points
	 */
	public static void printRooms(ArrayList<int[]> rooms) {
		String roomPrint = "";
		for (int i = 0; i < rooms.size(); i++) {
			tui.printWrite("\n");
			roomPrint = "Room ID: " + rooms.get(i)[0]
					+ "\tAmount of Players: " + rooms.get(i)[1]
					+ "\tRoom Width(X): " + rooms.get(i)[2]
					+ "\tRoom Depth(Y): " + rooms.get(i)[3]
					+ "\tRoom Height(Z): " + rooms.get(i)[4]
					+ "\tRoom Win Length: " + rooms.get(i)[5];
			tui.printWrite(roomPrint + "\n");
		}
	}
	/**
	 * Disconnect client and close all parsers, streams, etc.
	 */
	public static void shutDown() {
		System.out.println("starting shutdown protocol");
		tui.shutDown();
		if (connected) {
			sender.shutDown();
			receiver.shutDown();
			try {
				sock.close();
			} catch (IOException io1) {
				// TODO Auto-generated catch block
				io1.getMessage();
			}
		}
	}
	
	
}
