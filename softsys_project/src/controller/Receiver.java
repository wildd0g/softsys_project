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
	
	public static void main(String[] args) {
		while (active) {
			
			boolean readable = false;
			
			try {
				readable = buffer.ready();
			} catch (IOException io2) {
				io2.getMessage();
			}
			
			if(readable){ 
				
			}
		}
	}
	
	private synchronized void read() {
		try {
			
		}
	}

}
