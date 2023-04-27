package application;

public class GUI {
	public GUI(Board board) {
		GameButton confirmButton = new GameButton("Confirm");
		confirmButton.setBounds(100, 800, 200, 100);
		
		confirmButton.setOnAction(e -> {
			if(board.getSettlementsPlacedSinceReset() == 3) {
				TurnHandler turnHandler = TurnHandler.get();
			
				turnHandler.nextTurn();
				board.resetSettlementsPlaced();
				board.confirmPlacements();
			}
		});
		
		GameButton cancelButton = new GameButton("Cancel");
		cancelButton.setBounds(100, 950, 200, 100);
		
		cancelButton.setOnAction(e -> {
			
		});
	}
}
