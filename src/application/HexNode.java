package application;

public class HexNode {
    private final String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    private boolean[] checked;
    private boolean[] checkedF;

    private int player;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String terrain){
        bordering = new HexNode[6];
        this.terrain = terrain;
        hasSettlement = false;
        player = 5;
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
        return player;
    }
    public HexNode getBordering(int x){
        return bordering[x];
    }
    
    public HexNode[] getBordering(){return bordering;}    
    public String getTerrainType() {return terrain;}
    public void removePlayer(){
        player = 5;
    }
    public void setPlayer(int n){
        player = n;
    }
    public String toString(){
        if(bordering[4]==null){
            if(bordering[3]==null){
                return terrain + " " + toString(bordering[1]) + "\n" + toString(bordering[2]);
            }
            else{
                return terrain + " " + toString(bordering[1]) + "\n" + toString(bordering[3]);
            }
        }
        return terrain + " " + toString(bordering[1]);
    }
    private String toString(HexNode x){
        if(x==null) return "";
        if(x.getBordering()[4]!=null)
            return x.getTerrain() + " " + toString(x.getBordering()[1]);
        if(x.getBordering()[3]==null){
            return terrain + " " + toString(x.getBordering()[1]) + "\n" + toString(x.getBordering()[2]);
        }
        else{
            return terrain + " " + toString(x.getBordering()[1]) + "\n" + toString(x.getBordering()[3]);
        }
    }
    public String toPlayerString(){
        if(bordering[4]==null){
            if(bordering[3]==null){
                return player + " " + toPlayerString(bordering[1]) + "\n" + toPlayerString(bordering[2]);
            }
            else{
                return player + " " + toPlayerString(bordering[1]) + "\n" + toPlayerString(bordering[3]);
            }
        }
        return player + " " + toPlayerString(bordering[1]);
    }
    private String toPlayerString(HexNode x){
        if(x==null) return "";
        if(x.getBordering()[4]!=null)
            return x.getNum() + " " + toPlayerString(x.getBordering()[1]);
        if(x.getBordering()[3]==null){
            return player + " " + toPlayerString(x.getBordering()[1]) + "\n" + toPlayerString(x.getBordering()[2]);
        }
        else{
            return player + " " + toPlayerString(x.getBordering()[1]) + "\n" + toPlayerString(x.getBordering()[3]);
        }
    }
    public String getTerrain(){
        return  terrain;
    }
}