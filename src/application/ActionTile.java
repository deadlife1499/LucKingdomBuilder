package application;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class ActionTile { 
	private ActionTileTemplate tileObj;
	
    public ActionTile(HexButton hexButton, int x, int y, int width, int height, String type) {
    	tileObj = createTile(type);
    }
    
    private ActionTileTemplate createTile(String type) {
    	ActionTileTemplate obj = null;
    	
    	switch(type) {
    	case "fa":
    		obj = new Farm();
    		
    		break;
    	case "ha":
    		obj = new Harbor();
    		
    		break;
    	case "oa":
    		obj = new Oasis();
    		
    		break;
    	case "or":
    		obj = new Oracle();
    		
    		break;
    	case "pa":
    		obj = new Paddock();
    		
    		break;
    	case "ta":
    		obj = new Tavern();
    		
    		break;
    	case "to":
    		obj = new Tower();
    		
    		break;
    	}
    	return obj;
    }
    
    private class ActionTileTemplate {
    	private Image tileImage;
    	
    	private ActionTileTemplate(String type) {
    		tileImage = new Image(getClass().getResourceAsStream("/images/KB-" + type + ".png"));
    	}
    }
    
    private class Farm extends ActionTileTemplate {
    	private void score() {
    		
    	}
    }
    
    private class Harbor extends ActionTileTemplate {
    	
    }
    
    private class Oasis extends ActionTileTemplate {
    	
    }
    
    private class Oracle extends ActionTileTemplate {
    	
    }
    
    private class Paddock extends ActionTileTemplate {
    	
    }
    
    private class Tavern extends ActionTileTemplate {
    	
    }
    
    private class Tower extends ActionTileTemplate {
    	
    }
}









