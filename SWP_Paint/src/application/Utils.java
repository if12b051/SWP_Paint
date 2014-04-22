package application;

import shapes.ShapeComponent;
import gui.MainController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

public class Utils {
	
//	 // records relative x and y co-ordinates.
//	 static class Delta { double x, y; }
//	
//	// make a node movable by dragging it around with the mouse.
//	  public static void enableEllipseDrag(ShapeComponent shape) {
//	    final Delta dragDelta = new Delta();
//	    shape.getNode().setOnMousePressed(new EventHandler<MouseEvent>() {
//	      @Override public void handle(MouseEvent mouseEvent) {
//	        // record a delta distance for the drag and drop operation.
//	    	  String curAction = MainController.preferences.get("action").getStringPreference();
//	    	  if(curAction.equals("edit")) {
//		    	  dragDelta.x = ellipse.getCenterX() - mouseEvent.getX();
//		    	  dragDelta.y = ellipse.getCenterY() - mouseEvent.getY();
//		    	  ellipse.getScene().setCursor(Cursor.MOVE);
//	    	  }
//	      }
//	    });
//	    ellipse.setOnMouseReleased(new EventHandler<MouseEvent>() {
//	      @Override public void handle(MouseEvent mouseEvent) {
//	    	  String curAction = MainController.preferences.get("action").getStringPreference();
//	    	  if(curAction.equals("edit")) {
//	    		  ellipse.getScene().setCursor(Cursor.HAND);
//	    	  }
//	      }
//	    });
//	    ellipse.setOnMouseDragged(new EventHandler<MouseEvent>() {
//	      @Override public void handle(MouseEvent mouseEvent) {
//	    	  String curAction = MainController.preferences.get("action").getStringPreference();
//	    	  if(curAction.equals("edit")) {
//	    		  ellipse.setCenterX(mouseEvent.getX() + dragDelta.x);
//	    		  ellipse.setCenterY(mouseEvent.getY() + dragDelta.y);
//	    	  }
//	      }
//	    });
//	    ellipse.setOnMouseEntered(new EventHandler<MouseEvent>() {
//	      @Override public void handle(MouseEvent mouseEvent) {
//	        if (!mouseEvent.isPrimaryButtonDown()) {
//	        	String curAction = MainController.preferences.get("action").getStringPreference();
//		    	if(curAction.equals("edit")) {
//		    		ellipse.getScene().setCursor(Cursor.HAND);
//		    	}
//	        }
//	      }
//	    });
//	    ellipse.setOnMouseExited(new EventHandler<MouseEvent>() {
//	      @Override public void handle(MouseEvent mouseEvent) {
//	        if (!mouseEvent.isPrimaryButtonDown()) {
//	        	String curAction = MainController.preferences.get("action").getStringPreference();
//		    	if(curAction.equals("edit")) {
//		    		ellipse.getScene().setCursor(Cursor.DEFAULT);
//		    	}
//	        }
//	      }
//	    });
//	  }
}
