package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.scene.image.Image;

public class Scoring {
	private static HashMap<String, Boolean> cardMap;
	private static HexNode[][] hexMatrix;
	private static HashMap<HexNode, Integer>[] playerSettlements;
	private static HashMap<HexNode, Integer> currentPlayerSettlements;
	
	@SuppressWarnings("unchecked")
	public static void scoreCards() {
		TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
		
		if(cardMap == null) {
			Board board = Board.get();
			playerSettlements = new HashMap[4];
			
			for(int i = 0; i < 4; i++) {
				playerSettlements[i] = new HashMap<>();
			}
			
			buildMap();
			hexMatrix = board.getBoardGraph().getMatrix();
			displayCards();
		}
		updatePlayerSettlements();
		int score = 0;
		
		for(Entry<String, Boolean> cardEntry : cardMap.entrySet()) {
			if(cardEntry.getValue()) {
				score += score(cardEntry.getKey());
			}
		}
		player.setScore(score);
		player.updateScore();
	}
	
	private static void buildMap() {
		cardMap = new HashMap<>();
		String[] cardNames = {"citizen", "discoverer", "fisherman", "miner", "worker", "farmer", "knight", "lord"};
		
		for(String cardName : cardNames) {
			cardMap.put(cardName, false);
		}
		List<String> cardNameList = Arrays.asList(cardNames);
		Collections.shuffle(cardNameList);
		
		for(int i = 0; i < 3; i++) {
			cardMap.replace(cardNameList.get(i), true);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void updatePlayerSettlements() {
		TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
		
		for(int i = 0; i < 4; i++) {
			playerSettlements[i].clear();
		}
		
		for(HexNode[] row : hexMatrix) {
			for(HexNode node : row) {
				if(node.hasSettlement()) {
					playerSettlements[node.getPlayerNum()].put(node, node.getSector());
				}
			}
		}
		currentPlayerSettlements = (HashMap<HexNode, Integer>)playerSettlements[player.getPlayerNum()].clone();
	}
	
	private static int score(String cardName) {
		int score = 0;
		
		switch(cardName) {
		case "citizen":
			score += scoreCitizen();
			
			break;
		case "discoverer":
			score += scoreDiscoverer();
			
			break;
		case "fisherman":
			score += scoreFisherman();
			
			break;
		case "miner":
			score += scoreMiner();
			
			break;
		case "worker":
			score += scoreWorker();
			
			break;
		case "farmer":
			score += scoreFarmer();
			
			break;
		case "knight":
			score += scoreKnight();
			
			break;
		case "lord":
			score += scoreLord();
			
			break;
		}
		return score;
	}
	
	public static void displayCards() {
		GameObject cardObj = new GameObject();
		int cardY = 150;
		
		for(Entry<String, Boolean> cardEntry : cardMap.entrySet()) {
			if(cardEntry.getValue()) {
				Image cardImg = new Image("/images/" + cardEntry.getKey() + "Objective.png");
				
				cardObj.add(cardImg, 1650, cardY, cardImg.getWidth() / 2, cardImg.getHeight() / 2);
				cardY += 300;
			}
		}
	}
	
	private static int scoreCitizen() {
		TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
        int high = 0;
        
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                if(!hexMatrix[i][j].getChecked(player.getPlayerNum()) && hexMatrix[i][j].getPlayerNum() == player.getPlayerNum()) {
                    int k = citizenHelper(hexMatrix[i][j]);
                    
                    if (k > high) {
                        high = k;
                    }
                }
            }
        }
        return high / 2;
    }
	
