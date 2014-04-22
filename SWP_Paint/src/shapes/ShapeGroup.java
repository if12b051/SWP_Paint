package shapes;

import java.util.ArrayList;

public class ShapeGroup extends ShapeComponent {
	private ArrayList<ShapeComponent> shapeComponents = new ArrayList<ShapeComponent>();
	private ShapeComponent parent;
	
	public void add(ShapeComponent newShapeComponent) {
		shapeComponents.add(newShapeComponent);
	}
	
	public void remove(ShapeComponent oldShapeComponent) {
		shapeComponents.remove(oldShapeComponent);
	}
	
	public ArrayList<ShapeComponent> getChildren() {
		return shapeComponents;
	}
	
	public boolean isGroup() {
		return true;
	}

	public ShapeComponent getParent() {
		return parent;
	}

	public void setParent(ShapeComponent parent) {
		this.parent = parent;
	}
	
}
