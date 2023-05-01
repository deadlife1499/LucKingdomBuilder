package application;

import java.util.ArrayList;

public class HexNode {
    private final String terrain;
    private HexNode[] bordering;
    private boolean hasSettlement;
    private boolean[] checked;
    private boolean[] checkedF;

    private int playerNum;
    private boolean confirmed;
    private HexButton hexButton;
    private boolean[] isTaken;
    private int tokens;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String terrain){
        bordering = new HexNode[6];
        this.terrain = terrain;
        hasSettlement = false;
        playerNum = 5;
        confirmed=false;
        isTaken = new boolean[4];
        tokens = 2;
        checked = new boolean[4];
        checkedF = new boolean[4];
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
        playerNum=player.getPlayerNum();
    }
    public void setChecked(int n){
        checked[n] = true;
    }
    public boolean getChecked(int n){
        return checked[n];
    }
    
    public void removeSettlement() {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
    	player.addSettlement();
    	hasSettlement = false;
        playerNum=5;
    }
    public int getNum(){
        return playerNum;
    }
    public HexNode getBordering(int x){
        return bordering[x];
    }
    
    public HexNode[] getBordering(){return bordering;}    
    public String getTerrainType() {return terrain;}
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
        if(x.getBordering(4)==null){
            if(x.getBordering()[3]==null){
                return x.getTerrain() + " " + toString(x.getBordering(1)) + "\n" + toString(x.getBordering(2));
            }
            else{
                return x.getTerrain() + " " + toString(x.getBordering(1)) + "\n" + toString(x.getBordering(3));
            }
        }
        return x.getTerrain() + " " + toString((x.getBordering(1)));
    }
    public String toPlayerString(){
        if(bordering[4]==null){
            if(bordering[3]==null){
                return playerNum + " " + toPlayerString(bordering[1]) + "\n" + toPlayerString(bordering[2]);
            }
            else{
                return playerNum + " " + toPlayerString(bordering[1]) + "\n" + toPlayerString(bordering[3]);
            }
        }
        return playerNum + " " + toPlayerString(bordering[1]);
    }
    private String toPlayerString(HexNode x){
        if(x==null) return "";
        if(x.getBordering()[4]!=null)
            return x.getNum() + " " + toPlayerString(x.getBordering()[1]);
        if(x.getBordering()[3]==null){
            return x.getNum() + " " + toPlayerString(x.getBordering()[1]) + "\n" + toPlayerString(x.getBordering()[2]);
        }
        else{
            return x.getNum() + " " + toPlayerString(x.getBordering()[1]) + "\n" + toPlayerString(x.getBordering()[3]);
        }
    }
    public String getTerrain(){
        return  terrain;
    }
    public boolean equals(HexNode z){
        if(bordering==z.getBordering()){
            return terrain.equals(z.getTerrain());
        }
        return false;
    }
    public void setConfirmed(){
        confirmed=!confirmed;
    }
    public boolean isConfirmed(){
        return confirmed;
    }
    public HexButton getHexButton() {return hexButton;}
    //public int getPlayerNum() {return playerNum;}

    public void setHexButton(HexButton button) {
        hexButton = button;
    }
    public int getPlayerNum(){
        return playerNum;
    }
    public boolean isTaken(int x){
        return isTaken[x];
    }
    public void setIsTaken(int x){
        isTaken[x]=true;
        tokens--;
    }
}