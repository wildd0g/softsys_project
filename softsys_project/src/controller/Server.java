package controller;

//import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class Server {
	
	List<Player> nonPlaying = new ArrayList<>();
	Map<Game, Player> games = new HashMap<Game, Player>();

	//method to send a message from Server to Client
	public void sendString(String message) {

	}

	//method to broadcast a message from Server to Clients in one game
	public void sendAllString(String message) {

	}



	//method to receive message from Client as Server
	public void receiveString() {

	}

	//method that utilises protocol to translate received message into action
	public void processString() {

	}



}
