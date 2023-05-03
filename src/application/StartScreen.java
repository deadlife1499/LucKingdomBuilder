package application;

import javafx.scene.image.Image;

public class StartScreen {
	public StartScreen() {
		Image background = new Image(getClass().getResourceAsStream("/images/BackgroundPlaceholder.png"));
		new GameObject(background, 0, 0, 1920, 1080, 0);
		
		GameButton startButton = new GameButton("Play");
		startButton.setBounds(860, 490, 200, 100);
		
		startButton.setOnMouseReleased(e -> {
			ObjectHandler objectHandler = ObjectHandler.get();
			objectHandler.clear();
			
			new ColorSelection();
		});
	}
}
