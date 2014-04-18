package gui;

import java.net.URL;
import java.util.ResourceBundle;

import businesslayer.Businesslayer;
import bussinessobjects.GuiData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainController implements Initializable{
	
	/*Comboboxen*/
	@FXML private ComboBox<String> cbShape;
	@FXML private ComboBox<Integer> cbWeight;
	
	/*Textfelder*/
	@FXML TextField tfWidth;
	@FXML TextField tfLength;
	@FXML TextField tfRadius;
	
	@FXML Pane pCanvas;
	@FXML Pane pColorPicker;
	
	/*Buttons*/
	@FXML private Button bClear, bGroup, bShape, bEdit, bUndo, bRedo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Initialize Comboboxes */
		cbShape.getItems().addAll("Kreis", "Ellipse", "Dreieck", "Quadrat", "Rechteck");
		cbWeight.getItems().addAll(1,2,4,8,16,32);
		
		Canvas canvas = new Canvas(474,482);
		GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.ALICEBLUE);
        
//        final Text text = new Text("Try the color picker!");
//        text.setFont(Font.font ("Verdana", 20));
//        text.setFill(colorPicker.getValue());
        
//        colorPicker.setOnAction(new EventHandler() {
//            public void handle(Event t) {
//                text.setFill(colorPicker.getValue());               
//            }
//        });
 
        pColorPicker.getChildren().addAll(colorPicker);
		
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
	
	public void doAuswahl(){
		System.out.println("Auswahl getroffen.");
		
		GuiData g = new GuiData();
		
		/*TODO: Pen sperren wenn Form ausgewählt wurde und umgekehrt*/
		g.setShape(cbShape.getSelectionModel().getSelectedItem());
		//g.setPen(cbPen.getSelectionModel().getSelectedItem());
		
		g.setWidth(Integer.parseInt(tfWidth.getText()));
		g.setLength(Integer.parseInt(tfLength.getText()));
		
		Businesslayer b = new Businesslayer(g);
	}
	
}
