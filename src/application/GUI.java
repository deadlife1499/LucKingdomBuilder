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
	private HBox moveSelectionBox;
	private Label playerLabel;
	private static GUI gui;
	
	public GUI() {
		Board board = Board.get();
		confirmButton = new GameButton("Confirm");
		confirmButton.setBounds(80, 800, 200, 100);
		
		confirmButton.setOnAction(e -> {
			if(board.getSettlementsPlacedSinceReset() == 3) {
				TurnHandler turnHandler = TurnHandler.get();
				Player player = turnHandler.getCurrentPlayer();
			
				turnHandler.nextTurn();
				board.resetSettlementsPlaced();
				setConfirmButtonsVisible(false);
				
				TerrainCard card = board.getActiveCard();
				if(card.isActive()) {
					card.deactivateCard();
				}
			}
		});
		cancelButton = new GameButton("Cancel");
		cancelButton.setBounds(80, 950, 200, 100);
		
		cancelButton.setOnAction(e -> {
			GameObject settlementObj = board.getSettlementObj();
			TurnHandler turnHandler = TurnHandler.get();
			Player player = turnHandler.getCurrentPlayer();
			
			if(settlementObj != null) {
				settlementObj.removePreviousImages(board.getSettlementsPlacedSinceReset());
				board.resetSettlementsPlaced();
			}
			setConfirmButtonsVisible(false);
			
			TerrainCard card = board.getActiveCard();
			if(card.isActive()) {
				card.deactivateCard();
			}
		});
		moveSelectionBox = new HBox();
		ScrollPane moveSelectionScrollPane = new ScrollPane(moveSelectionBox);
		
		moveSelectionScrollPane.setLayoutX(50);
		moveSelectionScrollPane.setLayoutY(400);
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
		Button addSettlements = new Button();
    	addSettlements.setPrefSize(60, 60);
    	
    	ImageView settlementImg = new ImageView(player.getSettlementImg());
    	settlementImg.setFitWidth(60);
    	settlementImg.setFitHeight(40);
    	addSettlements.setGraphic(settlementImg);
    	
    	addSettlements.setOnAction(e -> {
    		gui.setConfirmButtonsVisible(true);
    		
    		TerrainCard card = board.getActiveCard();
			if(!card.isActive()) {
				card.activateCard();
			}
    	});
    	moveSelectionBox.getChildren().add(addSettlements);
    	setConfirmButtonsVisible(false);
	}
	
	public HBox getMoveSelectionBox() {return moveSelectionBox;}
	
	public void setPlayerLabelText(String text) {playerLabel.setText(text);}
	public void setConfirmButtonsVisible(boolean visible) {
		confirmButton.setVisible(visible);
		cancelButton.setVisible(visible);
	}
	
	public static GUI get() {
		if(GUI.gui == null) {
			GUI.gui = new GUI();
		}
		return GUI.gui;
	}
}







