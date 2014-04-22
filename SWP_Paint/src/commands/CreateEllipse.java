package commands;

import gui.MainController;
import javafx.scene.layout.Pane;
import shapes.EllipseShape;

public class CreateEllipse implements Command{
	private EllipseShape newEllipse;
	private Pane artBoard;
	
	public CreateEllipse(double mouseX, double mouseY, EllipseShape ellipse, Pane artBoard) {
		this.artBoard = artBoard;
		this.newEllipse = ellipse;
		newEllipse.getEllipse().setCenterX(mouseX);
		newEllipse.getEllipse().setCenterY(mouseY);
		if(MainController.preferences.get("tool").getStringPreference().equals("Circle")) {
			newEllipse.getEllipse().setRadiusY(MainController.preferences.get("radius").getDoublePreference());
			newEllipse.getEllipse().setRadiusX(MainController.preferences.get("radius").getDoublePreference());
		}
		else {
			newEllipse.getEllipse().setRadiusX(MainController.preferences.get("width").getDoublePreference());
			newEllipse.getEllipse().setRadiusY(MainController.preferences.get("radius").getDoublePreference());
		}
		newEllipse.getEllipse().setFill(MainController.preferences.get("paint").getPaintPreference());
	}
	
	@Override
	public void execute() {
		artBoard.getChildren().add(newEllipse.getEllipse());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
	}

}
