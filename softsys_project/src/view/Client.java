package view;

import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Client {
	
	private static Socket sock;
	private static int id;
	private static boolean connected;
	private BufferedReader reader;
	private BufferedWriter writer;
	private ClientTUI tui;
	
	//TODO create declaration for start of TUI
	
	public Client () {
		connected = false;
		tui = new ClientTUI(this);
				
	}
	
	public static void main(String[] args) {
		
		getConnected();
		
		while(connected) {
			
		}
		
	}
		
	public void getConnected() {
		InetAddress address = null;
		
		//TODO ask via TUI for input
		//tempvalues
		String simpleIP = null;
		int portNum = 0;
		
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
		
	}

}
