package application;

public class HexNode {
    private String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    private boolean[] checked;
    private boolean[] checkedF;

    private int p;
    
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
    public void setChecked(int n){
        checked[n] = !checked[n];
    }
    public boolean getChecked(int n){
        return checked[n];
    }
    
    public void removeSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.addSettlement();
    	hasSettlement = false;
    }
    public int getNum(){
        return p;
    }
    public HexNode getBordering(int x){
        return bordering[x];
    }
    
    public HexNode[] getBordering(){return bordering;}    
    public String getTerrainType() {return terrain;}
}