package application;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
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

	public GUI() {
		Image backgroundImage = new Image(getClass().getResourceAsStream("/images/KB-Background.png"));
		GameObject background = new GameObject(backgroundImage, 0, 0, 1920, 1080, 0);
		ColorAdjust colorAdjust = new ColorAdjust();  
		
	    colorAdjust.setContrast(0);          
	    colorAdjust.setHue(0);     	      
	    colorAdjust.setBrightness(-.4);  	      
	    colorAdjust.setSaturation(0);   
	    background.setEffect(colorAdjust);
		
		Board board = Board.get();
		confirmButton = new GameButton("Confirm");
		Font font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.TTF"), 32);
		confirmButton.setFont(font);
		confirmButton.setTextFill(Color.WHITE); 
		confirmButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		confirmButton.setBackground(null);
		confirmButton.setBounds(80, 650, 200, 75);

		confirmButton.setOnAction(e -> {
			if(board.getSettlementsPlacedSinceReset() == board.getSettlementLimit()) {
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
		cancelButton.setBounds(80, 800, 200, 75);

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

		nextTurnButton = new GameButton("[Next turn]");
		nextTurnButton.setFont(font);
		nextTurnButton.setTextFill(Color.WHITE); 
		nextTurnButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		nextTurnButton.setBackground(null);
		nextTurnButton.setBounds(80, 950, 200, 75);

		nextTurnButton.setOnAction(e -> {
			TurnHandler.get().getCurrentPlayer().setTurnConfirmed(false);
			TurnHandler turnHandler = TurnHandler.get();
			turnHandler.getCurrentPlayer().hideScore();
			turnHandler.nextTurn();
			setNextButtonDisable(true);
			turnHandler.getCurrentPlayer().displayScore();
			//Board.get().getActiveCard().reset();
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






