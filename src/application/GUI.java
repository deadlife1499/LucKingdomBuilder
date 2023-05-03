package application;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GUI {
	private GameButton confirmButton;
	private GameButton cancelButton;
	private GameButton nextTurnButton;
	private HBox moveSelectionBox;
	private Label playerLabel;
	private Button terrainButton;
	private static GUI gui;
	
	public GUI() {
		Board board = Board.get();
		confirmButton = new GameButton("[Confirm]");
		confirmButton.setBounds(80, 650, 200, 100);
		
		confirmButton.setOnAction(e -> {
			if(board.getSettlementsPlacedSinceReset() == 3) {			
				board.resetSettlementsPlaced();
				
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
		});
		cancelButton = new GameButton("[Cancel]");
		cancelButton.setBounds(80, 800, 200, 100);
		
		cancelButton.setOnAction(e -> {
			GameObject settlementObj = board.getSettlementObj();
			
			if(settlementObj != null) {
				settlementObj.removePreviousImages(board.getSettlementsPlacedSinceReset());
				board.resetSettlementsPlaced();
			}
			setConfirmButtonDisable(true);
			setCancelButtonDisable(true);
			
			TerrainCard card = board.getActiveCard();
			if(card.isActive()) {
				card.deactivateCard();
			}
		});
		
		nextTurnButton = new GameButton("[Next turn]");
		nextTurnButton.setBounds(80, 950, 200, 100);
		
		nextTurnButton.setOnAction(e -> {
			TurnHandler turnHandler = TurnHandler.get();
			
			turnHandler.nextTurn();
			setNextButtonDisable(true);
		});
		setNextButtonDisable(true);
		
		moveSelectionBox = new HBox();
		ScrollPane moveSelectionScrollPane = new ScrollPane(moveSelectionBox);
		
		moveSelectionScrollPane.setLayoutX(50);
		moveSelectionScrollPane.setLayoutY(300);
		moveSelectionScrollPane.setPrefSize(240, 62);
		
		ObjectHandler objectHandler = ObjectHandler.get();
		objectHandler.add(moveSelectionScrollPane);
		
		TurnHandler turnHandler = TurnHandler.get();
		playerLabel = new Label("Player " + (turnHandler.getCurrentPlayer().getPlayerNum() + 1));
		
		playerLabel.setLayoutX(50);
		playerLabel.setLayoutY(40);
		playerLabel.setPrefSize(300, 80);
		playerLabel.setFont(new Font("Ariel", 70));
		
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
			if(!card.isActive()) {
				card.activateCard();
			}
    	});
    	moveSelectionBox.getChildren().add(terrainButton);
    	setConfirmButtonDisable(true);
    	setCancelButtonDisable(true);
	}
	
	public HBox getMoveSelectionBox() {return moveSelectionBox;}
	
	public void setPlayerLabelText(String text) {playerLabel.setText(text);}
	public void setConfirmButtonDisable(boolean disabled) {confirmButton.setDisable(disabled);}
	public void setCancelButtonDisable(boolean disabled) {cancelButton.setDisable(disabled);}
	public void setNextButtonDisable(boolean disabled) {nextTurnButton.setDisable(disabled);}
	
	public static GUI get() {
		if(GUI.gui == null) {
			GUI.gui = new GUI();
		}
		return GUI.gui;
	}
}