    private static int citizenHelper(HexNode root) {
    	TurnHandler turnHandler = TurnHandler.get();
    	Player player = turnHandler.getCurrentPlayer();
    	
        if(root == null || root.getPlayerNum() != player.getPlayerNum()) {
            return 0;
        }
        HexNode[] bordering = root.getBordering();
        
        for(int i = 0; i < 6; i++) {
            bordering[i].setChecked(player.getPlayerNum());
        }
        
        if (root.getPlayerNum() == player.getPlayerNum()) {
            return 1 + citizenHelper(bordering[0]) + 
            		citizenHelper(bordering[1]) + 
            		citizenHelper(bordering[2]) + 
            		citizenHelper(bordering[3]) + 
            		citizenHelper(bordering[4]) + 
            		citizenHelper(bordering[5]);
        }
        return 0;
    }
    
    private static int scoreDiscoverer() {
        int count = 0;
        
        for(int i = 0; i < 20; i++) {
            if(discovererHelper(hexMatrix[i][0])) {
                count++;
            }
        }
        return count;
    }
    
    private static boolean discovererHelper(HexNode root) {
    	TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
    	
        if(root == null) {
            return false;
        }
        
        if(root.getPlayerNum() == player.getPlayerNum()) {
            return true;
        }
        return discovererHelper(root.getBordering(1));
    }
    
    private static int scoreFisherman() {
        int count = 0;
        
        for( HexNode node : currentPlayerSettlements.keySet()) {
            for(int i =0; i<6; i++) {
                if(node.getBordering(i).toString().equals("w")){
                    count++;
                }
            }
        }
        return count;
    }
    
    private static int scoreMiner() {
        int count = 0;
        
        for( HexNode node : currentPlayerSettlements.keySet()) {
            for(int i =0; i<6; i++) {
                if(node.getBordering(i).toString().equals("m")) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static int scoreWorker() {
        int count = 0;
        
        for(HexNode node : currentPlayerSettlements.keySet()) {
            for(int i = 0; i < 6; i++) {
                if(node.getBordering(i).toString().equals("s")) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private static int scoreFarmer() {
    	int[] tileCounts = new int[4];
        
        for (int num : currentPlayerSettlements.values()) {
        	for(int i = 0; i < 4; i++) {
        		if(num == i) {
        			tileCounts[i]++;
        		}
        	}
        }
        int minValue = Math.min(Math.min(tileCounts[0], tileCounts[1]), Math.min(tileCounts[2], tileCounts[3]));
        
        return minValue * 3;
    }
    
    private static int scoreKnight() {
        int highest = -1;
        
        for(int i = 0; i < 20; i++) {
            int z = knightHelper(hexMatrix[i][0]);
            
            if(z > highest) {
                highest = z;
            }
        }
        return highest * 2;
    }
    
    private static int knightHelper(HexNode root) {
    	TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
    	
        if(root==null) {
            return 0;
        }
        
        if(root.getPlayerNum() == player.getPlayerNum()) {
            return 1 + knightHelper(root.getBordering(1));
        }
        return knightHelper(root.getBordering(1));
    }
    
    private static int scoreLord() {
    	TurnHandler turnhandler = TurnHandler.get();
		Player player = turnhandler.getCurrentPlayer();
		
        int score = 0;
        int[][] sr = new int[4][2];
        int[][] pp = new int[4][4];
        
        for(int i = 0; i < 4; i++) {
            for(int v : playerSettlements[i].values()) {
                pp[i][v-1]++;
            }
        }
        
        for(int j = 0; j < 4; j++) {
            for(int i = 0; i < 4; i++) {
                int first = 0;
                int second = 0;
                int p1 = -1;
                int p2 = -1;
                
                if(pp[i][j]>first) {
                    first = pp[i][j];
                    p1 = i;
                } else if(pp[i][j]>second) {
                    second = pp[i][j];
                    p2 = i;
                }
                sr[j][0] = p1;
                sr[j][1] = p2;
            }
        }
        
        for(int j = 0; j<4; j++) {
            if(sr[j][0] == player.getPlayerNum()) {
                score += 12;
            }
            if(sr[j][1] == player.getPlayerNum()) {
                score += 6;
            }
        }
        return score;
    }
}
