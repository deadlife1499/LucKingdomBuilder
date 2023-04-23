package application;

import java.util.TreeSet;
import javafx.scene.image.Image;

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
        settlementImg = new Image(getClass().getResourceAsStream("/images/" + color.toLowerCase() + "Settlement.png"));
    }
    
    public Image getSettlementImg() {
    	return settlementImg;
    }
    
    public void startTurn() {
    	
    }
    
    public int getSettlementNum() {return settlementNum;}
    
    public void addSettlement() {settlementNum++;}
    public void removeSettlement() {settlementNum--;}
}
