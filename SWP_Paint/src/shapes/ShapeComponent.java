package shapes;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public abstract class ShapeComponent implements Cloneable {
	
	public void add(ShapeComponent newShapeComponent) {
		throw new UnsupportedOperationException();
	}
	
	public void remove(ShapeComponent newShapeComponent) {
		throw new UnsupportedOperationException();
	}
	
	public ShapeComponent getComponent(ShapeComponent findShapeComponent) {
		throw new UnsupportedOperationException();
	}
	
	public ShapeComponent getComponentList(ShapeComponent findShapeComponent) {
		throw new UnsupportedOperationException();
	}
	
	public void moveComponent(ShapeComponent shape, double mouseX, double mouseY) {
		throw new UnsupportedOperationException();
	}
	
	public void resizeComponent(double resizeValue) {
		throw new UnsupportedOperationException();
	}
	
	public ShapeComponent makeCopy() {
		throw new UnsupportedOperationException();
	}
	
	public boolean isGroup() {
		throw new UnsupportedOperationException();
	}
	
	public ShapeComponent getParent() {
		throw new UnsupportedOperationException();
	}

	public ArrayList<ShapeComponent> getChildren() {
		throw new UnsupportedOperationException();
	}
	
	public void setParent(ShapeComponent parent) {
		throw new UnsupportedOperationException();
	}
	
	public Node getNode() {
		throw new UnsupportedOperationException();
	}
	
	public Ellipse getEllipse() {
		throw new UnsupportedOperationException();
	}
	
	public Rectangle getRectangle() {
		throw new UnsupportedOperationException();
	}
	
	public Polygon getTriangle() {
		throw new UnsupportedOperationException();
	}
}
