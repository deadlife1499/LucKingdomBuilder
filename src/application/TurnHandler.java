package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import javafx.scene.paint.Color;

public class TurnHandler {
	private ArrayList<Player> playerList;
	private ListIterator<Player> playerIter;
	private int playerAmt;
	private static TurnHandler turnHandler;
	private static final String[] settlementColors = {"RED", "BLUE", "BROWN", "YELLOW"};//, "GREEN", "PURPLE", "ORANGE", "PINK"};
	
	public TurnHandler() {
		playerList = new ArrayList<>();
		playerAmt = 4;
	}
	
	public void nextTurn() {
		Player player = playerIter.next();
		player.startTurn();
		
		if(!playerIter.hasNext()) {
			while(playerIter.hasPrevious()) {
				playerIter.previous();
			}
		}
	}
	
	public Player getCurrentPlayer() {
		playerIter.next();
		
		return playerIter.previous();
	}
	
	public void setPlayers(String[] colors) {
		for(int i = 0; i < playerAmt; i++) {
			Player player = new Player(i, colors[i]);
			playerList.add(player);
		}
		Collections.shuffle(playerList);
		playerIter = playerList.listIterator();
	}
	
	public static TurnHandler get() {
		if(TurnHandler.turnHandler == null) {
			TurnHandler.turnHandler = new TurnHandler();
		}
		return TurnHandler.turnHandler;
	}
}
