package shapes;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleShape extends ShapeComponent{
	private Rectangle rectangle = new Rectangle();
	private ShapeComponent parent;
	
	public RectangleShape() {
		System.out.println("Rectangle created.");
	}
	
	public void moveComponent(double mouseX, double mouseY) {
		rectangle.setX(mouseX);
		rectangle.setY(mouseY);
	}
	
	public void resizeComponent(double resizeValue) {
		double width = rectangle.getWidth();
		double height = rectangle.getHeight();
		rectangle.setWidth(width*resizeValue);
		rectangle.setHeight(height*resizeValue);
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
