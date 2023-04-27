package application;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
public class Player {
    private int points;
    private final int playerNum;
    private int settlements;
    private final TreeSet<LocationTile> locTiles;

    private Image settlementImg;

    private final String color;

    public Player(int num, String color) {
        points = 0;
        playerNum = num;
        this.color = color;
        settlements = 40;
        locTiles = new TreeSet<>();
        try {
            settlementImg = new Image(getClass().getResourceAsStream("/images/" + color.toLowerCase() + "Settlement.png"));
        } catch(NullPointerException e) {
            settlementImg = new Image(getClass().getResourceAsStream("/images/blueSettlement.png"));
        }
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
        add+=citizen(hexMatrix);
        return add;

    }
    private int citizen(HexNode[][] hexMatrix){
        HexNode root = hexMatrix[0][0];
        HexNode highest= null;
        int high=0;
        for(int i = 0; i<20; i++){
            for(int j = 0; j<20; j++){
                if(!hexMatrix[i][j].getChecked(playerNum) && hexMatrix[i][j].getNum()==playerNum){
                    int k = citizenHelper(hexMatrix[i][j]);
                    if (k>high){
                        highest=root;
                        high = k;
                    }
                }
            }
        }
        return high/2;
    }
    private int citizenHelper(HexNode root){
        if(root == null || root.getNum()!=playerNum){
            return 0;
        }
        HexNode[] lol = root.getBordering();
        for(int i = 0; i<6; i++){
            lol[i].setChecked(playerNum);
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
    private int fisherman(TreeMap<HexNode, Integer> x){
        int pts = 0;
        //x.get()
        for( HexNode node : x.keySet()){
            for(int i =0; i<6; i++){
                if(node.getBordering(i).toString().equals("w")){
                    pts++;
                }
            }
        }
        return pts;
    }
    private int miner(TreeMap<HexNode, Integer> x){
        int pts = 0;
        //x.get()
        for( HexNode node : x.keySet()){
            for(int i =0; i<6; i++){
                if(node.getBordering(i).toString().equals("m")){
                    pts++;
                }
            }
        }
        return pts;
    }
    private int worker(TreeMap<HexNode, Integer> x){
        int pts = 0;
        //x.get()
        for( HexNode node : x.keySet()){
            for(int i =0; i<6; i++){
                if(node.getBordering(i).toString().equals("s")){
                    pts++;
                }
            }
        }
        return pts;
    }
    private int farmer(TreeMap<HexNode, Integer> x){
        //int pts = 0;
        int TL = 0;
        int TR = 0;
        int BL = 0;
        int BR = 0;
        //sector with least
        for (int j : x.values()){
            if(j==0)
                TL++;
            if(j==1)
                TR++;
            if(j==2)
                BL++;
            if(j==3)
                BR++;
        }
        int least = Math.min(TL, Math.min(TR, Math.min(BL, BR)));
        return least*3;
    }
    private int knight(HexNode[][] hexMatrix){
        int highest = -1;
        for(int i =0; i<20; i++){
            int z = knightHelper(hexMatrix[i][0]);
            if (z>highest)
                highest=z;
        }
        return highest*2;
    }
    private int knightHelper(HexNode root){
        if(root==null)
            return 0;
        if(root.getNum()==playerNum)
            return 1 + knightHelper(root.getBordering(1));;
        return knightHelper(root.getBordering(1));
    }
    private int lord(TreeMap<HexNode, Integer>[] x){
        int score = 0;
        int[][] sr = new int[4][2];
        int[][] pp = new int[4][4];
        for(int i =0; i<4; i++){
            for(int v : x[i].values()){
                pp[i][v-1]++;
            }
        }
        for(int j = 0; j<4; j++){
            for(int i =0; i<4; i++){
                int first = 0;
                int second = 0;
                int p1 = -1;
                int p2 = -1;
                if(pp[i][j]>first){
                    first = pp[i][j];
                    p1 = i;
                }else if(pp[i][j]>second){
                    second = pp[i][j];
                    p2 = i;
                }
                sr[j][1]=p1;
                sr[j][2]=p2;
            }
        }
        for(int j = 0; j<4; j++){
            if(sr[j][0]==playerNum)
                score+=12;
            if(sr[j][1]==playerNum)
                score+=6;
        }
        return score;
    }
    public Image getSettlementImg() {
        return settlementImg;
    }

    public void startTurn() {

    }
    public void addSettlement() {settlements++;}
    public void removeSettlement() {settlements--;}

    public int getPlayerNum(){
        return playerNum;
    }
    public String getColor(){
        return color;
    }
}

