package controller;

import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import supportclasses.Receiver;
import supportclasses.Sender;
import view.ClientTUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.ArrayList;

public class Client {

	private static Socket sock;
	private static int id;
	public static String name;
	public static boolean connected;
	private BufferedReader reader;
	private BufferedWriter writer;
	public static ClientTUI tui;
	public static Client client;
	public static Sender sender;
	private static Receiver receiver;
	private ClientGame currentGame;
	private ClientInput input;
	
	public static void main(String[] args) {
		connected  = false;
		
		client = new Client();
		tui = new ClientTUI(client);
		
		tui.start();
	}

	public void getConnected(String simpleIP, int portNum) {
		InetAddress address = null;

		//TODO ask via TUI for input

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
	
	public Socket getSocket() {
		return sock;
	}
	
	public void requestRooms() {
		sender.getRoomList();
	}
	
	public void joinRoom(int roomid) {
		sender.joinRoom(roomid);
	}

	public void createRoom(int players, int xDim, int yDim, int zDim, int winLength) {
		sender.createRoom(players, xDim, yDim, zDim, winLength);
	}
	
	public void leaveRoom() {
		sender.leaveRoom();
	}
	
	public static void makeMove(int xPos, int yPos) {
		sender.makeMove(xPos, yPos);
	}
	
	public void setGame(ClientGame game) {
		currentGame = game;
	}
	
	public void setTurn(int playerID) {
		currentGame.currentTurn = playerID;
		if (playerID == id) {
			isTurn();
		}
	}
	
	public void isTurn() {
		input.determineMove(currentGame);
	}
	
	public static void setID(int setID) {
		id = setID;
	}
	
	public static int getID() {
		return id;
	}
	
	public static void receiveCapabilities(int amountOfPlayers,
			boolean roomSupport,
			int maxRoomDimensionX,
			int maxRoomDimensionY,
			int maxRoomDimensionZ,
			int maxLengthToWin,
			boolean chatSupport) {
		
		
		
	}
	
	//method that formats the printing of room information sent by server
	public static void printRooms(ArrayList<int[]> rooms) {
		String roomPrint = "";
		for (int i = 0; i < rooms.size(); i++) {
			roomPrint = "Room ID: " + rooms.get(i)[0]
					+ "Amount of Players: " + rooms.get(i)[1]
					+ "Room Width(X): " + rooms.get(i)[2]
					+ "Room Depth(Y): " + rooms.get(i)[3]
					+ "Room Height(Z): " + rooms.get(i)[4]
					+ "Room Win Length: " + rooms.get(i)[5];
			tui.printWrite(roomPrint);
		}
	}
	
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
