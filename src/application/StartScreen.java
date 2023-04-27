package application;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class StartScreen {
	public StartScreen() {
		Window window = Window.get();
		double xMultiplier = window.getWidth() / 1920.0;
		double yMultiplier = window.getHeight() / 1080.0;
		Image background = new Image(getClass().getResourceAsStream("/images/BackgroundPlaceholder.png"));
		GameObject backgroundObj = new GameObject(background, 0, 0, 1920 * xMultiplier, 1080 * yMultiplier, 0);

		GameButton startButton = new GameButton("Play");
		startButton.setBounds(860, 490, 200 * xMultiplier, 100 * yMultiplier);

		startButton.setOnMouseReleased(e -> {
			ObjectHandler objectHandler = ObjectHandler.get();
			objectHandler.clear();

			//Board board = new Board();
			//new GUI(board);

			new ColorSelection();
		});
	}
}
