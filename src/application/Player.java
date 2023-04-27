package application;

import java.util.HashMap;
import java.util.TreeSet;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    private int points;
    private int playerNum;
    private String color;
    private int settlementNum;
    private TreeSet<HexNode> locTiles;
    private Image settlementImg;

    public Player(int num, String color) {
        points = 0;
        playerNum = num;
        this.color = color;
        settlementNum = 40;
        locTiles = new TreeSet<>();
        
        try {
        	settlementImg = new Image(getClass().getResourceAsStream("/images/" + color.toLowerCase() + "Settlement.png"));
        } catch(NullPointerException e) {
        	settlementImg = new Image(getClass().getResourceAsStream("/images/blueSettlement.png"));
        }
    }
    
    public Image getSettlementImg() {return settlementImg;}
    public int getSettlementNum() {return settlementNum;}
    public int getPlayerNum() {return playerNum;}
    
    public void startTurn() {
    	GUI gui = GUI.get();
    	ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();
    	
    	moveSelectionList.clear();
    	Button addSettlements = new Button();
    	addSettlements.setPrefSize(60, 60);
    	
    	ImageView settlementImg = new ImageView(getSettlementImg());
    	settlementImg.setFitWidth(60);
    	settlementImg.setFitHeight(40);
    	addSettlements.setGraphic(settlementImg);
    	
    	addSettlements.setOnAction(e -> {    
    		Board board = Board.get();
    		
    		gui.setConfirmButtonsVisible(true);
    		
    		TerrainCard card = board.getActiveCard();
    		if(!card.isActive()) {
    			card.activateCard();
    		}
    	});
    	moveSelectionList.add(addSettlements);
    }
    
    public void addSettlement() {settlementNum++;}
    public void removeSettlement() {settlementNum--;}
}
