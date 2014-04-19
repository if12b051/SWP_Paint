package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

import commands.ClearAll;
import commands.Command;
import commands.CreateEllipse;
import commands.EditShape;
import commands.GroupShapes;
import shapes.CircleShape;
import shapes.CloneFactory;
import shapes.EllipseShape;
import application.Preference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainController implements Initializable{
	
	private final String[] TOOL_TYPES = {"Circle", "Ellipse", "Triangle", "Square", "Rectangle", "Pencil"};
	public final static int canvasWidth=474, canvasHeight=482;
	
	/* GUI Objects */
	@FXML private ComboBox<String> cbTools;
	@FXML private Slider sSize, sWidth, sLength, sRadius;
	@FXML private Label lblSize, lblWidth, lblLength, lblRadius;
	@FXML private Pane pCanvas, pColorPicker;
	@FXML private Button bClear, bGroup, bShape, bEdit, bUndo, bRedo;
	private final ColorPicker colorPicker = new ColorPicker();
	private GraphicsContext artBoard;
	private Canvas canvas;
	
	/* important general objects */
	private Stack<Command> undo;
    private Stack<Command> redo;
    private Map<String, Preference> preferences; //"action", "tool", "paint", "size", "radius", "width", "length"
    private CloneFactory cloneFactory;
    
    /* geometry prototypes */
	private EllipseShape ellipsePrototype;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Initialize Combobox and set a value */
		cbTools.getItems().addAll(TOOL_TYPES);
		cbTools.setValue("Circle");
		
		/* initialize canvas */
		canvas = new Canvas(canvasWidth,canvasHeight);
		artBoard = canvas.getGraphicsContext2D();
        drawShapes(artBoard); //test
        
        /* initalize general objects */
        undo = new Stack<Command>();
        redo = new Stack<Command>();
        preferences = new HashMap<String, Preference>();
        cloneFactory = new CloneFactory();
        
        /* initialize prototypes */
        ellipsePrototype = new EllipseShape();
        
        /* add eventListeners to canvas for: CLICKED and DRAGGED*/
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double mouseX, mouseY;
				Command command = null;
				mouseX = event.getX();
				mouseY = event.getY();
				String curAction;
				
				/* get current action */
				curAction = preferences.get("action").getStringPreference();
				artBoard.save();
				switch(curAction) {
				case "draw":
					switch(preferences.get("tool").getStringPreference()) 
					{
						case "Circle":
						case "Ellipse":
							EllipseShape newEllipse = (EllipseShape) cloneFactory.getClone(ellipsePrototype);
							command = new CreateEllipse(preferences, mouseX, mouseY, newEllipse, artBoard);
							break;
						case "Triangle":
							break;
						case "Rectangle":
						case "Square":
							//command = new CreateShape(preferences, mouseX, mouseY);
							break;
						case "Pencil":
							break;
					}
					command.execute();
					break;
				case "edit":
					//command = new EditShape(preferences, mouseX, mouseY);
					break;
				default:
					//just cleared or not set yet
					return;
				}
				putCmdOnStack(command);
			}
        	
        });
        
        colorPicker.setValue(Color.AZURE);
        
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
	
	private void setPreferences(String newAction) {
		
		preferences.put("action", new Preference(newAction));
		preferences.put("tool", new Preference(cbTools.getValue()));
		preferences.put("paint", new Preference(colorPicker.getValue()));
		preferences.put("size", new Preference(sSize.getValue()));
		preferences.put("radius", new Preference(sRadius.getValue()));
		preferences.put("width", new Preference(sWidth.getValue()));
		preferences.put("length", new Preference(sLength.getValue()));
		
		
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
	
	@FXML public void doDraw(ActionEvent event){
		String action = "draw";
		System.out.println("Action: " + action);
		setPreferences(action);
	}
	
	@FXML public void doEdit(ActionEvent event){
		String action = "edit";
		System.out.println("Action: " + action);
		setPreferences(action);
	}
	
	@FXML public void doClear(ActionEvent event){
		String action = "clear";
		System.out.println("Action: " + action);
		setPreferences(action);
		Command command = new ClearAll();
		command.execute();
		putCmdOnStack(command);
	}
	
	@FXML public void doGroup(ActionEvent event){
		String action = "group";
		System.out.println("Action: " + action);
		setPreferences(action);
		Command command = new GroupShapes();
		command.execute();
		putCmdOnStack(command);
	}
	
	@FXML public void doUndo(ActionEvent event){
		Command undoCmd;
		
		if(!undo.isEmpty()) {
			System.out.println("Action: undo");
			undoCmd = undo.pop();
			undoCmd.undo();
			redo.push(undoCmd);
		}
		else
			System.out.println("Action: nothing to undo");
	}
	
	@FXML public void doRedo(ActionEvent event){
		Command redoCmd;
		if(!redo.isEmpty()) {
			System.out.println("Action: redo");
			redoCmd = redo.pop();
			redoCmd.execute();
			undo.push(redoCmd);
		}
		else
			System.out.println("Action: nothing to redo");
	}
	
	private void putCmdOnStack(Command command) {
		undo.push(command);
		if(!redo.isEmpty())
			redo.clear();
	}
}
