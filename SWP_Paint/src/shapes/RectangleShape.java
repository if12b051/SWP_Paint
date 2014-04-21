package shapes;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleShape implements Shape{
	private Rectangle rectangle;
	
	public RectangleShape() {
		System.out.println("Rectangle created.");
		rectangle = new Rectangle();
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
