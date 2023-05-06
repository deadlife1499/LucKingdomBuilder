package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Board {
	private static Board board;
	private BoardGraph boardGraph;
	private int[] boardNums;
	private HexButton[][] buttonMatrix;
	private GameObject settlementObj;
	private int settlementsPlacedSinceReset;
	private ArrayList<HexNode> settlementQueue;
	private String[][] terrainMatrix;
	private ArrayList<TerrainCard> terrainCards;
	private GameObject terrainCardObj;
	private TerrainCard activeCard;

	public Board(){
		boardNums = new int[4];
		buttonMatrix = new HexButton[20][20];
		settlementsPlacedSinceReset = 0;
		settlementQueue = new ArrayList<>();
		terrainCards = new ArrayList<>();
		terrainCardObj = new GameObject();
		terrainCardObj.setBounds(120, 450, 117, 180);

		setBoardNums();
		displayBoard(354, 14, 1212, 1041);

		HashMap<Integer, String[][]> terrainMaps = buildMap();
		terrainMatrix = buildMatrix(terrainMaps);

		boardGraph = new BoardGraph(terrainMatrix);

		addHexButtons();
		createTerrainCards();
		drawTerrainCard();
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
					String token = sc.next();
					
					if(token.equals("s")) {
						tempArr[r][c] = sc.next();
					} else {
						tempArr[r][c] = token;
					}
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

	private void addHexButtons() {
		HexNode[][] hexMatrix = boardGraph.getMatrix();

		for(int r = 0; r < 20; r++) {
			for(int c = 0; c < 20; c++) {
				if(terrainMatrix[r][c].length() < 2) {
					createHexButton(hexMatrix, r, c, 384, 48, 1152, 973.75);
				} else {
					createActionTile(hexMatrix, r, c, 384, 48, 1152, 973.75);
				}
			}
		}
	}

	private HexButton createHexButton(HexNode[][] hexMatrix, int r, int c, double xOffset, double yOffset, double width, double height) {
		HexButton button = new HexButton(33, hexMatrix[r][c]);

		double hexWidth = (width - 31 * (width / 1152)) / 19;
		double hexHeight = height / 19;
		button.setBounds(xOffset + hexWidth * c + c / 10 * (1.5 * (width / 1152)) + r % 2 * (29.5 * (width / 1152)), yOffset + r * hexHeight);
		buttonMatrix[r][c] = button;
		//button.setOpacity(0);

		button.setOnMouseClicked(e -> {
			GUI gui = GUI.get();
			HexNode hexNode = button.getHexNode();

			if(settlementObj == null) {
				settlementObj = new GameObject();
				//settlementObj.setSortingLayer(2);
				DropShadow shadow = new DropShadow(30, Color.BLACK);
				shadow.setBlurType(BlurType.ONE_PASS_BOX);
				settlementObj.setEffect(shadow);
			}

			if(!hexNode.hasSettlement()) {
				TurnHandler turnHandler = TurnHandler.get();
				Player player = turnHandler.getCurrentPlayer();
				Image settlement = player.getSettlementImg();

				settlementObj.add(settlement, button.getLayoutX() - 28 + Math.random() * 16, button.getLayoutY() - 28 + Math.random() * 16, 
						settlement.getWidth() * (42 / settlement.getHeight()), 42);
	
				hexNode.addSettlement();
				settlementsPlacedSinceReset++;
				settlementQueue.add(hexNode);

				if(activeCard.isActive()) {
					activeCard.deactivateCard();
					activeCard.activateCard();
				}
				
				if(settlementsPlacedSinceReset == 3) {
					gui.setConfirmButtonDisable(false);
				}
				hexNode.checkAdjacent();
				Scoring.scoreCards();
			} else {
				settlementObj.removePreviousImages(1);
				hexNode.removeSettlement();
				settlementsPlacedSinceReset--;
				settlementQueue.remove(hexNode);

				if(activeCard.isActive()) {
					activeCard.deactivateCard();
					activeCard.activateCard();
				}
				
				if(settlementsPlacedSinceReset == 2) {
					gui.setConfirmButtonDisable(true);
				}
				hexNode.removeAdjacent();
				Scoring.scoreCards();
			}
		});
		//button.setDisable(true);
		button.setVisible(false);

		return button;
	}

	private void createActionTile(HexNode[][] hexMatrix, int r, int c, double xOffset, double yOffset, double width, double height) {
		ActionTile tile = new ActionTile(buttonMatrix[r][c], r, c, xOffset, yOffset, width, height, hexMatrix[r][c].getTerrainType());
		hexMatrix[r][c].setActionTile(tile);
	}

	private void createTerrainCards() {
		for(int i = 0; i < 25; i++) {
			terrainCards.add(new TerrainCard(i % 5));
		}
		Collections.shuffle(terrainCards);
	}

	public void drawTerrainCard() {
		TerrainCard card = terrainCards.remove(terrainCards.size() - 1);

		terrainCardObj.setImage(card.getImage());
		activeCard = card;
	}

	public BoardGraph getBoardGraph() {return boardGraph;}
	public GameObject getSettlementObj() {return settlementObj;}
	public int getSettlementsPlacedSinceReset() {return settlementsPlacedSinceReset;}
	public ArrayList<HexNode> getSettlementQueue() {return settlementQueue;}
	public HexButton[][] getButtonMatrix() {return buttonMatrix;}
	public TerrainCard getActiveCard() {return activeCard;}

	public void resetSettlementsPlaced() {
		settlementQueue.clear();
		settlementsPlacedSinceReset = 0;
	}

	public static Board get() {
		if(Board.board == null) {
			Board.board = new Board();
		}
		return Board.board;
	}
}










