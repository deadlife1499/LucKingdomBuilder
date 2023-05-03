package application;

import java.util.HashMap;

public class HexNode {
    private String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    private HexButton hexButton;
    private int playerNum;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String terrain){
        bordering = new HexNode[6];
        this.terrain = terrain;
        hasSettlement = false;
        
        TurnHandler turnHandler = TurnHandler.get();
        playerNum = -1;
    }
    
    public boolean hasSettlement() {return hasSettlement;}
    
    public void addSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.removeSettlement();
    	hasSettlement = true;
    	
        playerNum = player.getPlayerNum();
    }
    
    public HexButton getHexButton() {return hexButton;}
    public int getPlayerNum() {return playerNum;}
    
    public void setHexButton(HexButton button) {
    	hexButton = button;
    }
    
    public void removeSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.addSettlement();
    	hasSettlement = false;
    }
    
    public HexNode[] getBordering(){return bordering;}    
    public String getTerrainType() {return terrain;}
}