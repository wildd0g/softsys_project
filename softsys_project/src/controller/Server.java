package controller;

import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import view.ServerTUI;

import supportclasses.*;

public class Server {
	
	private static List<Player> nonPlaying = new ArrayList<>();
	private static Map<ServerGame, Set<Player>> games = new HashMap<>();
	private static Map<Integer, ServerGame> activeGames = new HashMap<Integer, ServerGame>();

	private static boolean receiving = true;
	private static boolean active = true;
	private static Socket registrationSocket = null;
	private static ServerSocket serverSocket = null;
	private static int playerCounter = 0;
	private static int gamesCounter = 0;
	private static Server server;
	private static ServerTUI tui;
	
	//method to neatly shut down server
	//closes all games
	//disconnects all Clients
	public void serverShutDown() {
		receiving  = false;
	}
	
	public static void main(String[] args) {
		
		server = new Server();
		
		tui = new ServerTUI(server);
			
		int port = 1212;
		
		try {
			port = tui.start();
		} catch (MaliciousInputException mie1) {
			System.exit(11);
		}
		
		
		//assign server socket at listening port
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException io) {
			System.out.println("Couldn't create a socket on this port");
			System.out.println(io.getMessage());
		}
		
		//While loop to accept new clients wanting to link to the server
		while (receiving) {
			
			try {
				
				//socket reference is loaded with new attached socket
				registrationSocket = serverSocket.accept();
				
				connectNew(registrationSocket);
								
			} catch (IOException io1) {
				//TODO handle exception properly
				io1.getMessage();
			}
		}
		
	}
	
	public static synchronized void connectNew(Socket sock) {
		
		//player object is created with this as reference. 
		//It is added to the list of players not yet in a game and is activated
		Player newPlayer = 
				new Player(playerCounter, "player" + playerCounter, registrationSocket);
		nonPlaying.add(newPlayer);
		nonPlaying.get(nonPlaying.size() - 1).activate();
		
	}
	
	/**
	 * methods utilised by parser
	 */
	
	//method that sets capabilities of a player
	public static void setCapabilities(Player player,
			int amountOfPlayers, 
			String name, 
			int maxDimX,
			int maxDimY,
			int maxDimZ,
			int maxWin,
			boolean chat,
			boolean refresh) {
		
		//use player function to set capabilities
		player.setCapabilities(
				amountOfPlayers, 
				name, 
				maxDimX, 
				maxDimY, 
				maxDimZ, 
				maxWin, 
				chat, 
				refresh);
		if(refresh){
			player.send.sendListRooms(getRooms());
		} else {
			boolean foundRoom = false;
			Iterator<ServerGame> itt = games.keySet().iterator();
			for(int i = 0; i < games.keySet().size(); i++){
				ServerGame thisGame = itt.next();
				if(thisGame.defaultGame){
					thisGame.addPlayer(player);
					foundRoom = true;
					break;
				}
			}
			if (!foundRoom){
				int newRoom = createNew(2, 4, 4, 4, 4);
				activeGames.get(newRoom).addPlayer(player);
				player.send.assignID(player.getID());
			}
		}
		
	}

	//method that puts the player into a new game
	public static void joinToRoom(Player player, int game) {
		ServerGame servGame = activeGames.get(game);
		Set<Player> playersInGame = games.get(servGame); 
		
		if (servGame.players.length > games.get(servGame).size()) {
			servGame.addPlayer(player);
			playersInGame.add(player);
			games.put(servGame, playersInGame);
		} else {
			player.send.error(3);
			player.send.sendListRooms(getRooms());
		}
		
	}  
	
	//method that retrieves the list of all present games
	public static List<ServerGame> getRooms() {
		//TODO change to only available games
		List<ServerGame> returnList = new ArrayList<>(); 
		returnList.addAll(games.keySet());
		return returnList;
	}
	
	//method that creates a new game 
	public static int createNew(
			int amountOfPlayers, 
			int maxRoomDimensionX,
			int maxRoomDimensionY,
			int maxRoomDimensionZ,
			int maxLengthToWin) {

		int gameID = gamesCounter;
		ServerGame newGame = new ServerGame(
				gameID,
				amountOfPlayers,
				maxRoomDimensionX,
				maxRoomDimensionY,
				maxRoomDimensionZ,
				maxLengthToWin);

		activeGames.put(gameID, newGame);
		games.put(newGame, new HashSet<Player>());

		gamesCounter++;
		
		return gameID;

	}
	
	public static void leaveRoom(Player player) {
		nonPlaying.add(player);
		player.send.sendListRooms(getRooms());
	}

	//method that processes move made by a new player
	public static void processMove(Player player, int moveX, int moveY) {

		ServerGame tempGame = (ServerGame) player.getGame();
		tempGame.makeMove(moveX, moveY, player.getID());

	}






}
