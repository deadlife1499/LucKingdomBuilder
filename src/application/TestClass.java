package application;

import javafx.scene.image.Image;

public class TestClass {
	private Image image, image2;
	
	public TestClass() {
		image = new Image(getClass().getResourceAsStream("/images/OvergrownBackground2.png"));
		image2 = new Image(getClass().getResourceAsStream("/images/Spaceship.png"));
		
		GameObject background = new GameObject(image, 0, 0, 1920, 1080, 0);
		GameObject spaceship = new GameObject(image2, 860, 440, 200, 200, 0);
		
		GameButton button = new GameButton("Click Me!");
		button.setBounds(760, 440, 400, 200);
		button.setOnAction(e -> {
			button.setText("Click Me!" + (int)(Math.random() * 10));
        });
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
