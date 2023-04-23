package application;

public class HexNode {
    private String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String terrain){
        bordering = new HexNode[6];
        this.terrain = terrain;
        hasSettlement = false;
    }
    
    public boolean canPlaceSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	return !hasSettlement && !terrain.equals("s") && player.getSettlementNum() > 0;
    }
    
    public boolean hasSettlement() {return hasSettlement;}
    
    public void addSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.removeSettlement();
    	hasSettlement = true;
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