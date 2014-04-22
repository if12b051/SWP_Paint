package shapes;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

public class TriangleShape extends ShapeComponent {
	private Polygon triangle = new Polygon();
	
	public TriangleShape() {
		System.out.println("Triangle created.");
	}
	
	public void moveComponent(double mouseX, double mouseY) {
//		triangle.set
//		rectangle.setX(mouseX);
//		rectangle.setY(mouseY);
	}
	
	public void resizeComponent(double resizeValue) {
//		double width = rectangle.getWidth();
//		double height = rectangle.getHeight();
//		rectangle.setWidth(width*resizeValue);
//		rectangle.setHeight(height*resizeValue);
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

	public Polygon getTriangle() {
		return triangle;
	}

	public void setTriangle(Polygon triangle) {
		this.triangle = triangle;
	}
	
	public boolean isGroup() {
		return false;
	}

}
