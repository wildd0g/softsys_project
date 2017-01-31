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

public class Client {

	private static Socket sock;
	private static int id;
	private static String name;
	private static boolean connected;
	private BufferedReader reader;
	private BufferedWriter writer;
	public static ClientTUI tui;
	public static Sender sender;
	private static Receiver receiver;
	private ClientGame currentGame;
	private ClientInput input;

	//TODO create declaration for start of TUI

	public Client() {
		connected = false;
		tui = new ClientTUI(this);
	}
	
	public static void main(String[] args) {
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
	
	public void setID(int setID) {
		id = setID;
	}
	
	public static int getID() {
		return id;
	}
	
	public static void shutDown() {
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
