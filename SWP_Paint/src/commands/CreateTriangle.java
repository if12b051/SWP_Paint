package commands;

import java.util.Map;

import javafx.scene.layout.Pane;
import shapes.RectangleShape;
import shapes.TriangleShape;
import application.Preference;

public class CreateTriangle implements Command{
	private TriangleShape newTriangle;
	private Pane artBoard;
	
	public CreateTriangle(Map<String, Preference> preferences, double mouseX, double mouseY, TriangleShape triangle, Pane artBoard) {
		this.artBoard = artBoard;
		this.newTriangle = triangle;
		newTriangle.getTriangle().getPoints().addAll(new Double[]{
				mouseX + preferences.get("width").getDoublePreference()/2, mouseY + preferences.get("height").getDoublePreference()/2,
				mouseX - preferences.get("width").getDoublePreference()/2, mouseY + preferences.get("height").getDoublePreference()/2,
				mouseX, mouseY - preferences.get("height").getDoublePreference()/2
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
