package commands;

import gui.MainController;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import shapes.EllipseShape;
import shapes.RectangleShape;
import application.Preference;

public class CreateRectangle implements Command{
	
	GraphicsContext curArtBoard;
	RectangleShape newRect;
	
	public CreateRectangle(Map<String, Preference> preferences, double mouseX, double mouseY, RectangleShape rect, GraphicsContext artBoard) {
		curArtBoard = artBoard;
		newRect = rect;
		newRect.setCenterX(mouseX - preferences.get("width").getDoublePreference()/2);
		newRect.setCenterY(mouseY - preferences.get("height").getDoublePreference()/2);
		newRect.setWidth(preferences.get("width").getDoublePreference());
		newRect.setHeight(preferences.get("height").getDoublePreference());
		newRect.setPaint(preferences.get("paint").getPaintPreference());
	}
	
	@Override
	public void execute() {
		/* save current state of artboard in Stack */
		curArtBoard.setFill(newRect.getPaint());
		
		curArtBoard.fillRect(newRect.getCenterX(), newRect.getCenterY(), newRect.getWidth(), newRect.getHeight());
		System.out.println("Geometry drawn: Ellipse(Width: "+newRect.getWidth()+" Height: "+newRect.getHeight());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
		curArtBoard.restore();
	}

}
