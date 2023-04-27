package application;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.image.Image;

public class TerrainCard {
	private Image cardImage;
	private String terrainToken;
	private boolean isActive;
	
	public TerrainCard(int type) {
		isActive = false;
		terrainToken = createToken(type);
	}
	
	private String createToken(int type) {
		String temp = "";
		
		switch(type) {
		case 0:
			temp = "c";
			cardImage = new Image(getClass().getResourceAsStream("/images/KB-Card-Canyon.png"));
			break;
			
		case 1:
			temp = "d";
			cardImage = new Image(getClass().getResourceAsStream("/images/KB-Card-Desert.png"));
			break;
			
		case 2:
			temp = "f";
			cardImage = new Image(getClass().getResourceAsStream("/images/KB-Card-Flower.png"));
			break;
			
		case 3:
			temp = "t";
			cardImage = new Image(getClass().getResourceAsStream("/images/KB-Card-Forest.png"));
			break;
			
		case 4:
			temp = "g";
			cardImage = new Image(getClass().getResourceAsStream("/images/KB-Card-Meadow.png"));
			break;
		}
		
		return temp;
	}
	
	public Image getImage() {return cardImage;}
	public boolean isActive() {return isActive;}
	
	public void activateCard() {
		Board board = Board.get();
		HexButton[][] buttonMatrix = board.getButtonMatrix();
		HashSet<HexButton> buttonSet = new HashSet<>();
		
		//for(HexButton[] row : buttonMatrix) {
			//for(HexButton button : row) {
		for(int r = 0; r < buttonMatrix.length; r++) {
			for(int c = 0; c < buttonMatrix[r].length; c++) {
				HexButton button = buttonMatrix[r][c];
				HexNode node = button != null? button.getHexNode() : null;
				TurnHandler turnHandler = TurnHandler.get();
				ArrayList<HexNode> settlementQueue = board.getSettlementQueue();
				
				if(node == null) {}
				else if((board.getSettlementsPlacedSinceReset() < 3 || 
						node.hasSettlement()) &&
						node.getTerrainType().equals(terrainToken) && 
						!(node.hasSettlement() && 
						(!settlementQueue.isEmpty()? !settlementQueue.get(settlementQueue.size() - 1).equals(node) : true)) &&
						hasBorderingSettlement(button) || 
						(node.hasSettlement() && 
						node.getPlayerNum() == turnHandler.getCurrentPlayer().getPlayerNum() && 
						(!settlementQueue.isEmpty()? settlementQueue.get(settlementQueue.size() - 1).equals(node) : false))) {
					//button.setDisable(false);
					button.setVisible(true);
					buttonSet.add(button);
				}
				if(node != null)
				System.out.println(r + " " + c + " " +
						(board.getSettlementsPlacedSinceReset() < 3) + " |1| " +
						(node.hasSettlement()) + " &2& " +
						(node.getTerrainType().equals(terrainToken)) + " &3& " + 
						!(node.hasSettlement()) + " &4& " +
						(!settlementQueue.isEmpty()? !settlementQueue.get(settlementQueue.size() - 1).equals(node) : true) + " &5& " +
						!(node.hasSettlement() && 
						(!settlementQueue.isEmpty()? !settlementQueue.get(settlementQueue.size() - 1).equals(node) : true)) + " E6E " +
						hasBorderingSettlement(button) + " |7| " + 
						((node.hasSettlement() && 
						node.getPlayerNum() == turnHandler.getCurrentPlayer().getPlayerNum() && 
						(!settlementQueue.isEmpty()? settlementQueue.get(settlementQueue.size() - 1).equals(node) : false))));
			}
			System.out.println();
		}
		
		if(buttonSet.isEmpty() || 
				(buttonSet.size() == 1 && 
				buttonSet.stream().findFirst().get().getHexNode().hasSettlement() && 
				board.getSettlementsPlacedSinceReset() < 3)) {
			for(HexButton[] row : buttonMatrix) {
				for(HexButton button : row) {
					HexNode node = button != null? button.getHexNode() : null;
					TurnHandler turnHandler = TurnHandler.get();
					ArrayList<HexNode> settlementQueue = board.getSettlementQueue();
					
					if(node == null) {}
					else if(node.getTerrainType().equals(terrainToken) && 
							!(node.hasSettlement() && 
							node.getPlayerNum() != turnHandler.getCurrentPlayer().getPlayerNum()) && 
							!(node.hasSettlement() && 
							node.getPlayerNum() == turnHandler.getCurrentPlayer().getPlayerNum() && 
							(!settlementQueue.isEmpty()? !settlementQueue.get(settlementQueue.size() - 1).equals(node) : true))) {
						//button.setDisable(false);
						button.setVisible(true);
					} 
				}
			}
		} else {
			buttonSet.clear();
		}
		isActive = true;
	}
	
	public void deactivateCard() {
		Board board = Board.get();
		HexButton[][] buttonMatrix = board.getButtonMatrix();
		
		for(HexButton[] row : buttonMatrix) {
			for(HexButton button : row) {
				//button.setDisable(true);
				if(button != null) {
					button.setVisible(false);
				}
			}
		}
		isActive = false;
	}
	
	private boolean hasBorderingSettlement(HexButton button) {
		HexNode[] bordering = button.getHexNode().getBordering();
		
		for(int i = 0; i < bordering.length; i++) {
			HexNode node = bordering[i];
			TurnHandler turnHandler = TurnHandler.get();
			
			if(node != null && node.hasSettlement() && node.getPlayerNum() == turnHandler.getCurrentPlayer().getPlayerNum()) {
				return true;
			}
		}
		return false;
	}
}











