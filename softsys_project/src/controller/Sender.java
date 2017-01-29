package controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;



public class Sender {

	protected Socket playerSocket;
	protected OutputStreamWriter sender;
	
	public Sender(Socket sock) {
		playerSocket = sock;
		try {
			sender = new OutputStreamWriter(sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void send(String msg) {
		try {
			sender.write(msg);
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendServerCapabilities() {
		send(Capabilities.Server.get());
	}
	
	public void sendListRooms() {
		//TODO send the list of all rooms.
		String msg = Protocol.Server.SENDLISTROOMS;
	}
	
	public void turn(int playerID) {
		String msg = Protocol.Server.TURNOFPLAYER + " " + playerID + "/n";
		send(msg);
	}
	
	public void error(int error) {
		String msg = Protocol.Server.ERROR + " " + error + "/n";
		send(msg);
	}
	
	public void assignID(int playerID) {
		String msg = Protocol.Server.ASSIGNID + " " + playerID + "/n";
		send(msg);
	}
	
	public void startGame(int[] roomData, String[][] players) {
		String msg = Protocol.Server.STARTGAME +
				roomData[0] + "|" +
				roomData[1] + "|" +
				roomData[2] + "|" +
				roomData[3] + "|";
		for (int i = 0; i < players.length; i++) {
			msg = msg + players[i][0] +
					players[i][1] +
					players[i][2];
		}
		send(msg);
	}

	public void notifyMove(int playerID, int x, int y) {
		String msg = Protocol.Server.NOTIFYMOVE + " " +
					playerID + " " +
					x + " " + y + "/n";
		send(msg);
	}

	public void notifyEnd(int endCondition, int playerID) {
		String msg = Protocol.Server.NOTIFYEND + " " + endCondition;
		if (endCondition == 1 || endCondition == 3) {
			msg = msg + " " + playerID;
		}
		send(msg);
	}
	
	public void notifyMessage(String name, String message) {
		String msg = Protocol.Server.NOTIFYMESSAGE + " " + name + " " + message + "/n";
		send(msg);
	}

	public void sendLeaderboard() {
		//TODO send the leaderboard.
		String msg = Protocol.Server.SENDLEADERBOARD;
	}
	
	public void sendCapabilities(String name, boolean autoRefresh) {
		send(Capabilities.Client.get(name, autoRefresh));
	}
	
	public void joinRoom(int roomID) {
		String msg = Protocol.Client.JOINROOM + " " + roomID + "/n";
		send(msg);
	}
	
	public void createRoom(int players, int dimX, int dimY, int dimZ, int winL){
		String msg = Protocol.Client.CREATEROOM 
				+ " " + players
				+ " " + dimX 
				+ " " + dimY
				+ " " + dimZ
				+ " " + winL;
	}
	
	public void getRoomList() {
		send(Protocol.Client.GETROOMLIST + "/n");
	}
	
	public void leaveRoom() {
		send(Protocol.Client.LEAVEROOM + "/n");
	}
	
	public void makeMove(int x, int y) {
		String msg = Protocol.Client.MAKEMOVE + " " + x + " " + y + "/n";
		send(msg);
	}
	
	public void sendMessage(String message) {
		String msg = Protocol.Client.SENDMESSAGE + " " + message + "/n";
		send(msg);
	}
	
	public void requestLeaderboard() {
		send(Protocol.Client.REQUESTLEADERBOARD + "/n");
	}
}
