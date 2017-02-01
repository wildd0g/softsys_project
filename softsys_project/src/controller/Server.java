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
	private static List<ServerGame> availableGames = new ArrayList<>();
	private static Map<ServerGame, Set<Player>> games = new HashMap<>();
	private static Map<Integer, ServerGame> activeGames = new HashMap<Integer, ServerGame>();

	private static boolean receiving = true;
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
	/**
	 * Stop and remove a given game.
	 * @param stopGame
	 * 					the game to stop and remove
	 */
	public static void removeGame(ServerGame stopGame) {
		activeGames.remove(stopGame);
		games.remove(stopGame.gameID);
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
			serverSocket.setReuseAddress(true);
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
	/**
	 * Create a new player after accepting a socket connection.
	 * @param sock
	 * 			the player's socket
	 */
	public static synchronized void connectNew(Socket sock) {
		
		//player object is created with this as reference. 
		//It is added to the list of players not yet in a game and is activated
		Player newPlayer = 
				new Player(playerCounter, "player" + playerCounter, registrationSocket);
		nonPlaying.add(newPlayer);
		nonPlaying.get(nonPlaying.size() - 1).activate();
		
		playerCounter++;
		
		newPlayer.send.sendServerCapabilities();
		
	}
	
	
	/**
	 * Methods utilized by parser.
	 * @param player
	 * 				player object to set capabilities of
	 * @param amountOfPlayers
	 * 				maximum amount of players this player can handle
	 * @param name
	 * 				name of the player
	 * @param maxDimX
	 * 				maximum row size
	 * @param maxDimY
	 * 				maximum column size
	 * @param maxDimZ
	 * 				maximum level size
	 * @param maxWin
	 * 				maximum win length
	 * @param chat
	 * 				supports chat or not
	 * @param refresh
	 * 				wants to auto refresh the lobby listing, though we can't do that
	 */
	//method that sets capabilities of a player
	public synchronized static void setCapabilities(Player player,
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
		
		if (refresh) {
			player.send.sendListRooms(getRooms());
		} else {
			boolean foundRoom = false;
			Iterator<ServerGame> itt = availableGames.iterator();
			for (int i = 0; i < availableGames.size(); i++) {
				ServerGame thisGame = itt.next();
				if (thisGame.defaultGame) {
					joinToRoom(player, thisGame.gameID);
					foundRoom = true;
					break;
				}
			}
			if (!foundRoom) {
				int newRoom = createNew(2, 4, 4, 4, 4);
				joinToRoom(player, newRoom);
			}
		}

	}

	/**
	 * Method that puts the player into a new game.
	 * @param player
	 * 				Player object of player to move
	 * @param game
	 * 				Game to move to
	 */
	//@ requires availableGames.contains(game);
	public synchronized static void joinToRoom(Player player, int game) {
		if (!activeGames.containsKey(game)) {
			player.send.error(2);
		} else {
			ServerGame servGame = activeGames.get(game);
			Set<Player> playersInGame = games.get(servGame); 

			if (servGame.players.length > games.get(servGame).size()) {

				servGame.addPlayer(player);
				playersInGame.add(player);

				player.setGame(servGame);
				player.send.assignID(player.getID());

				games.put(servGame, playersInGame);

				if (servGame.players.length == games.get(servGame).size()) {
					availableGames.remove(servGame);
					servGame.serverStartGame();
				}
				nonPlaying.remove(player);
			} else {
				player.send.error(3);
				player.send.sendListRooms(getRooms());
			}
		}
	}  
	
	/**
	 * Method that retrieves the list of all present games.
	 * @return availableGames
	 * 				All the available games in list format
	 */
	/*@pure*/
	public static List<ServerGame> getRooms() {
		return availableGames;
	}

	//method that retrieves the list of all players not in games
	public static List<Player> getNonPlaying() {
		return nonPlaying;
	}
	
	public static List<Player> getRoomMates(Player player) {
		List<Player> roomMates = new ArrayList<Player>();
		if (player.getGame() == null) {
			roomMates = controller.Server.getNonPlaying();	
		} else {
			Set<Player> playerSet = games.get(player); 
			roomMates.addAll(playerSet); 
		}
		return roomMates;
	}

	//method that creates a new game 
	public synchronized static int createNew(
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
		availableGames.add(newGame);

		gamesCounter++;
		
		return gameID;

	}
	
	public synchronized static void leaveRoom(Player player) {
		nonPlaying.add(player);
		player.send.sendListRooms(getRooms());
	}

	//method that processes move made by a new player
	public static void processMove(Player player, int moveX, int moveY) {

		player.getGame().makeMove(moveX, moveY, player.getID());

	}






}
