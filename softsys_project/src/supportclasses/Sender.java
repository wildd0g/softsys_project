package supportclasses;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import java.util.List;

import controller.ServerGame;

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
			e.printStackTrace();
		}
	}
	
	public void shutDown() {
		try {
			sender.close();
		} catch (IOException io1) {
			// TODO Auto-generated catch block
			io1.getMessage();
		}
	}
	
	public void sendServerCapabilities() {
		send(Capabilities.Server.get());
	}
	
	public void sendListRooms(List<ServerGame> gamesList) {
		String msg = Protocol.Server.SENDLISTROOMS;
		
		for(int i = 0; i < gamesList.size(); i++) {
			msg = msg + gamesList.get(i).getGame();
		}
		msg = msg + "\n";
		send(msg);
	}
	
	public void turn(int playerID) {
		String msg = Protocol.Server.TURNOFPLAYER + " " + playerID + "\n";
		send(msg);
	}
	
	public void error(int error) {
		String msg = Protocol.Server.ERROR + " " + error + "\n";
		send(msg);
	}
	
	public void assignID(int playerID) {
		String msg = Protocol.Server.ASSIGNID + " " + playerID + "\n";
		send(msg);
	}
	
	public void startGame(int[] roomData, String[][] players) {
		String msg = Protocol.Server.STARTGAME 
				+ " " + roomData[0]
				+ "|" + roomData[1]
				+ "|" + roomData[2]
				+ "|" + roomData[3];
		for (int i = 0; i < players.length; i++) {
			msg = msg 
				+ " " + players[i][0] 
				+ "|" + players[i][1] 
				+ "|" + players[i][2];
		}
		msg = msg + "\n";
		send(msg);
	}

	public void notifyMove(int playerID, int x, int y) {
		String msg = Protocol.Server.NOTIFYMOVE + " " +
					playerID + " " +
					x + " " + y + "\n";
		send(msg);
	}

	public void notifyEnd(int endCondition, int playerID) {
		String msg = Protocol.Server.NOTIFYEND + " " + endCondition;
		if (endCondition == 1 || endCondition == 3) {
			msg = msg + " " + playerID + "\n";
		}
		send(msg);
	}
	
	public void notifyMessage(String name, String message) {
		String msg = Protocol.Server.NOTIFYMESSAGE + " " + name + " " + message + "\n";
		send(msg);
	}

	public void sendLeaderboard() {
		//TODO send the leaderboard.
		//currently leaving it out.
		String msg = Protocol.Server.SENDLEADERBOARD + "\n";
	}
	
	public void sendCapabilities(String name, boolean autoRefresh) {
		send(Capabilities.Client.get(name, autoRefresh));
	}
	
	public void joinRoom(int roomID) {
		String msg = Protocol.Client.JOINROOM + " " + roomID + "\n";
		send(msg);
	}
	
	public void createRoom(int players, int dimX, int dimY, int dimZ, int winL){
		String msg = Protocol.Client.CREATEROOM 
				+ " " + players
				+ " " + dimX 
				+ " " + dimY
				+ " " + dimZ
				+ " " + winL
				+ "\n";
		send(msg);
	}
	
	public void roomCreated(int roomID) {
		String msg = Protocol.Server.ROOMCREATED + " " + roomID;
		send(msg);
	}
	
	public void getRoomList() {
		send(Protocol.Client.GETROOMLIST + "\n");
	}
	
	public void leaveRoom() {
		send(Protocol.Client.LEAVEROOM + "\n");
	}
	
	public void makeMove(int x, int y) {
		String msg = Protocol.Client.MAKEMOVE + " " + x + " " + y + "\n";
		send(msg);
	}
	
	public void sendMessage(String message) {
		String msg = Protocol.Client.SENDMESSAGE + " " + message + "\n";
		send(msg);
	}
	
	public void requestLeaderboard() {
		send(Protocol.Client.REQUESTLEADERBOARD + "\n");
	}
}
