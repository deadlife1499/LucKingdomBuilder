package application;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class ObjectHandler {
	private static TreeMap<Integer, ArrayList<GameObject>> gameObjects;
	private static HashSet<GameButton> buttonList;
	private static HashSet<HexButton> hexList;
	private static ObjectHandler objectHandler;
	
	public void add(GameObject imgView) {
		int sortingLayer = imgView.getSortingLayer();
		
		if(gameObjects.get(sortingLayer) == null) {
			gameObjects.put(sortingLayer, new ArrayList<GameObject>());
		}
		
		ArrayList<GameObject> temp;
		temp = gameObjects.get(sortingLayer);
		temp.add(imgView);
		
		Window window = Window.get();
	    Group root = window.getRoot();
		
		if(gameObjects.lastKey() == sortingLayer) {
			root.getChildren().add(imgView);
		    
		    return;
		}
		ObservableList<Node> list = root.getChildren();
		list.removeAll();
		
		Set<Entry<Integer, ArrayList<GameObject>>> entrySet = gameObjects.entrySet();
        Iterator<Entry<Integer, ArrayList<GameObject>>> iter = entrySet.iterator();
        
        while(iter.hasNext()) {
            Entry<Integer, ArrayList<GameObject>> entry = iter.next();
            for(GameObject img : entry.getValue()) {
            	root.getChildren().add(img);
            }
        }
        
        Iterator<GameButton> iter2 = buttonList.iterator();
        while(iter2.hasNext()) {
        	GameButton button = iter2.next();
        	
        	root.getChildren().add(button);
        }
        
        Iterator<HexButton> iter3 = hexList.iterator();
        while(iter3.hasNext()) {
        	HexButton button = iter3.next();
        	
        	root.getChildren().add(button);
        }
	}
	
	public void add(GameButton button) {
		buttonList.add(button);
		
		Window window = Window.get();
	    Group root = window.getRoot();
	    
	    root.getChildren().add(button);
	}
	
	public void add(HexButton button) {
		hexList.add(button);
		
		Window window = Window.get();
	    Group root = window.getRoot();
	    
	    root.getChildren().add(button);
	}
	
	public ObjectHandler() {
		gameObjects = new TreeMap<>();
		buttonList = new HashSet<>();
		hexList = new HashSet<>();
	}
	
	public static ObjectHandler get() {
		if(ObjectHandler.objectHandler == null) {
			ObjectHandler.objectHandler = new ObjectHandler();
		}
		return ObjectHandler.objectHandler;
	}
}
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	

