package application;
	
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class Window extends Application {
	private static Window window = null;
	private static Group root;
	private static final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int WIDTH = (int)screen.getWidth();
	private static final int HEIGHT = (int)screen.getHeight();
	
	@Override
	public void start(Stage primaryStage) {
		try {		
			root = new Group();
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(false);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		window = new Window();
		new StartScreen();
	}
	
	public static void main(String[] args) {

		launch(args);
	}
	
	public int getWidth() {return Window.WIDTH;}
	public int getHeight() {return Window.HEIGHT;}
	public Group getRoot() {return Window.root;}
	
	public String toString() {
		return root.getChildren() + "";
	}
	
	public static Window get() {
		return Window.window;
	}

}














