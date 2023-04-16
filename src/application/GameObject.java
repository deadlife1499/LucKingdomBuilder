package application;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class GameObject extends ImageView {
	//new Image(getClass().getResourceAsStream("/images/filename"));
	private int sortingLayer;
	private ObjectHandler objectHandler;
	
	public GameObject() {
		super();
		sortingLayer = 0;
		
		objectHandler = ObjectHandler.get();
		objectHandler.add(this);
	}
	
	public GameObject(Image image, double x, double y, double width, double height, int sortingLayer) {
		super(image);
		setBounds(x, y, width, height);
		this.sortingLayer = sortingLayer;
		
		objectHandler = ObjectHandler.get();
		objectHandler.add(this);
	}
	
	public void add(Image image1, double x1, double y1, double width1, double height1) {
		Image image2 = getImage();
		if(image2 == null) {
			setImage(image1);
			setBounds(x1, y1, width1, height1);
			return;
		}
		
		double x2 = getX();
		double y2 = getY();
		double width2 = getFitWidth();
		double height2 = getFitHeight();
		
		BufferedImage bImg1 = SwingFXUtils.fromFXImage(image1, null);
		BufferedImage bImg2 = SwingFXUtils.fromFXImage(image2, null);
		
		int width = (int)(Math.max(x1 + width1, x2 + width2) - Math.min(x1, x2));
		int height = (int)(Math.max(y1 + height1, y2 + height2) - Math.min(y1, y2));
		System.out.println("Width: " + width + " Height: " + height);
		
		BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = combinedImage.createGraphics();
		
		double x = Math.min(x1, x2);
		double y = Math.min(y1, y2);
		
		g.drawImage(bImg2, (int)(x2 - x), (int)(y2 - y), (int)width2, (int)height2, null);
		g.drawImage(bImg1, (int)(x1 - x), (int)(y1 - y), (int)width1, (int)height1, null);
		g.dispose();
		
		WritableImage img = SwingFXUtils.toFXImage(combinedImage, null);
		setImage(img);
		setBounds(Math.min(x1, x2), Math.min(y1, y2), width, height);
	}
	
	public int getSortingLayer() {return sortingLayer;}
	
	public void setBounds(double x, double y, double width, double height) {
		setX(x);
		setY(y);
		setFitWidth(width);
	    setFitHeight(height);
	}
	
	public String toString() {
		return getImage() + "\n" + getX() + "\n" + getY() + "\n" + getFitWidth() + "\n" + getFitHeight() + "\n" +  getSortingLayer();
	}
}











