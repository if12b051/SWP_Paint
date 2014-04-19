package shapes;

import javafx.scene.paint.Paint;

public class EllipseShape implements Shape {

	private double centerX, centerY, width, radius;
	private Paint paint;
	
	public EllipseShape() {
		System.out.println("Ellipse created.");
	}
	
	@Override
	public Shape makeCopy() {
		
		Shape newEllipse = null;
		
		try {
			newEllipse = (EllipseShape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return newEllipse;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

}
