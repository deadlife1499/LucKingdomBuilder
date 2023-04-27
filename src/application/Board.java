package application;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {
	private BoardGraph boardGraph;
	private int[] boardNums;
    private HexNode root;
    private HexButton[][] buttonMatrix;
    private GameObject settlementObj;
    private int settlementsPlacedSinceReset;
    private ArrayList<HexNode> settlementQueue;

	private ArrayList<HexNode> current;
    
    public Board(){
    	boardNums = new int[4];
    	buttonMatrix = new HexButton[20][20];
    	settlementsPlacedSinceReset = 0;
    	settlementQueue = new ArrayList<>();
		current = new ArrayList<>();
        setBoardNums();
        displayBoard(354, 14, 1212, 1041);
        
    	HashMap<Integer, String[][]> terrainMaps = buildMap();
        String[][] terrainMatrix = buildMatrix(terrainMaps);
        
        boardGraph = new BoardGraph(terrainMatrix);
        
        addHexButtons();
    }
    
    private void setBoardNums() {
    	for(int i = 0; i < 4; i++) {
        	int num = (int)(Math.random() * 8 + 1);
        	
        	for(int j = 0; j < 4; j++) {
        		if(boardNums[j] == num) {
                	num = (int)(Math.random() * 8 + 1);
                	j = 0;
        		}
        	}
        	boardNums[i] = num;
        }
    }
    
    private void displayBoard(double x, double y, double width, double height) {
    	//354, 14, 1212, 1041
    	//620 x 528
    	Image backImg = new Image(getClass().getResourceAsStream("/images/BlackSquare.png"));
    	GameObject obj = new GameObject(backImg, 384, 30, 1151, 1008, 1);
    	
    	for(int i = 0; i < 4; i++) {
    		Image boardImg = new Image(getClass().getResourceAsStream("/images/board" + boardNums[i] + ".png"));
    		obj.add(boardImg, x + 591.2 * (i % 2), y + 512.5 * (i / 2), 620, 528);
    	}
    	obj.setEffect(new DropShadow(10, Color.BLACK));
    	obj.setBounds(x, y, width, height);
    }
    
    private HashMap<Integer, String[][]> buildMap() {
    	HashMap<Integer, String[][]> tempMap = new HashMap<>();
    	
    	for(int i = 0; i<4; i++) {
        	String[][] tempArr = new String[10][10];
        	File board = new File("src/boards/board" + boardNums[i] + ".txt");
        	Scanner sc = null;
        	
			try {
				sc = new Scanner(board);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        	
        	for(int r = 0; r < tempArr.length; r++) {
        		for(int c = 0; c < tempArr[0].length; c++) {
        			tempArr[r][c] = sc.next();
        		}
        	}
        	
        	tempMap.put(i, tempArr);
        }
    	return tempMap;
    }

	/**
	 * Sowa qhRAFdsaf
	 * @param terrainMaps mPA RWRajN
	 * @return THING
	 */
	private String[][] buildMatrix(HashMap<Integer, String[][]> terrainMaps) {
    	String[][] tempMatrix = new String[20][20];
    	
    	for(int r = 0; r < tempMatrix.length; r++) {
        	for(int c = 0; c < tempMatrix[0].length; c++) {
        		if(r < 10) {
        			if(c < 10) {
        				tempMatrix[r][c] = terrainMaps.get(0)[r][c];
        			} else {
        				tempMatrix[r][c] = terrainMaps.get(1)[r][c - 10];
        			}
        		} else {
        			if(c < 10) {
        				tempMatrix[r][c] = terrainMaps.get(2)[r - 10][c];
        			} else {
        				tempMatrix[r][c] = terrainMaps.get(3)[r - 10][c - 10];
        			}
        		}
        	}
        }
    	return tempMatrix;
    }
    
    private void addHexButtons() {
    	HexNode[][] hexMatrix = boardGraph.getMatrix();
    	
    	for(int r = 0; r < 20; r++) {
    		for(int c = 0; c < 20; c++) {
    			createHexButton(hexMatrix, r, c);
    		}
    	}
		//System.out.println(hexMatrix[0][0].toString());
    }
    
    private HexButton createHexButton(HexNode[][] hexMatrix, int r, int c) {
    	HexButton button = new HexButton(33, hexMatrix[r][c]);
		
		button.setBounds(384 + 59 * c + c / 10 * 1.5 + r % 2 * 29.5, 48 + r * 51.25);
		buttonMatrix[r][c] = button;
		button.setOpacity(0);
		
		button.setOnMouseClicked(e -> {
			HexNode hexNode = button.getHexNode();
			
			if(settlementObj == null) {
				settlementObj = new GameObject();
				//settlementObj.setSortingLayer(2);
				settlementObj.setEffect(new DropShadow(10, Color.BLACK));
			}
			
			if(hexNode.canPlaceSettlement() && settlementsPlacedSinceReset < 3 && !hexNode.getTerrain().equals("m") && !hexNode.getTerrain().equals("w")) {
				TurnHandler turnHandler = TurnHandler.get();
				Player player = turnHandler.getCurrentPlayer();
				Image settlement = player.getSettlementImg();
				
				settlementObj.add(settlement, button.getLayoutX() - 35, button.getLayoutY() - 20, 73.6, 46);
				hexNode.addSettlement();
				settlementsPlacedSinceReset++;
				settlementQueue.add(hexNode);
				current.add(hexNode);
				System.out.println(hexMatrix[0][0].toPlayerString());
			} else if(hexNode.hasSettlement() && !hexNode.isConfirmed()) {
				settlementObj.removeImgAt(button.getLayoutX() - 35, button.getLayoutY() - 20, 73.6, 46);
				hexNode.removeSettlement();
				settlementsPlacedSinceReset--;
				settlementQueue.remove(hexNode);
				current.remove(hexNode);
			}
		});
		
		return button;
    }
    
    public int getSettlementsPlacedSinceReset() {
    	return settlementsPlacedSinceReset;
    }
    
    public void resetSettlementsPlaced() {
    	settlementsPlacedSinceReset = 0;
    }
    
    public HexNode getRoot(){
        return root;
    }

	public void confirmPlacements(){
		for(int i =0;i<current.size(); i++){
			current.get(i).setConfirmed();
		}
		current = new ArrayList<>();
	}
}










