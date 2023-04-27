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
	private static final String[] settlementColors = {"RED", "BLUE", "BROWN", "YELLOW"};//, "GREEN", "PURPLE", "ORANGE", "PINK"};
	private FadeTransition fade;
	
	public TurnHandler() {
		playerList = new ArrayList<>();
		playerAmt = 4;
		
		/*
		Rectangle fadeRectangle = new Rectangle();
		fadeRectangle.setWidth(1920);
		fadeRectangle.setHeight(1080);
		fadeRectangle.setOpacity(0);
		
		fade = new FadeTransition(Duration.millis(3000), fadeRectangle);
	    fade.setFromValue(1.0);
	    fade.setToValue(0.3);
	    fade.setCycleCount(4);
	    fade.setAutoReverse(true);
	    
	    ObjectHandler objectHandler = ObjectHandler.get();
	    objectHandler.add(fadeRectangle);
	 
	    //ft.play();
	     */
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
		gui.setPlayerLabelText("Player " + (getCurrentPlayer().getPlayerNum() + 1));
		//fade.play();
	}
	
	public FadeTransition getFadeTransition() {return fade;}
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
	
	public void startTurns() {
		playerIter.next().startTurn();
		playerIter.previous();
	}
	
	public static TurnHandler get() {
		if(TurnHandler.turnHandler == null) {
			TurnHandler.turnHandler = new TurnHandler();
		}
		return TurnHandler.turnHandler;
	}
}
