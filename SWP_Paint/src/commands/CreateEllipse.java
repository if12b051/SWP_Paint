package commands;

import gui.MainController;

import java.util.ArrayList;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import shapes.EllipseShape;
import shapes.ShapeComponent;
import application.Preference;

public class CreateEllipse implements Command{
	private EllipseShape newEllipse;
	private Pane artBoard;
	
	public CreateEllipse(double mouseX, double mouseY, EllipseShape ellipse, Pane artBoard) {
		this.artBoard = artBoard;
		this.newEllipse = ellipse;
		newEllipse.getEllipse().setCenterX(mouseX);
		newEllipse.getEllipse().setCenterY(mouseY);
		newEllipse.getEllipse().setRadiusX(MainController.preferences.get("width").getDoublePreference());
		newEllipse.getEllipse().setRadiusY(MainController.preferences.get("radius").getDoublePreference());
		newEllipse.getEllipse().setFill(MainController.preferences.get("paint").getPaintPreference());
		newEllipse.setFirstWidth(newEllipse.getEllipse().getRadiusX());
		newEllipse.setFirstRadius(newEllipse.getEllipse().getRadiusY());
		newEllipse.setResizeValue(100);
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
