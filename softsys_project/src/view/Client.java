package view;

import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.Sender;

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
	private ClientTUI tui;
	public Sender send;

	//TODO create declaration for start of TUI

	public Client() {
		connected = false;
		tui = new ClientTUI(this);
		tui.start();
	}

	public static void main(String[] args){

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

		//possibly replace with Sender
		try {
			reader = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			writer = new BufferedWriter(
					new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException io2) {
			//TODO properly handle Exception
			io2.getMessage();
		}

		connected = true;

		while (connected) {

		}

	}

	public void requestRooms() {

	}
	
	public void joinRoom(int id) {
		
	}

	public void createRoom(int players, int xDim, int yDim, int zDim, int winLength) {

	}
	
	public void leaveRoom() {
		
		
	}
	
	public void makeMove(int xPos, int yPos) {
		String send = "makeMove " + xPos + " " + yPos;
	}
}
