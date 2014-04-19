package commands;

import gui.MainController;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import shapes.CircleShape;
import shapes.EllipseShape;
import application.Preference;

public class CreateEllipse implements Command{
	
	GraphicsContext curArtBoard;
	EllipseShape newEllipse;
	
	public CreateEllipse(Map<String, Preference> preferences, double mouseX, double mouseY, EllipseShape ellipse, GraphicsContext artBoard) {
		curArtBoard = artBoard;
		newEllipse = ellipse;
		newEllipse.setCenterX(mouseX-preferences.get("length").getDoublePreference()/2);
		newEllipse.setCenterY(mouseY-preferences.get("radius").getDoublePreference()/2);
		newEllipse.setLength(preferences.get("length").getDoublePreference());
		newEllipse.setRadius(preferences.get("radius").getDoublePreference());
		newEllipse.setPaint(preferences.get("paint").getPaintPreference());
	}
	
	@Override
	public void execute() {
		/* save current state of artboard in Stack */
		curArtBoard.setFill(newEllipse.getPaint());
		
		curArtBoard.fillArc(newEllipse.getCenterX(), newEllipse.getCenterY(), newEllipse.getLength(), newEllipse.getRadius(), 0, 360, ArcType.OPEN);
		System.out.println("Geometry drawn: Ellipse(Radius: "+newEllipse.getRadius()+" Length: "+newEllipse.getLength());
	}

	@Override
	public void undo() {
		System.out.println("Action: undo - restoring last state");
		curArtBoard.restore();
	}

}
