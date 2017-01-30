package controller;

public class HumanInput extends ClientInput {
	
	@Override
	public void determineMove(ClientGame game) {
		Client.tui.moveInfo();
	}

}
