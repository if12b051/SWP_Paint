package commands;

import gui.MainController;
import javafx.scene.canvas.GraphicsContext;

public class ClearAll implements Command{
	
	GraphicsContext curArtBoard;
	
	public ClearAll(GraphicsContext artBoard) {
		curArtBoard = artBoard;
	}
	
	@Override
	public void execute() {
		curArtBoard.clearRect(0, 0, MainController.CANVAS_WIDTH, MainController.CANVAS_HEIGHT);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}



}
