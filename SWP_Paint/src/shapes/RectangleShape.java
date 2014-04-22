package shapes;

import gui.MainController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleShape extends ShapeComponent{
	private Rectangle rectangle = new Rectangle();
	private ShapeComponent parent, thisShape;
	
	public RectangleShape(ShapeComponent parentMain) {
		System.out.println("Rectangle created.");
		this.parent = parentMain;
		thisShape = this;
		rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String curAction = MainController.preferences.get("action").getStringPreference();
				System.out.println("Tool: " + curAction);
				
				switch(curAction){
				case "group":
					if(MainController.shapeBeingGrouped == null) {
						MainController.shapeBeingGrouped = thisShape;
						System.out.println("First shape chosen.");
					} else {
						if(thisShape != MainController.shapeBeingGrouped) { 
							if(parent == MainController.shapeBeingGrouped.getParent()) { //both are first child nodes
								/* create new group and add set it */
								ShapeComponent newGroup = new ShapeGroup();
								newGroup.add(MainController.shapeBeingGrouped);
								newGroup.add(thisShape);
								newGroup.setParent(parent);
								newGroup.getParent().add(newGroup);
								
								/* set the shapes parents accordingly and remove from old one */
								MainController.shapeBeingGrouped.setParent(newGroup);
								thisShape.setParent(newGroup);
								newGroup.getParent().remove(thisShape);
								newGroup.getParent().remove(MainController.shapeBeingGrouped);
								
								System.out.println("Created new group.");
							} else if(parent.getParent() == null && MainController.shapeBeingGrouped.getParent().getParent() != null) {
								MainController.shapeBeingGrouped.getParent().add(parent);
								parent.setParent(MainController.shapeBeingGrouped.getParent());
								System.out.println("Blabla 1");
							} else if(parent.getParent() != null && MainController.shapeBeingGrouped.getParent().getParent() == null) {
								parent.getParent().add(MainController.shapeBeingGrouped.getParent());
								MainController.shapeBeingGrouped.setParent(parent);
								System.out.println("Blabla 2");
							} else {
								parent.getParent().add(MainController.shapeBeingGrouped.getParent());
								MainController.shapeBeingGrouped.setParent(parent);
								System.out.println("Blabla 3");
							}
							MainController.shapeBeingGrouped = null;
						} else { //clicked twice on the same object
							System.out.println("Clicked twice on same object.");
						}
						
					}
					break;
				case "edit":
					double differenceX = 0;
					double differenceY = 0;
					double factor = 0;
					
					double curResizeSlider = MainController.preferences.get("resize").getDoublePreference();
					factor = curResizeSlider/100;
					
					if(curResizeSlider != 100){
						if(thisShape.getParent().getParent() != null) {
							resizeComponent(thisShape.getParent(), factor);
						}
						else {					
							thisShape.getNode().setScaleX(factor);
							thisShape.getNode().setScaleY(factor);
						}
						MainController.resetResizeSlider();
					}
					else {
						differenceX = (event.getSceneX() - rectangle.getX());
						differenceY = (event.getSceneY() - rectangle.getY());
						if(thisShape.getParent().getParent() != null) {
							System.out.println("Children second or higher generation..");
							moveComponent(thisShape.getParent(), differenceX, differenceY);
						}
						else {
							System.out.println("Children first generation..");
							thisShape.getNode().setLayoutX(differenceX);
							thisShape.getNode().setLayoutY(differenceY);
						}
					}	
					break;
				}
			}
			 
		 });
	}
	
	public void moveComponent(ShapeComponent theShape, double differenceX, double differenceY) {
		if(theShape.isGroup()) {
			for(ShapeComponent s : theShape.getChildren()) {
				moveComponent(s, differenceX, differenceY);
			}
		} else {
			theShape.getNode().setLayoutX(differenceX);
			theShape.getNode().setLayoutY(differenceY);
		}
	}
	
	public void resizeComponent(ShapeComponent theShape, double factor) {
		if(theShape.isGroup()) {
			for(ShapeComponent s : theShape.getChildren()) {
				resizeComponent(s, factor);
			}
		} else {
			theShape.getNode().setScaleX(factor);
			theShape.getNode().setScaleY(factor);
		}
	}
	
	@Override
	public ShapeComponent makeCopy() {
		
		ShapeComponent newRectangle = null;
		
		try {
			newRectangle = (RectangleShape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newRectangle;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public boolean isGroup() {
		return false;
	}
}
