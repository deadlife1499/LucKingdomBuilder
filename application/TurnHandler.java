package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TurnHandler {
	private ArrayList<Player> playerList;
	private ListIterator<Player> playerIter;
	private int playerAmt;
	private static TurnHandler turnHandler;
	
	public TurnHandler() {
		playerList = new ArrayList<>();
		playerAmt = 4;
	}
	
	public void nextTurn() {
		playerIter.next();
		
		if(!playerIter.hasNext()) {
			while(playerIter.hasPrevious()) {
				playerIter.previous();
			}
		}
		getCurrentPlayer().startTurn();
		
		GUI gui = GUI.get();
		Board board = Board.get();
		
		gui.setPlayerLabelText("Player " + (getCurrentPlayer().getPlayerNum() + 1));
		board.drawTerrainCard();
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
		//Collections.shuffle(playerList);
		playerIter = playerList.listIterator();
	}
	
	public static TurnHandler get() {
		if(TurnHandler.turnHandler == null) {
			TurnHandler.turnHandler = new TurnHandler();
		}
		return TurnHandler.turnHandler;
	}
}
