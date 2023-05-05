package application;

import java.util.HashMap;

public class HexNode {
    private String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    private int playerNum;
    private ActionTile actionTile;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String terrain){
    	this.terrain = terrain;
        bordering = new HexNode[6];
        hasSettlement = false;
        playerNum = -1;
        actionTile = null;
    }
    
    public void checkAdjacent() {
    	HexNode nodeAT = null;
    	
    	for(HexNode node : bordering) {
    		if(node != null && node.getTerrainType().length() > 1) {
    			nodeAT = node;
    		}
    	} if(nodeAT == null) {
    		return;
    	}
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	if(nodeAT.getActionTile().takeToken()) {
    		player.addActionTile(nodeAT.getActionTile());
    	}
    }
    
    public void removeAdjacent() {
    	HexNode nodeAT = null;
    	
    	for(HexNode node : bordering) {
    		if(node.getTerrainType().length() > 1) {
    			nodeAT = node;
    		}
    	} if(nodeAT == null) {
    		return;
    	}
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	HexNode nodeAT2 = null;
    	
    	for(HexNode node : nodeAT.getBordering()) {
    		if(node.hasSettlement && node.getPlayerNum() == player.getPlayerNum()) {
    			nodeAT2 = node;
    		}
    	} if(nodeAT2 != null) {
    		return;
    	}
    	nodeAT.getActionTile().returnToken();
    	player.removeActionTile(nodeAT.getActionTile());
    }
    
    public boolean hasSettlement() {return hasSettlement;}
    public int getPlayerNum() {return playerNum;}
    public HexNode[] getBordering(){return bordering;}    
    public String getTerrainType() {return terrain;}
    public ActionTile getActionTile() {return actionTile;}
    
    public void setActionTile(ActionTile tile) {actionTile = tile;}
    
    public void addSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.removeSettlement();
    	hasSettlement = true;
    	
        playerNum = player.getPlayerNum();
    }
    
    public void removeSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.addSettlement();
    	hasSettlement = false;
    }
}











