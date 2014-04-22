package commands;

import gui.MainController;

import java.util.Map;

import javafx.scene.layout.Pane;
import shapes.RectangleShape;
import shapes.TriangleShape;
import application.Preference;

public class CreateTriangle implements Command{
	private TriangleShape newTriangle;
	private Pane artBoard;
	
	public CreateTriangle(double mouseX, double mouseY, TriangleShape triangle, Pane artBoard) {
		this.artBoard = artBoard;
		this.newTriangle = triangle;
		newTriangle.getTriangle().getPoints().addAll(new Double[]{
				mouseX + MainController.preferences.get("width").getDoublePreference()/2, mouseY + MainController.preferences.get("height").getDoublePreference()/2,
				mouseX - MainController.preferences.get("width").getDoublePreference()/2, mouseY + MainController.preferences.get("height").getDoublePreference()/2,
				mouseX, mouseY - MainController.preferences.get("height").getDoublePreference()/2
		});
	}
	
	@Override
	public void execute() {
		/* save current state of artboard in Stack */
		//System.out.println("Geometry drawn: Ellipse(Radius: "+newTriangle.getRadius()+" Length: "+newTriangle.getWidth());
		artBoard.getChildren().add(newTriangle.getTriangle());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
	}

}
