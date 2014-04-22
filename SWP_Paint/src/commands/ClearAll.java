package commands;

import gui.MainController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
		
		ObservableList<Node> myList = curArtBoard.getChildren();
		
		curArtBoard.getChildren().removeAll(myList);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}



}
