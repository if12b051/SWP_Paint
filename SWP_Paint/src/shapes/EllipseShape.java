package shapes;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class EllipseShape implements Shape {

	private Ellipse ellipse;
	
	public EllipseShape() {
		System.out.println("Ellipse created.");
		ellipse = new Ellipse();
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

	public Ellipse getEllipse() {
		return ellipse;
	}

	public void setEllipse(Ellipse ellipse) {
		this.ellipse = ellipse;
	}

}
