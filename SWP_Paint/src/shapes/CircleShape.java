package shapes;

import javafx.scene.paint.Paint;

public class CircleShape implements Shape {

	double centerX, centerY, radius;
	Paint fill;
	
	public CircleShape() {
		System.out.println("Circle created.");
	}
	
	@Override
	public Shape makeCopy() {
		
		Shape newCircle = null;
		
		try {
			newCircle = (CircleShape) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return newCircle;
	}

}
