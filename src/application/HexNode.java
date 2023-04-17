package application;

public class HexNode {
    private boolean[] checked;
    private boolean[] checkedF;
    private String terrain;
    private HexNode[] bordering;

    private int num; // 0 1 2 3 | 5 for none
    
    // 0 1 2 3 4 5
    // NE E SE SW W NW
    public HexNode(String t){
        bordering = new HexNode[6];
        terrain = t;
        checked = new boolean[4];
        checkedF = new boolean[4];
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
    public void setChecked(int n){
        checked[n] = !checked[n];
    }
    public boolean getChecked(int n){
        return checked[n];
    }
    public int getNum(){
        return num;
    }
}