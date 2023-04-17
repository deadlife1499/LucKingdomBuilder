package application;

import java.util.TreeMap;
import java.util.TreeSet;
public class Player {
    private int points;
    private int playerNum;
    private int settlements;
    private TreeSet<LocationTile> locTiles;

    public Player(int num) {
        points=0;
        playerNum = num;
        settlements = 40;
        locTiles = new TreeSet<>();
    }
    public int getSettlementNum() {
        return settlements;
    }
    public int getPoints() {
        return points;
    }
    public TreeSet getLocTiles() {
        return locTiles;
    }
    public void addLocTiles(LocationTile tile) {
        locTiles.add(tile);
    }
    public void removeSettlements(int num) {
        settlements-=num;
    }
    public void useSettlements() {
        settlements-=3;
    }
    public int score(HexNode[][] hexMatrix){
        int add = 0;
        add += citizen(hexMatrix);
        points += add;
        return add;

    }
    private int citizen(HexNode[][] hexMatrix){
        HexNode root = hexMatrix[0][0];
        HexNode highest= null;
        int high=0;
        for(int i = 0; i<20; i++){
            for(int j = 0; j<20; j++){
                if(hexMatrix[i][j].getChecked(playerNum)!=true && hexMatrix[i][j].getNum()==playerNum){
                    int k = citizenHelper(hexMatrix[i][j]);
                    if (k>high){
                        highest=root;
                    }
                }
            }
        }
        return high/2;
    }
    private int citizenHelper(HexNode root){
        HexNode[] lol = root.getBordering();
        for(int i = 0; i<6; i++){
            lol[i].setChecked(playerNum);
        }
        if(root == null || root.getNum()!=playerNum){
            return 0;
        }
        if (root.getNum()==playerNum){
            return 1 + citizenHelper(lol[0]) + citizenHelper(lol[1]) + citizenHelper(lol[2]) + citizenHelper(lol[3]) + citizenHelper(lol[4]) + citizenHelper(lol[5]);
        }
        return 0;
    }
    private int discoverer(HexNode[][] hexMatrix){
        int cnt = 0;
        for(int i = 0; i<20; i++){
            if(discovererHelper(hexMatrix[i][0]))
                cnt++;
        }
        return cnt;
    }
    private boolean discovererHelper(HexNode root){
        if(root==null)
            return false;
        if(root.getNum()==playerNum)
            return true;
        return discovererHelper(root.getBordering(1));
    }
}

