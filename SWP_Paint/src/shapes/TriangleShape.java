package shapes;

import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

public class TriangleShape implements Shape {
	private Polygon triangle;
	
	public TriangleShape() {
		System.out.println("Triangle created.");
		setTriangle(new Polygon());
	}
	
	@Override
	public Shape makeCopy() {
		
		Shape newEllipse = null;
		
		try {
			newEllipse = (EllipseShape) super.clone();
			System.out.println("CURRENTLY CLONING");
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		System.out.println("CLONING ELLIPSE");
		return newEllipse;
	}

	public Polygon getTriangle() {
		return triangle;
	}

	public void setTriangle(Polygon triangle) {
		this.triangle = triangle;
	}
}
