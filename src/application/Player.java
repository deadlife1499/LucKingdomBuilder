package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import application.ActionTile.ActionTileTemplate;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Player {
    private int score;
    private int playerNum;
    private int settlementNum;
    private Image[] settlementImgArr;
    private Image settlementIcon;
    private ArrayList<ActionTile> actionTileList;
    private Label scoreLabel;
    private int tempScore;
    private boolean turnConfirmed;
    private boolean usedActionTile;
    private Image firstPlayerToken;
    private static GameObject firstPlayerObj;

    public Player(int num, String color) {
        score = 0;
        playerNum = num;
        settlementNum = 40;
        settlementImgArr = new Image[4];
        settlementIcon = new Image(getClass().getResourceAsStream("/images/" + color + ".png"));
        actionTileList = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            settlementImgArr[i - 1] = new Image(getClass().getResourceAsStream("/images/" + color + i + ".png"));
        }
        displayScore();
        tempScore =0;
        turnConfirmed = false;
        firstPlayerToken = new Image(getClass().getResourceAsStream("/images/NullImage.png"));
        
        if(firstPlayerObj == null) {
        	firstPlayerObj = new GameObject(firstPlayerToken, 145, 10, 50, 57.67, 2);
        	firstPlayerObj.setEffect(new DropShadow(10, Color.BLACK));
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

    public void setScore(int score) {this.score = score;}
    public void setTempScore(int score) {this.tempScore = score;}

    public void displayScore() {
        ObjectHandler objectHandler = ObjectHandler.get();

        scoreLabel = new Label("Score: ");
        scoreLabel.setVisible(true);
        scoreLabel.setLayoutX(50);
        scoreLabel.setLayoutY(120);
        scoreLabel.setPrefSize(300, 80);
        scoreLabel.setFont(new Font("Ariel", 70));

        objectHandler.add(scoreLabel);
    }
    
    private void displayFirstPlayerToken() {
    	firstPlayerObj.setImage(firstPlayerToken);
    }
    
    public void setFirstPlayer() {
    	firstPlayerToken = new Image(getClass().getResourceAsStream("/images/KB-FirstPlayerToken.png"));
    	displayFirstPlayerToken();
    }

    public void updateScore() {
        if (!turnConfirmed)
            scoreLabel.setText("Score: " + tempScore);
        else
            scoreLabel.setText("Score: " + score);
    }

    public void startTurn() {
        updateGUIButtons();
        displayFirstPlayerToken();
    }

    public void addActionTile(ActionTile actionTile) {
        actionTileList.add(actionTile);
        updateGUIButtons();
    }

    public void removeActionTile(ActionTile actionTile) {
        actionTileList.remove(actionTile);
        updateGUIButtons();
    }

    private void updateGUIButtons() {
        GUI gui = GUI.get();
        ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();

        moveSelectionList.clear();

        updateTerrainCard();
        updateActionTiles();
    }

    private void updateTerrainCard() {
        GUI gui = GUI.get();
        ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();

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

    private void updateActionTiles() {
        Iterator<ActionTile> iter = actionTileList.iterator();

        while(iter.hasNext()) {
            ActionTile tile = iter.next();
            GUI gui = GUI.get();
            ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();

            Button addActionTiles = new Button();

            ActionTileTemplate tileObj = tile.getTileObj();
            ImageView tileImg = new ImageView(tileObj.getLocationImage());
            tileImg.setFitHeight(52);
            tileImg.setPreserveRatio(true);

            addActionTiles.setPrefSize(tileImg.getFitWidth(), tileImg.getFitHeight());
            addActionTiles.setGraphic(tileImg);

            addActionTiles.setOnAction(e -> {
                if (Board.get().getSettlementsPlacedSinceReset()==Board.get().getSettlementLimit() || Board.get().getSettlementsPlacedSinceReset()==0){
                    gui.setCancelButtonDisable(false);
                    usedActionTile = true;
                    tileObj.setActive(true);
                }
            });
            moveSelectionList.add(addActionTiles);
        }
    }

    public void addSettlement() {settlementNum++;}
    public void removeSettlement() {settlementNum--;}
    public void setTurnConfirmed(boolean x){
        turnConfirmed = x;
    }
    public int getTempScore(){
        return tempScore;
    }
    public int getScore(){
        return score;
    }
    public void hideScore(){
        scoreLabel.setVisible(false);
    }
    public boolean isUsedActionTile(){
        return usedActionTile;
    }
}







