package shapes;

import gui.MainController;

import java.util.ArrayList;

import commands.GroupShapes;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class EllipseShape extends ShapeComponent {

	private Ellipse ellipse = new Ellipse();
	private ShapeComponent parent, thisShape;
	
	public EllipseShape(ShapeComponent parentMain) {
		System.out.println("Ellipse created.");
		this.parent = parentMain;
		thisShape = this;
		this.enableThisDrag();
		ellipse.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

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
								MainController.shapeBeingGrouped.getParent().add(thisShape);
								thisShape.setParent(MainController.shapeBeingGrouped.getParent());
								System.out.println("Blabla 1");
							} else if(parent.getParent() != null && MainController.shapeBeingGrouped.getParent().getParent() == null) {
								parent.add(MainController.shapeBeingGrouped);
								MainController.shapeBeingGrouped.setParent(parent);
								System.out.println("Blabla 2");
							} else {
								parent.add(MainController.shapeBeingGrouped);
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
					double factor = 0;
					
					double curResizeSlider = MainController.preferences.get("resize").getDoublePreference();
					factor = curResizeSlider/100;
					
					if(curResizeSlider != 100){
						if(thisShape.getParent().getParent() != null) {
							resizeComponent(thisShape.getParent(), factor);
						}
						else {					
							thisShape.getEllipse().setScaleX(factor);
							thisShape.getEllipse().setScaleY(factor);
						}
						MainController.resetResizeSlider();
					}
					break;
				}
				
			}
			 
		 });
	}
	
	/**
	 * Is called to move components using the compository pattern
	 * gets called recursively every time function encounters group
	 * -> otherwise it moves the node (leaf)
	 * @param theShape		the shape from which is searched recursively
	 * @param differenceX	X-Value node needs to move in x-direction
	 * @param differenceY	Y-Value node needs to move in y-direction	
	 */
	public void moveComponent(ShapeComponent theShape, double differenceX, double differenceY) {
		if(theShape.isGroup()) {
			for(ShapeComponent s : theShape.getChildren()) {
				moveComponent(s, differenceX, differenceY);
			}
		} else {
			theShape.getEllipse().setCenterX(differenceX);
			theShape.getEllipse().setCenterY(differenceY);
		}
	}
	
	/**
	 * Same as moveComponent, just for resize
	 * @param theShape		the shape from which is searched recursively
	 * @param factor		the factor with which the shapes are resized
	 */
	public void resizeComponent(ShapeComponent theShape, double factor) {
		if(theShape.isGroup()) {
			for(ShapeComponent s : theShape.getChildren()) {
				resizeComponent(s, factor);
			}
		} else {
			theShape.getEllipse().setScaleX(factor);
			theShape.getEllipse().setScaleY(factor);
		}
	}
	
	/**
	 * creates a clone of this class
	 * is used for the Prototype-Pattern
	 * @return		the copy which has been made
	 */
	@Override
	public ShapeComponent makeCopy() {
		
		ShapeComponent newEllipse = null;
		
		try {
			newEllipse = (EllipseShape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newEllipse;
	}

	public Ellipse getEllipse() {
		return ellipse;
	}

	public void setEllipse(Ellipse ellipse) {
		this.ellipse = ellipse;
	}
	
	public boolean isGroup() {
		return false;
	}

	public ShapeComponent getParent() {
		return parent;
	}

	public void setParent(ShapeComponent parent) {
		this.parent = parent;
	}
	
	// records relative x and y co-ordinates.
	static class Delta { double x, y; }
		
	/**
	 * enable drag on all newly created objects, whenever mode "edit" is on
	 */
	private void enableThisDrag() {
	    final Delta dragDelta = new Delta();
	    thisShape.getEllipse().setOnMousePressed(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        // record a delta distance for the drag and drop operation.
	    	  String curAction = MainController.preferences.get("action").getStringPreference();
	    	  if(curAction.equals("edit")) {
		    	  dragDelta.x = thisShape.getEllipse().getCenterX() - mouseEvent.getX();
		    	  dragDelta.y = thisShape.getEllipse().getCenterY() - mouseEvent.getY();
		    	  ellipse.getScene().setCursor(Cursor.MOVE);
	    	  }
	      }
	    });
	    thisShape.getEllipse().setOnMouseReleased(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	    	  String curAction = MainController.preferences.get("action").getStringPreference();
	    	  if(curAction.equals("edit")) {
	    		  ellipse.getScene().setCursor(Cursor.HAND);
	    	  }
	      }
	    });
	    thisShape.getEllipse().setOnMouseDragged(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	    	  String curAction = MainController.preferences.get("action").getStringPreference();
	    	  double finalX, finalY;
	    	  if(curAction.equals("edit")) {
	    		  finalX = ((mouseEvent.getX()+dragDelta.x) );
    			  finalY = ((mouseEvent.getY()+dragDelta.y) );
	    		  if(thisShape.getParent().getParent() != null)
	    		  {
	    			  moveComponent(thisShape.getParent(), finalX, finalY);
	    		  }
	    		  else {
	    			 
	    			  moveComponent(thisShape, finalX, finalY);
	    		  }
	    		  //thisShape.getEllipse().setTranslateX(30);
	    	  }
	      }
	    });
	    thisShape.getEllipse().setOnMouseEntered(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        if (!mouseEvent.isPrimaryButtonDown()) {
	        	String curAction = MainController.preferences.get("action").getStringPreference();
		    	if(curAction.equals("edit")) {
		    		thisShape.getEllipse().getScene().setCursor(Cursor.HAND);
		    	}
	        }
	      }
	    });
	    thisShape.getEllipse().setOnMouseExited(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        if (!mouseEvent.isPrimaryButtonDown()) {
	        	String curAction = MainController.preferences.get("action").getStringPreference();
		    	if(curAction.equals("edit")) {
		    		thisShape.getEllipse().getScene().setCursor(Cursor.DEFAULT);
		    	}
	        }
	      }
	    });
	  }
}
