package application;

public class HexNode {
    private String terrain;
    private HexNode[] bordering;
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String t){
        bordering = new HexNode[6];
        terrain = t;
    }
    
    public HexNode[] getBordering(){
        return bordering;
    }
    
    public HexNode getBordering(int x){
        return bordering[x];
    }
    
    public String toString(){
    	return terrain;
    }
}