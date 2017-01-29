package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;

public class Receiver {
	
	protected Socket socket;
	protected InputStreamReader receiver;
	protected static BufferedReader buffer;
	public static boolean active;
	private static Parser parser;
	
	public Receiver(Socket sock) {
		
		socket = sock;
		
		try {
			receiver = new InputStreamReader(sock.getInputStream());
		} catch (IOException io1) {
			//TODO properly handle Exception
			io1.getMessage();
		}
		
		buffer = new BufferedReader(receiver);
		
		active = true;
		
	}
	
	//main method looping action to continually read input
	public static void main(String[] args) {
		while (active) {
			
			boolean readable = false;
			
			try {
				readable = buffer.ready();
			} catch (IOException io2) {
				io2.getMessage();
			}
			
			if(readable){ 
				try {
					parser.parse(null, buffer.readLine());
				} catch (IOException io3) {
					//properly handle Exception
					io3.getMessage();
				}
			}
			
		}
	}

}
