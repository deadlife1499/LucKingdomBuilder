package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GUI {
	private GameButton confirmButton;
	private GameButton cancelButton;
	private GameButton nextTurnButton;
	private HBox moveSelectionBox;
	private Label playerLabel;
	private Button terrainButton;
	private static GUI gui;
	private Group playerStatsGroup;
	private ArrayList<Player> playerList;
	private Group endGameGroup;

	public GUI() {
		Image backgroundImage = new Image(getClass().getResourceAsStream("/images/KB-MainBackground.png"));
		GameObject background = new GameObject(backgroundImage, 0, 0, 1920, 1080, 0);
		ColorAdjust colorAdjust = new ColorAdjust();  
		
	    colorAdjust.setContrast(0);          
	    colorAdjust.setHue(0);     	      
	    colorAdjust.setBrightness(-.4);  	      
	    colorAdjust.setSaturation(0);   
	    //background.setEffect(colorAdjust);
		
		Board board = Board.get();
		confirmButton = new GameButton("Confirm");
		Font font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.ttf"), 32);
		confirmButton.setFont(font);
		confirmButton.setTextFill(Color.WHITE); 
		confirmButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		confirmButton.setBackground(null);
		confirmButton.setBounds(138, 500, 200, 75);

		confirmButton.setOnAction(e -> {
			if(board.getSettlementsPlacedSinceReset() == board.getSettlementLimit()) {

				setConfirmButtonDisable(true);
				setCancelButtonDisable(true);
				setNextButtonDisable(false);

				TerrainCard card = board.getActiveCard();
				if(card.isActive()) {
					card.deactivateCard();
					terrainButton = (Button)moveSelectionBox.getChildren().get(0);
					terrainButton.setDisable(true);
				}
			}
			board.confirmPlacement();
			TurnHandler.get().getCurrentPlayer().setTurnConfirmed(true);
			TurnHandler.get().getCurrentPlayer().setScore(TurnHandler.get().getCurrentPlayer().getTempScore());
			TurnHandler.get().getCurrentPlayer().updateScore();
		});
		cancelButton = new GameButton("Cancel");
		cancelButton.setFont(font);
		cancelButton.setTextFill(Color.WHITE); 
		cancelButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		cancelButton.setBackground(null);
		cancelButton.setBounds(138, 600, 200, 75);

		cancelButton.setOnAction(e -> {
			GameObject settlementObj = board.getSettlementObj();
			if(settlementObj != null) {
				settlementObj.removePreviousImages(board.getSettlementsPlacedSinceReset());
				board.resetSettlementsPlaced();
			}
			setConfirmButtonDisable(true);
			setCancelButtonDisable(true);
			board.cancelPlacement();
			TerrainCard card = board.getActiveCard();
			if(card.isActive()) {
				card.deactivateCard();
			}
			TurnHandler.get().getCurrentPlayer().setTurnConfirmed(false);
			TurnHandler.get().getCurrentPlayer().setTempScore(0);
			TurnHandler.get().getCurrentPlayer().updateScore();
			//Board.get().getActiveCard().reset();
		});

		nextTurnButton = new GameButton("Next turn");
		nextTurnButton.setFont(font);
		nextTurnButton.setTextFill(Color.WHITE); 
		nextTurnButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		nextTurnButton.setBackground(null);
		nextTurnButton.setBounds(138, 700, 200, 75);

		nextTurnButton.setOnAction(e -> {
			board.resetSettlementsPlaced();
			TurnHandler.get().getCurrentPlayer().setTurnConfirmed(false);
			TurnHandler turnHandler = TurnHandler.get();
			turnHandler.getCurrentPlayer().hideScore();
			turnHandler.nextTurn();
			setNextButtonDisable(true);
			turnHandler.getCurrentPlayer().displayScore();
			//Board.get().getActiveCard().reset();
			turnHandler.getCurrentPlayer().activateActionTiles();
		});
		setNextButtonDisable(true);

		moveSelectionBox = new HBox();
		ScrollPane moveSelectionScrollPane = new ScrollPane(moveSelectionBox);

		moveSelectionScrollPane.setLayoutX(75);
		moveSelectionScrollPane.setLayoutY(190);
		moveSelectionScrollPane.setPrefSize(320, 62);

		ObjectHandler objectHandler = ObjectHandler.get();
		objectHandler.add(moveSelectionScrollPane);

		TurnHandler turnHandler = TurnHandler.get();
		playerLabel = new Label("Player " + (turnHandler.getCurrentPlayer().getPlayerNum() + 1));

		font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.ttf"), 70);
		playerLabel.setFont(font);
		playerLabel.setTextFill(Color.WHITE); 
		playerLabel.setLayoutX(120);
		playerLabel.setLayoutY(40);
		playerLabel.setPrefSize(300, 80);

		objectHandler.add(playerLabel);

		Player player = turnHandler.getCurrentPlayer();
		terrainButton = new Button();

		ImageView settlementImg = new ImageView(player.getSettlementIcon());
		settlementImg.setFitHeight(52);
		settlementImg.setPreserveRatio(true);

		terrainButton.setPrefSize(settlementImg.getFitWidth(), settlementImg.getFitHeight());
		terrainButton.setGraphic(settlementImg);

		terrainButton.setOnAction(e -> {
			setCancelButtonDisable(false);

			TerrainCard card = board.getActiveCard();
			card.reset();
			card.tempDeactivateCard();
			if(!card.isActive()) {
				card.activateCard();
			}
		});
		moveSelectionBox.getChildren().add(terrainButton);
		setConfirmButtonDisable(true);
		setCancelButtonDisable(true);

		Scoring.scoreCards();
		updatePlayerStats();
		player.updateSettlementsRemaining();
		openEndGameScreen();
	}

	public HBox getMoveSelectionBox() {return moveSelectionBox;}

	public void setPlayerLabelText(String text) {playerLabel.setText(text);}
	public void setConfirmButtonDisable(boolean disabled) {confirmButton.setDisable(disabled);}
	public void setCancelButtonDisable(boolean disabled) {cancelButton.setDisable(disabled);}
	public void setNextButtonDisable(boolean disabled) {nextTurnButton.setDisable(disabled);}
	
	@SuppressWarnings("unchecked")
	public void updatePlayerStats() {
		ObjectHandler objectHandler = ObjectHandler.get();
		TurnHandler turnHandler = TurnHandler.get();
		if(playerList == null) {
			playerList = (ArrayList<Player>)turnHandler.getPlayerList().clone();
		}
		playerList.add(playerList.remove(0));
		
		if(playerStatsGroup == null) {
			playerStatsGroup = new Group();
			objectHandler.add(playerStatsGroup);
		} else {
			playerStatsGroup.getChildren().clear();
		}
		
		for(int i = 0; i < playerList.size() - 1; i++) {
			Label playerLabel = new Label("Player " + (playerList.get(i).getPlayerNum() + 1));
			playerStatsGroup.getChildren().add(playerLabel);

			Font font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.ttf"), 20);
			playerLabel.setFont(font);
			playerLabel.setTextFill(Color.WHITE); 
			playerLabel.setLayoutX(60);
			playerLabel.setLayoutY(875 + i * 75);
			playerLabel.setPrefSize(300, 80);

			Image settlementImg = playerList.get(i).getSettlementIcon();
			ImageView settlementObj = new ImageView(settlementImg);
			
			settlementObj.setLayoutX(70);
			settlementObj.setLayoutY(860 + i * 75);
			settlementObj.setFitWidth(settlementImg.getWidth() / 10);
			settlementObj.setFitHeight(settlementImg.getHeight() / 10);
			
			playerStatsGroup.getChildren().add(settlementObj);
			Label settlementNumLabel = new Label("x " + playerList.get(i).getSettlementNum());
			playerStatsGroup.getChildren().add(settlementNumLabel);

			font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.ttf"), 40);
			settlementNumLabel.setFont(font);
			settlementNumLabel.setTextFill(Color.WHITE); 
			settlementNumLabel.setLayoutX(125);
			settlementNumLabel.setLayoutY(840 + i * 75);
			settlementNumLabel.setPrefSize(300, 80);
			
			ArrayList<ActionTile> actionTileList = playerList.get(i).getActionTileList();
			for(int j = 0; j < actionTileList.size(); j++) {
				Image tileImage = actionTileList.get(j).getTileObj().getLocationImage();
				ImageView tileObj = new ImageView(tileImage);
				
				tileObj.setLayoutX(200 + j * 50);
				tileObj.setLayoutY(855 + i * 75);
				tileObj.setFitWidth(tileImage.getWidth() / 4);
				tileObj.setFitHeight(tileImage.getHeight() / 4);
				
				playerStatsGroup.getChildren().add(tileObj);
			}
		}
	}

	public static GUI get() {
		if(GUI.gui == null) {
			GUI.gui = new GUI();
		}
		return GUI.gui;
	}
	public void openEndGameScreen() {
		if(endGameGroup == null) {
			endGameGroup = new Group();
			ObjectHandler.get().add(endGameGroup);
		}
		Image backgroundImg = new Image(getClass().getResourceAsStream("/images/BackgroundPlaceholder.png"));
		ImageView background = new ImageView(backgroundImg);
		background.setEffect(new DropShadow(10, Color.BLACK));

		background.setLayoutX(300);
		background.setLayoutY(200);
		background.setFitWidth(1320);
		background.setFitHeight(680);

		endGameGroup.getChildren().add(background);
		HashMap<String, Boolean> cardMap = Scoring.getCardMap();

		int cardX = 450;
		int playerY = 450;

		for(Map.Entry<String, Boolean> cardEntry : cardMap.entrySet()) {
			if(cardEntry.getValue()) {
				Image cardImg = new Image("/images/" + cardEntry.getKey() + "Objective.png");
				ImageView cardObj = new ImageView();
				cardObj.setEffect(new DropShadow(10, Color.BLACK));

				cardObj.setImage(cardImg);
				cardObj.setLayoutX(cardX);
				cardObj.setLayoutY(250);
				cardObj.setFitWidth(cardImg.getWidth() / 2);
				cardObj.setFitHeight(cardImg.getHeight() / 2);

				endGameGroup.getChildren().add(cardObj);
				cardX += 250;
				playerY += 60;
			}
		}
	}

	public void closeEndGameScreen() {
		endGameGroup.getChildren().clear();
	}
}






