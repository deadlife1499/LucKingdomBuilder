package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class Player {
    private int points;
    private final int playerNum;
    private int settlements;
    private final TreeSet<LocationTile> locTiles;
    private Image settlementImg;
    private final String color;
    private ArrayList<ActionTile> actionTiles;
    private boolean[] usedActionTile;

    public Player(int num, String color) {
        points = 0;
        playerNum = num;
        this.color = color;
        settlements = 40;
        locTiles = new TreeSet<>();
        actionTiles = new ArrayList<>();
        try {
            settlementImg = new Image(getClass().getResourceAsStream("/images/" + color.toLowerCase() + "Settlement.png"));
        } catch(NullPointerException e) {
            settlementImg = new Image(getClass().getResourceAsStream("/images/blueSettlement.png"));
        }
        usedActionTile= new boolean[4];
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
    public void score(HexNode[][] hexMatrix){
        int add = 0;
        add+=lord();
        System.out.println(add);
    }
    private int citizen(HexNode[][] hexMatrix){
        int high=0;
        for(int i = 0; i<20; i++){
            for(int j = 0; j<20; j++){
                if(!hexMatrix[i][j].getChecked(playerNum) && hexMatrix[i][j].getNum()==playerNum){
                    int k = citizenHelper(hexMatrix[i][j]);
                    if (k>high){
                        high = k;
                    }
                }
            }
        }
        return high/2;
    }
    private int citizenHelper(HexNode root){
        if(root!= null && root.getNum()==playerNum && !root.getChecked(playerNum)){
            //System.out.println(root.getTerrain());
            HexNode[] lol = new HexNode[6];
            root.setChecked(playerNum);
            for(int i = 0; i<6; i++){
                lol[i]=root.getBordering(i);
            }
            return + 1 + citizenHelper(lol[0]) + citizenHelper(lol[1]) + citizenHelper(lol[2]) + citizenHelper(lol[3]) + citizenHelper(lol[4]) + citizenHelper(lol[5]);
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
    private int fisherman(){
        int pts = 0;
        //x.get()
        TreeMap<Integer, ArrayList<HexNode>> x = Board.get().getPlayerMap(playerNum);
        for( ArrayList<HexNode> z : x.values()){
            for (HexNode node : z){
                boolean temp = false;
                for(int i =0; i<6; i++){
                    if(node.getBordering(i).getTerrain().equals("w"))
                        temp = true;
                }
                if (temp)
                    pts++;
            }
        }
        return pts;
    }
    private int miner(){
        int pts = 0;
        //x.get()
        TreeMap<Integer, ArrayList<HexNode>> x = Board.get().getPlayerMap(playerNum);
        for( ArrayList<HexNode> z : x.values()){
            for (HexNode node : z){
                boolean temp = false;
                for(int i =0; i<6; i++){
                    if(node.getBordering(i).getTerrain().equals("m"))
                        temp = true;
                }
                if (temp)
                    pts++;
            }
        }
        return pts;
    }
    private int worker(){
        String lmfao = "c d w m t f g";
        int pts = 0;
        //x.get()
        TreeMap<Integer, ArrayList<HexNode>> x = Board.get().getPlayerMap(playerNum);
        for( ArrayList<HexNode> z : x.values()){
            for (HexNode node : z){
                boolean temp = false;
                for(int i =0; i<6; i++){
                    if(node.getBordering(i)!=null)
                        if (!lmfao.contains(node.getBordering(i).getTerrain())) {
                            temp = true;
                        }
                }
                if (temp)
                    pts++;
            }
        }
        return pts;
    }
    private int farmer(){
        //int pts = 0;
        TreeMap<Integer, ArrayList<HexNode>> x = Board.get().getPlayerMap(playerNum);
        int TL = 0;
        int TR = 0;
        int BL = 0;
        int BR = 0;
        //sector with least
        TL+=x.get(0).size();

        TR+=x.get(1).size();

        BL+=x.get(2).size();

        BR+=x.get(3).size();
        int least = 0;
        if(TL > 0 && TR > 0 && BL > 0 && BR > 0)
            least = Math.min(TL, Math.min(TR, Math.min(BL, BR)));
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
            return 1 + knightHelper(root.getBordering(1));
        return knightHelper(root.getBordering(1));
    }
    private int lord(){
        ArrayList<TreeMap<Integer, ArrayList<HexNode>>> x = Board.get().getPlayerMaps();
        int score = 0;
        int[][] sr = new int[4][4];
        for(int i = 0; i<4;i++){
            for (int j = 0; j<4; j++){
                sr[i][j]=-5;
            }
        }
        int[][] pp = new int[4][4];
        for(int i =0; i<4; i++){
            for(int j =0; j<4; j++){
                pp[i][j] = x.get(i).get(j).size();
                //System.out.print(pp[i][j]  + " ");
            }
            //System.out.println();
        }
        //System.out.println();
        // row is player, column is sector
        for(int i = 0; i<4; i++){
            // i iterates through sector
            int sectorMax = 0;
            int sectorSecond = 0;
            int sectorThird = 0;
            int sectorFourth = 0;
            int sectorMaxPlayer = 5;
            int sectorSecondPlayer = 5;
            int sectorThirdPlayer = 5;
            int sectorFourthPlayer = 5;
            for(int j = 0; j<4; j++){
                // iterates each player
                if(pp[i][j]>sectorMax){
                    sectorMax=pp[i][j];
                    sectorMaxPlayer=j;
                }
                else if(pp[i][j]>sectorSecond){
                    sectorSecond = pp[i][j];
                    sectorSecondPlayer = j;
                }
                else if(pp[i][j]>sectorThird){
                    sectorThird = pp[i][j];
                    sectorThirdPlayer = j;
                }
                else if(pp[i][j]>sectorFourth){
                    sectorFourth = pp[i][j];
                    sectorFourthPlayer = j;
                }
            }
            //System.out.println(sectorMaxPlayer);
            sr[i][0] = sectorMaxPlayer;
            sr[i][1] = sectorSecondPlayer;
            sr[i][2] = sectorThirdPlayer;
            sr[i][3] = sectorFourthPlayer;
            //System.out.println();
        }
        //System.out.println(playerNum);
        //System.out.println();
        for(int j = 0; j<4; j++){
            //System.out.println(j + " " + sr[j][0]);
            if(sr[j][0]==playerNum)
                score+=12;
            else if(sr[j][1]==playerNum)
                score+=6;
        }
        return score;
    }
    public Image getSettlementImg() {
        return settlementImg;
    }

    public void addSettlement() {settlements++;}
    public void removeSettlement() {settlements--;}

    public int getPlayerNum(){
        return playerNum;
    }
    public String getColor(){
        return color;
    }
    //public Image getSettlementImg() {return settlementImg;}
    public void startTurn() {
        GUI gui = GUI.get();
        ObservableList<Node> moveSelectionList = gui.getMoveSelectionBox().getChildren();

        moveSelectionList.clear();
        Button addSettlements = new Button();
        addSettlements.setPrefSize(60, 60);

        ImageView settlementImg = new ImageView(getSettlementImg());
        settlementImg.setFitWidth(60);
        settlementImg.setFitHeight(40);
        addSettlements.setGraphic(settlementImg);

        addSettlements.setOnAction(e -> {
            Board board = Board.get();

            gui.setConfirmButtonsVisible(true);

            TerrainCard card = board.getActiveCard();
            if(!card.isActive()) {
                card.activateCard();
            }
        });
        moveSelectionList.add(addSettlements);
    }
    public void createActionTiles(){
        double xOffset = 200.0;
        double yOffset = 200.0;
        int width = 1920;
        int height = 1080;
        //HexNode[][] matrix = boardGraph.getMatrix();
        //matrix[r][c].setHexButton(button);
        double hexWidth = 50;
        double hexHeight = 50;
        for(int i =0; i<actionTiles.size(); i++){
            ActionTile tile = actionTiles.get(i);
            tile.setBounds(200+60*i, 200);
                tile.setOnMouseClicked(e -> {
                    if (Board.get().getSettlementsPlacedSinceReset()==Board.get().getSettlementLimit() || Board.get().getSettlementsPlacedSinceReset()==0){
                        if (!tile.isUsed()){
                            if (tile.getType().equals("oracle")){
                                Board.get().allowAdditionalSettlement();
                            }
                            tile.setUsed(true);
                            Board.get().getActiveCard().deactivateCard();
                            Board.get().getActiveCard().activateCard();
                        }
                    }
                });
        }
        //buttonMatrix[r][c] = button;
    }
    public void addActionTiles(String type){
        actionTiles.add(new ActionTile(33, type));
    }
    public boolean usedActionTile(){
        for (boolean x: usedActionTile){
            if(x){
                return x;
            }
        }
        return false;
    }
    public void usedActionTile(boolean x){
        for(int i =0;i<4; i++){
            usedActionTile[i]=false;
        }
    }
}

