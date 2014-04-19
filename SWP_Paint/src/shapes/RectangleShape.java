package shapes;

import javafx.scene.paint.Paint;

public class RectangleShape implements Shape{
	private double centerX, centerY, height, width;
	private Paint paint;
	
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

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
}
