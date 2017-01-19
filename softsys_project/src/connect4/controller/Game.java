package connect4.controller;

public class Game implements Runnable{
	
	public int currentPlaying = 0;
	public Player[] players;
	public int gameID;
	
	public Game(int id, int playerNum, int dimX, int dimY, int dimZ, int winLength){
		players = new Player[playerNum];
		this.gameID = id;
	}
	
	//method that adds a player to this room
	//action for command joinRoom
	public void addPlayer(Player newPlayer){
		players[currentPlaying] = newPlayer; 
		currentPlaying = currentPlaying + 1;
	}
	
	//method that removes a player from the room
	//action for command leaveRoom
	public void removePlayer(Player gonePlayer){
		//for loop browses through the list of players logged in this room 
		//(no need for those who added later because they can't remove themselves until after they've been added)
		for(int i = 0; i <= currentPlaying; i++){
			//if the specified player is found remove it and set currentPlaying to one less
			if(players[i].equals(gonePlayer)){
				players[i] = null;
				currentPlaying = currentPlaying - 1;
				break;
			} 
		}
	}
	
	//run method to allow communication with the clients
	public void run(){
		
	}
	
	

}
