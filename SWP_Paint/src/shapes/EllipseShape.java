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
	private double firstRadius, firstWidth, resizeValue;
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
					MainController.shapeBeingEdited = thisShape;
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
	
	public void resizeComponent(double resizeValue) {
		double radiusX = ellipse.radiusXProperty().getValue();
		double radiusY = ellipse.radiusYProperty().getValue();
		ellipse.radiusXProperty().setValue(radiusX*resizeValue);
		ellipse.radiusYProperty().setValue(radiusY*resizeValue);
	}
	
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
	
	public Node getNode() {
		return ellipse;
	}
	
	// records relative x and y co-ordinates.
	static class Delta { double x, y; }
		
		// make a node movable by dragging it around with the mouse.
	private void enableThisDrag() {
	    final Delta dragDelta = new Delta();
	    thisShape.getNode().setOnMousePressed(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        // record a delta distance for the drag and drop operation.
	    	  String curAction = MainController.preferences.get("action").getStringPreference();
	    	  if(curAction.equals("edit")) {
		    	  dragDelta.x = thisShape.getNode().getLayoutX() - mouseEvent.getX();
		    	  dragDelta.y = thisShape.getNode().getLayoutY() - mouseEvent.getY();
		    	  ellipse.getScene().setCursor(Cursor.MOVE);
	    	  }
	      }
	    });
	    thisShape.getNode().setOnMouseReleased(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	    	  String curAction = MainController.preferences.get("action").getStringPreference();
	    	  if(curAction.equals("edit")) {
	    		  ellipse.getScene().setCursor(Cursor.HAND);
	    	  }
	      }
	    });
	    thisShape.getNode().setOnMouseDragged(new EventHandler<MouseEvent>() {
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
	    		  //thisShape.getNode().setTranslateX(30);
	    	  }
	      }
	    });
	    thisShape.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        if (!mouseEvent.isPrimaryButtonDown()) {
	        	String curAction = MainController.preferences.get("action").getStringPreference();
		    	if(curAction.equals("edit")) {
		    		thisShape.getNode().getScene().setCursor(Cursor.HAND);
		    	}
	        }
	      }
	    });
	    thisShape.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
	      @Override public void handle(MouseEvent mouseEvent) {
	        if (!mouseEvent.isPrimaryButtonDown()) {
	        	String curAction = MainController.preferences.get("action").getStringPreference();
		    	if(curAction.equals("edit")) {
		    		thisShape.getNode().getScene().setCursor(Cursor.DEFAULT);
		    	}
	        }
	      }
	    });
	  }

	public double getResizeValue() {
		return resizeValue;
	}

	public void setResizeValue(double resizeValue) {
		this.resizeValue = resizeValue;
	}

	public double getFirstRadius() {
		return firstRadius;
	}

	public void setFirstRadius(double firstRadius) {
		this.firstRadius = firstRadius;
	}

	public double getFirstWidth() {
		return firstWidth;
	}

	public void setFirstWidth(double firstWidth) {
		this.firstWidth = firstWidth;
	}
}
