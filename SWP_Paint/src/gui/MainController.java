package gui;

import java.net.URL;
import java.util.ResourceBundle;

import businesslayer.Businesslayer;
import bussinessobjects.GuiData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MainController implements Initializable{
	
	/*Comboboxen*/
	@FXML private ComboBox<String> cbColor;
	@FXML private ComboBox<String> cbShape;
	@FXML private ComboBox<Integer> cbWeight;
	
	/*Textfelder*/
	@FXML TextField tfWidth;
	@FXML TextField tfLength;
	@FXML TextField tfRadius;
	
	@FXML Pane pCanvas;
	
	/*Buttons*/
	@FXML private Button bClear, bGroup, bShape, bEdit, bUndo, bRedo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Initialize Comboboxes */
		cbShape.getItems().addAll("Kreis", "Ellipse", "Dreieck", "Quadrat", "Rechteck");
		cbColor.getItems().addAll("Rot", "Grün");
		cbWeight.getItems().addAll(1,2,4,8,16,32);
		
		Canvas canvas = new Canvas(474,482);
		GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
		
		pCanvas.getChildren().add(canvas);
	}
	
	private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                       new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                         new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                          new double[]{210, 210, 240, 240}, 4);
    }
	
	public void initComboboxWeight(){
		cbWeight.getItems().addAll(1,2,4,8,16,32);
	}
	
	public void start(){
		System.out.println("Applikation ist gestartet");
		
		GuiData g = new GuiData();
		
		/*TODO: Pen sperren wenn Form ausgewählt wurde und umgekehrt*/
		g.setColor(cbColor.getSelectionModel().getSelectedItem());
		g.setShape(cbShape.getSelectionModel().getSelectedItem());
		//g.setPen(cbPen.getSelectionModel().getSelectedItem());
		
		g.setWidth(Integer.parseInt(tfWidth.getText()));
		g.setLength(Integer.parseInt(tfLength.getText()));
		
		Businesslayer b = new Businesslayer(g);
	}
	
}
