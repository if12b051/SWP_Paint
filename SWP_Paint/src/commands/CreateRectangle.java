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
import shapes.RectangleShape;
import application.Preference;

public class CreateRectangle implements Command{
	private RectangleShape newRectangle;
	private Pane artBoard;
	
	public CreateRectangle(Map<String, Preference> preferences, double mouseX, double mouseY, RectangleShape rectangle, Pane artBoard) {
		this.artBoard = artBoard;
		this.newRectangle = rectangle;
		newRectangle.getRectangle().setX(mouseX - preferences.get("width").getDoublePreference()/2);
		newRectangle.getRectangle().setY(mouseY - preferences.get("height").getDoublePreference()/2);
		newRectangle.getRectangle().setHeight(preferences.get("height").getDoublePreference());
		newRectangle.getRectangle().setWidth(preferences.get("width").getDoublePreference());
		newRectangle.getRectangle().setFill(preferences.get("paint").getPaintPreference());
	}
	
	@Override
	public void execute() {
		/* save current state of artboard in Stack */
		//System.out.println("Geometry drawn: Ellipse(Radius: "+newRectangle.getRadius()+" Length: "+newRectangle.getWidth());
		artBoard.getChildren().add(newRectangle.getRectangle());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
	}

}
