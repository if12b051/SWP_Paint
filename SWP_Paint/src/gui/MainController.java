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
import shapes.ShapeComponent;
import shapes.ShapeGroup;
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
	
	static public ShapeComponent everyShape = new ShapeGroup();
	static public ShapeComponent shapeBeingGrouped, shapeBeingEdited;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* Constructors */
		drawnGeometry = new ArrayList<ShapeComponent>();
		mediatorList = new ArrayList<Mediator>();
		preferences = new HashMap<String, Preference>();
		undo = new Stack<Command>();
        redo = new Stack<Command>();
        cloneFactory = new CloneFactory();
		
		/* Initialize GUI, set values, create listeners */
		cbTools.getItems().addAll(TOOL_TYPES);
		sResize.setMax(200);
		
		setPreferences("draw");
		
        createListeners();
        resetValues();
        
        
        /* initialize colorpicker and canvas */
        colorPicker.setValue(Color.BEIGE);
        pColorPicker.getChildren().addAll(colorPicker);
        
        /* add eventListeners to canvas for: CLICKED and DRAGGED */
        pArtBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
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
							EllipseShape newEllipse = new EllipseShape(everyShape);
							everyShape.add(newEllipse);
							command = new CreateEllipse(mouseX, mouseY, newEllipse, pArtBoard);
							break;
						case "Triangle":
							TriangleShape newTriangle = new TriangleShape();
							everyShape.add(newTriangle);
							command = new CreateTriangle(preferences, mouseX, mouseY, newTriangle, pArtBoard);
							break;
						case "Rectangle":
						case "Square":
							RectangleShape newRectangle = new RectangleShape();
							everyShape.add(newRectangle);
							command = new CreateRectangle(preferences, mouseX, mouseY, newRectangle, pArtBoard);
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
	}
	
	private void setPreferences(String newAction) {
		
		/* set action if "keep"(keep old value) is not chosen */
		if(!newAction.equals("keep")) {
			preferences.put("action", new Preference(newAction));
		} 
		else {
			newAction = preferences.get("action").getStringPreference();
		}
			
		/* set right button color/style here */
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
		case "keep":
		default:
		}
		
		/* set other preferences */
		preferences.put("tool", new Preference(cbTools.getValue()));
		preferences.put("paint", new Preference(colorPicker.getValue()));
		preferences.put("size", new Preference(sSize.getValue()));
		preferences.put("radius", new Preference(sRadius.getValue()));
		preferences.put("width", new Preference(sWidth.getValue()));
		preferences.put("height", new Preference(sHeight.getValue()));
		preferences.put("resize", new Preference(sResize.getValue()));
		
		/* notify mediators of new preferences, and let them adjust if necessary */
		for(Mediator m : mediatorList) {
			m.getDisable().invalidate();
		}
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
		Command command = new ClearAll(pArtBoard);
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
		
		resetValues();
		
		
		/* initialize mediators */
		ToolsSizeMediator tsMediator = new ToolsSizeMediator();
		tsMediator.getCurTool().bindBidirectional(cbTools.valueProperty());
		sSize.disableProperty().bind(tsMediator.getDisable());
		
		ToolsRadiusMediator trMediator = new ToolsRadiusMediator();
		trMediator.getCurTool().bindBidirectional(cbTools.valueProperty());
		sRadius.disableProperty().bind(trMediator.getDisable());
		
		ToolsWidthMediator twMediator = new ToolsWidthMediator();
		twMediator.getCurTool().bindBidirectional(cbTools.valueProperty());
		sWidth.disableProperty().bind(twMediator.getDisable());
		
		ToolsHeightMediator thMediator = new ToolsHeightMediator();
		thMediator.getCurTool().bindBidirectional(cbTools.valueProperty());
		sHeight.disableProperty().bind(thMediator.getDisable());
		
		ToolsResizeMediator trsMediator = new ToolsResizeMediator();
		trsMediator.getCurTool().bindBidirectional(cbTools.valueProperty());
		sResize.disableProperty().bind(trsMediator.getDisable());
		
		ToolsButtonsMediator tbMediator = new ToolsButtonsMediator();
		cbTools.disableProperty().bind(tbMediator.getDisable());
		
		ToolsButtonsMediator tcMediator = new ToolsButtonsMediator();
		colorPicker.disableProperty().bind(tcMediator.getDisable());
		
		mediatorList.add(tsMediator);
		mediatorList.add(trMediator);
		mediatorList.add(twMediator);
		mediatorList.add(thMediator);
		mediatorList.add(trsMediator);
		mediatorList.add(tbMediator);
		mediatorList.add(tcMediator);
		
		Bindings.bindBidirectional(lblRadius.textProperty(), sRadius.valueProperty(), (StringConverter<Number>)converter);
		lblRadius.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblRadius.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("keep");
			}
		});
		
		Bindings.bindBidirectional(lblSize.textProperty(), sSize.valueProperty(), (StringConverter<Number>)converter);
		lblSize.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblSize.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("keep");
			}
		});
		
		Bindings.bindBidirectional(lblWidth.textProperty(), sWidth.valueProperty(), (StringConverter<Number>)converter);
		lblWidth.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblWidth.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("keep");
			}
		});
		
		Bindings.bindBidirectional(lblHeight.textProperty(), sHeight.valueProperty(), (StringConverter<Number>)converter);
		lblHeight.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblHeight.setText(Integer.toString((int) Double.parseDouble(newValue)));
				setPreferences("keep");
			}
		});
		
		Bindings.bindBidirectional(lblResize.textProperty(), sResize.valueProperty(), (StringConverter<Number>)converter);
		sResize.valueProperty().bindBidirectional(trsMediator.sliderValue);
		lblResize.textProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblResize.setText(Integer.toString((int) Double.parseDouble(newValue)));
				if(shapeBeingEdited != null) {
					shapeBeingEdited.setResizeValue(sResize.getValue());
					
				}
				setPreferences("keep");
			}
		});
		
		cbTools.valueProperty().addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				setPreferences("keep");
			}
		});
		
		colorPicker.valueProperty().addListener(new ChangeListener<Paint>()  {
			@Override
			public void changed(ObservableValue<? extends Paint> observable,
					Paint oldValue, Paint newValue) {
				setPreferences("keep");
			}
		});
		/* here so double values are not shown */
		resetLabels();
	}
	
	private void resetValues() {
		cbTools.setValue("Circle");
		sSize.setValue(50);
		sWidth.setValue(50);
		sHeight.setValue(50);
		sRadius.setValue(50);
		sResize.setValue(100);
	}
	
	private void resetLabels() {
		lblRadius.setText("50");
		lblHeight.setText("50");
		lblWidth.setText("50");
		lblSize.setText("50");
		lblResize.setText("100");
	}
	
	private final String[] TOOL_TYPES = {"Circle", "Ellipse", "Triangle", "Square", "Rectangle", "Pencil"};
	public final static int CANVAS_WIDTH=474, CANVAS_HEIGHT=482;
	
	/* GUI Objects */
	@FXML private ComboBox<String> cbTools;
	@FXML private Slider sSize, sWidth, sHeight, sRadius, sResize;
	@FXML private Label lblRadius, lblSize, lblWidth, lblHeight, lblResize;
	@FXML private Pane pArtBoard, pColorPicker, pControl;
	@FXML private Button bClear, bGroup, bDraw, bEdit, bUndo, bRedo, bHelp;
	private final ColorPicker colorPicker = new ColorPicker();
	private MainControllerModel model;
	
	/* important general objects */
	private Stack<Command> undo;
    private Stack<Command> redo;
    public static Map<String, Preference> preferences; //"action", "tool", "paint", "size", "radius", "width", "height"
    private CloneFactory cloneFactory;
    private ArrayList<ShapeComponent> drawnGeometry;
    private ArrayList<Mediator> mediatorList;
}
