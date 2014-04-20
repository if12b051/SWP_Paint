package commands;

import gui.MainController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class ClearAll implements Command{
	
	Pane curArtBoard;
	
	public ClearAll(Pane artBoard) {
		
		curArtBoard = artBoard;
	}
	
	@Override
	public void execute() {
		//curArtBoard.clearRect(0, 0, MainController.CANVAS_WIDTH, MainController.CANVAS_HEIGHT);
		curArtBoard.getChildren().removeAll();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}



}
