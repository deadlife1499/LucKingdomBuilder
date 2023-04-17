package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Board {
	private BoardGraph graph;
	private int[] boardNums;
    private HexNode root;
    
    public Board(){
    	boardNums = new int[4];
        setBoardNums();
        displayBoard(354, 14, 1212, 1041);
        
    	HashMap<Integer, String[][]> terrainMaps = buildMap();
        String[][] terrainMatrix = buildMatrix(terrainMaps);
        
        graph = new BoardGraph(terrainMatrix);
        
        /*
        System.out.println(graph);
        
        System.out.println();
        String str = "";
        for(int i = 0; i < boardNums.length; i++) {
        	str += boardNums[i] + ", ";
        }
        System.out.println(str.substring(0, str.length() - 2));
        */
        
        HexButton hexButton = new HexButton(100);
        hexButton.setBounds(100, 100, 100);
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
    
    public HexNode getRoot(){
        return root;
    }
	public BoardGraph getGraph(){
		return graph;
	}
}
