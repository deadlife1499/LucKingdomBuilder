package application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ColorSelection {
	private static final String[] settlementColors = {"RED", "GREEN", "PINK", "ORANGE", "BLUE", "BROWN", "YELLOW", "PURPLE"};
	private HashSet<Node> selectionObjects;
	private String[] playerColors;
	private GameButton[] mainButtons;
	private int currentColorNum;
	private GameButton nextButton;
	
	public ColorSelection() {
		//setStyle("-fx-background-color: " + Color.COLOR.getValue());
		Window window = Window.get();
		double xMultiplier = window.getWidth() / 1920.0;
		double yMultiplier = window.getHeight() / 1080.0;
		Image background = new Image(getClass().getResourceAsStream("/images/colorSelectBackground.jpeg"));
		GameObject backgroundObj = new GameObject(background, 0, 0, 1920 * xMultiplier, 1080 * yMultiplier, 0);
		selectionObjects = new HashSet<>();
		playerColors = new String[4];
		mainButtons = new GameButton[4];
		nextButton = new GameButton("[Use random colors]");
		
		for(int i = 0; i < 4; i++) {			
			GameButton button = new GameButton("Player " + (i+1) + "\n[Click to select color]");
			button.setBounds(224 + 424 * i, 440, 200, 200);
			
			button.setOnAction(e -> {
				if(!selectionObjects.isEmpty()) {
					closeColorSelection();
				}
				
				currentColorNum = (int)(button.getLayoutX() - 224) / 424;
				openColorSelection(button, button.getLayoutX(), button.getLayoutY() + 200);
			});
			mainButtons[i] = button;
		}
		nextButton.setBounds(1670, 980, 200, 50);
		
		nextButton.setOnAction(e -> {
			for(int i = 0; i < 4; i++) {
				String[] sortedArr = playerColors.clone();
				Arrays.sort(sortedArr);
				
				if(playerColors[i] == null) {
					String newColor = settlementColors[(int)(Math.random() * 8)];
					
					while(Arrays.binarySearch(sortedArr, newColor) < 0) {
						newColor = settlementColors[(int)(Math.random() * 8)];
					}
				}
			}
			TurnHandler turnHandler = TurnHandler.get();
			turnHandler.setPlayers(playerColors);
			
			ObjectHandler objectHandler = ObjectHandler.get();
			objectHandler.clear();
			
			Board board = new Board();
			new GUI(board);
		});
	}
	
	private void openColorSelection(GameButton button, double xOffset, double yOffset) {
		GameButton selectionBackground = new GameButton();
		selectionBackground.setBounds(xOffset - 60, yOffset, 320, 320);
		selectionBackground.setStyle("-fx-background-color: " + "#" + Color.BLACK.toString().substring(2));
		selectionObjects.add(selectionBackground);
		
		for(int i = 0; i < 8; i++) {			
			GameButton colorButton = new GameButton();
			colorButton.setBounds(xOffset - 55 + 105 * (i % 3) , yOffset + 5 + 105 * (i / 3), 100, 100);
			colorButton.setStyle("-fx-background-color: " + "#" + Color.web(settlementColors[i]).toString().substring(2));
			for(String str : playerColors) {
				if(str != null && str.equals(settlementColors[i])) {
					colorButton.setDisable(true);
				}
			}
			
			colorButton.setOnAction(e -> {
				String newColor = colorButton.getStyle().substring(23);
				
				for(int j = 0; j < 8; j++) {
					String oldColor = Color.web(settlementColors[j]).toString().substring(2);
					
					if(newColor.equals(oldColor)) {
						playerColors[currentColorNum] = settlementColors[j];
						j = 8;
					}
				}
				mainButtons[currentColorNum].setStyle("-fx-background-color: " + "#" + Color.web(playerColors[currentColorNum]).toString().substring(2));
				closeColorSelection();
				
				for(String str : playerColors) {
					if(str == null) {
						return;
					}
				}
				nextButton.setText("[Next]");
			});
			selectionObjects.add(colorButton);
		}
		GameButton colorButton = new GameButton("[Back]");
		colorButton.setBounds(xOffset + 155 , yOffset + 215, 100, 100);
		
		colorButton.setOnAction(e -> {
			closeColorSelection();
		});
		selectionObjects.add(colorButton);
	}
	
	private void closeColorSelection() {
		ObjectHandler objectHandler = ObjectHandler.get();
		Iterator<Node> iter = selectionObjects.iterator();
					
		while(iter.hasNext()) {
			Node node = iter.next();
				
			objectHandler.remove(node);
		}
		selectionObjects.clear();
	}
}











