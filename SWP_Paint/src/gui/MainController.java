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
import commands.CreateRectangle;
import commands.CreateTriangle;
import commands.EditShape;
import commands.GroupShapes;
import shapes.CloneFactory;
import shapes.EllipseShape;
import shapes.RectangleShape;
import shapes.Shape;
import shapes.TriangleShape;
import application.Preference;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class MainController implements Initializable{
	
	private final String[] TOOL_TYPES = {"Circle", "Ellipse", "Triangle", "Square", "Rectangle", "Pencil"};
	public final static int CANVAS_WIDTH=474, CANVAS_HEIGHT=482;
	
	/* GUI Objects */
	@FXML private ComboBox<String> cbTools;
	@FXML private Slider sSize, sWidth, sHeight, sRadius;
	@FXML private Label lblRadius, lblSize, lblWidth, lblHeight;
	@FXML private Pane pCanvas, pColorPicker;
	@FXML private Button bClear, bGroup, bDraw, bEdit, bUndo, bRedo, bHelp;
	private final ColorPicker colorPicker = new ColorPicker();
	private MainControllerModel model;
	
	/* important general objects */
	private Stack<Command> undo;
    private Stack<Command> redo;
    public static Map<String, Preference> preferences; //"action", "tool", "paint", "size", "radius", "width", "height"
    private CloneFactory cloneFactory;
    private ArrayList<Shape> drawnGeometry;
    
    /* geometry prototypes */
	private EllipseShape ellipsePrototype;
	private RectangleShape rectanglePrototype;
	private TriangleShape trianglePrototype;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* initialize bindings and model */
		model = new MainControllerModel();
		drawnGeometry = new ArrayList<Shape>();
		
		/* Initialize GUI, set values, create listeners */
		cbTools.getItems().addAll(TOOL_TYPES);
		resetValues();
		createListeners();
        
        /* initalize general objects */
        undo = new Stack<Command>();
        redo = new Stack<Command>();
        preferences = new HashMap<String, Preference>();
        cloneFactory = new CloneFactory();
        
        /* initialize prototypes */
        ellipsePrototype = new EllipseShape();
        rectanglePrototype = new RectangleShape();
        
        /* initialize colorpicker and canvas */
        colorPicker.setValue(Color.BEIGE);
        pColorPicker.getChildren().addAll(colorPicker);
        
        /* add eventListeners to canvas for: CLICKED and DRAGGED*/
        pCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double mouseX, mouseY;
				Command command = null;
				mouseX = event.getX();
				mouseY = event.getY();
				String curAction;
				
				/* get current action */
				curAction = preferences.get("action").getStringPreference();
				switch(curAction) {
				case "draw":
					switch(preferences.get("tool").getStringPreference()) 
					{
						case "Circle":
						case "Ellipse":
							EllipseShape newEllipse = new EllipseShape();
							drawnGeometry.add(newEllipse);
							command = new CreateEllipse(preferences, mouseX, mouseY, newEllipse, pCanvas);
							break;
						case "Triangle":
							TriangleShape newTriangle = new TriangleShape();
							drawnGeometry.add(newTriangle);
							command = new CreateTriangle(preferences, mouseX, mouseY, newTriangle, pCanvas);
							break;
						case "Rectangle":
						case "Square":
							RectangleShape newRectangle = new RectangleShape();
							drawnGeometry.add(newRectangle);
							command = new CreateRectangle(preferences, mouseX, mouseY, newRectangle, pCanvas);
//							RectangleShape newRectangle = (RectangleShape) cloneFactory.getClone(rectanglePrototype);
							//command = new CreateRectangle(preferences, mouseX, mouseY, newRectangle, artBoard);
							break;
						case "Pencil":
							break;
					}
					command.execute();
					
					break;
				case "edit":
					//command = new EditShape(preferences, mouseX, mouseY);
					break;
				case "group":
					/*for(int i=0; i<drawnGeometry.size(); i++){
						System.out.println(drawnGeometry.get(i).getClass());
						String classString = drawnGeometry.get(i).getClass().toString();
						String[] splitArray;
						splitArray = classString.split(".");
						if(splitArray[1] == "EllipseShape"){
							drawnGeometry.get(i);
						}	
					}*/
					
					break;
				default:
					//just cleared or not set yet
					return;
				}
				putCmdOnStack(command);
			}
        });
        setPreferences("draw");
        
        Circle circle = new Circle(500, 200, 100);
        
//        circle.setOnMouseMoved(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent event) {
//                circle.setCenterX(event.getX());
//                ellipse.setCenterY(event.getY());
//            }
//        });
        
        pCanvas.getChildren().add(circle);
	}
	
	private void setPreferences(String newAction) {
		preferences.put("action", new Preference(newAction));
		bDraw.getStyleClass().remove("btn_selected");
		bEdit.getStyleClass().remove("btn_selected");
		bGroup.getStyleClass().remove("btn_selected");
		bClear.getStyleClass().remove("btn_selected");
		switch(newAction)
		{
		case "draw":
			bDraw.getStyleClass().add("btn_selected");
			break;
		case "edit":
			bEdit.getStyleClass().add("btn_selected");
			break;
		case "group":
			bGroup.getStyleClass().add("btn_selected");
			break;
		case "clear":
			bClear.getStyleClass().add("btn_selected");
			break;
		}
		preferences.put("tool", new Preference(cbTools.getValue()));
		preferences.put("paint", new Preference(colorPicker.getValue()));
		preferences.put("size", new Preference(sSize.getValue()));
		preferences.put("radius", new Preference(sRadius.getValue()));
		preferences.put("width", new Preference(sWidth.getValue()));
		preferences.put("height", new Preference(sHeight.getValue()));
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
		Command command = new ClearAll(pCanvas);
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
	
	private void createListeners() {
		StringConverter<? extends Number> converter = new DoubleStringConverter();
		
		Bindings.bindBidirectional(lblRadius.textProperty(), sRadius.valueProperty(), (StringConverter<Number>)converter);
		lblRadius.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblRadius.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("draw");
			}
		});
		
		Bindings.bindBidirectional(lblSize.textProperty(), sSize.valueProperty(), (StringConverter<Number>)converter);
		lblSize.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblSize.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("draw");
			}
		});
		
		Bindings.bindBidirectional(lblWidth.textProperty(), sWidth.valueProperty(), (StringConverter<Number>)converter);
		lblWidth.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblWidth.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("draw");
			}
		});
		
		Bindings.bindBidirectional(lblHeight.textProperty(), sHeight.valueProperty(), (StringConverter<Number>)converter);
		lblHeight.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblHeight.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("draw");
			}
		});
	}
	
	private void resetValues() {
		cbTools.setValue("Circle");
		sSize.setValue(50);
		sWidth.setValue(50);
		sHeight.setValue(50);
		sRadius.setValue(50);
	}
}
