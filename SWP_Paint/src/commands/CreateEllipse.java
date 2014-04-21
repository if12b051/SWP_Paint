package commands;

import gui.MainController;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import shapes.EllipseShape;
import application.Preference;

public class CreateEllipse implements Command{
	private EllipseShape newEllipse;
	private Pane artBoard;
	
	public CreateEllipse(Map<String, Preference> preferences, double mouseX, double mouseY, EllipseShape ellipse, Pane artBoard) {
		this.artBoard = artBoard;
		this.newEllipse = ellipse;
		newEllipse.getEllipse().setCenterX(mouseX);
		newEllipse.getEllipse().setCenterY(mouseY);
		newEllipse.getEllipse().setRadiusX(preferences.get("width").getDoublePreference());
		newEllipse.getEllipse().setRadiusY(preferences.get("radius").getDoublePreference());
		newEllipse.getEllipse().setFill(preferences.get("paint").getPaintPreference());
	}
	
	@Override
	public void execute() {
		/* save current state of artboard in Stack */
		//System.out.println("Geometry drawn: Ellipse(Radius: "+newEllipse.getRadius()+" Length: "+newEllipse.getWidth());
		artBoard.getChildren().add(newEllipse.getEllipse());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
	}

}
