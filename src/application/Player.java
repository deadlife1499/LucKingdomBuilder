package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.sun.tools.javac.Main;

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
    private Image[] settlementImgArr;
    private Image settlementIcon;

    public Player(int num, String color) {
        points = 0;
        playerNum = num;
        this.color = color;
        settlementNum = 40;
        locTiles = new TreeSet<>();
        settlementImgArr = new Image[4];
        settlementIcon = new Image(getClass().getResourceAsStream("/images/" + color + ".png"));
        
        for(int i = 1; i < 5; i++) {
        	settlementImgArr[i - 1] = new Image(getClass().getResourceAsStream("/images/" + color + i + ".png"));
        }
    }
    
    public Image getSettlementIcon() {return settlementIcon;}
    public Image getSettlementImg() {
    	List<Image> tempList = Arrays.asList(settlementImgArr);
    	Collections.shuffle(tempList);
    	
    	return tempList.get(0);
    }
    public int getSettlementNum() {return settlementNum;}
    public int getPlayerNum() {return playerNum;}
    
    public void startTurn() {
    	GUI gui = GUI.get();
    	ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();
    	
    	moveSelectionList.clear();
    	Button addSettlements = new Button();
    	
    	ImageView settlementImg = new ImageView(settlementIcon);
    	settlementImg.setFitHeight(52);
    	settlementImg.setPreserveRatio(true);
    	
    	addSettlements.setPrefSize(settlementImg.getFitWidth(), settlementImg.getFitHeight());
    	addSettlements.setGraphic(settlementImg);
    	
    	addSettlements.setOnAction(e -> {    
    		Board board = Board.get();
    		
    		gui.setCancelButtonDisable(false);
    		
    		TerrainCard card = board.getActiveCard();
    		if(!card.isActive()) {
    			card.activateCard();
    		}
    	});
    	moveSelectionList.add(addSettlements);
    }
    
    public void checkAdjacent() {
    	
    }
    
    public void addSettlement() {settlementNum++;}
    public void removeSettlement() {settlementNum--;}
}
