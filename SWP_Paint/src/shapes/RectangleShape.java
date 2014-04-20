package shapes;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleShape implements Shape{
	private Rectangle rectangle = new Rectangle();
	
	public RectangleShape() {
		System.out.println("Rectangle created.");
	}
	
	@Override
	public Shape makeCopy() {
		
		Shape newRectangle = null;
		
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
}
