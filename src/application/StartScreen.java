package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartScreen {
	private int playerAmount;

	public StartScreen() {
		playerAmount = 4;
		Image background = new Image(getClass().getResourceAsStream("/images/KB-StartScreen.png"));
		GameObject z = new GameObject(background, 0, 0, 1920, 1080, 0);
		Image logo = new Image(getClass().getResourceAsStream("/images/KB-Logo.png"));
		GameObject y = new GameObject(logo, 640, 30, 290, 256, 1);

		GameButton startButton = new GameButton("Play");
		Font font = Font.loadFont(getClass().getResourceAsStream("/MorrisRoman-Black.TTF"), 40);
		startButton.setFont(font);
		startButton.setTextFill(Color.WHITE);
		startButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		startButton.setBackground(null);
		startButton.setBounds(985, 80, 200, 85);



		GameButton playerAmountButton = new GameButton("Players: " + playerAmount);
		playerAmountButton.setFont(font);
		playerAmountButton.setTextFill(Color.WHITE);
		playerAmountButton.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(7), new BorderWidths(7))));
		playerAmountButton.setBackground(null);
		playerAmountButton.setBounds(970, 175, 230, 85);

		playerAmountButton.setOnMouseReleased(e -> {
			playerAmount++;
			if(playerAmount > 4) {
				playerAmount = 2;
			}
			playerAmountButton.setText("Players: " + playerAmount);
		});
		AnimationClass.FadeScreen(z);
		AnimationClass.FadeScreen(y);
		AnimationClass.FadeScreen(startButton);
		AnimationClass.FadeScreen(playerAmountButton);

		startButton.setOnMouseReleased(e -> {
			ObjectHandler objectHandler = ObjectHandler.get();
			objectHandler.clear();

			ColorSelection n = new ColorSelection(playerAmount);
			//AnimationClass.FadeScreenTransition();
			AnimationClass.FadeScreenOut(y);
			AnimationClass.FadeScreenOut(z);
			AnimationClass.FadeScreenOut(startButton);
			AnimationClass.FadeScreenOut(playerAmountButton);
		});
	}

}






