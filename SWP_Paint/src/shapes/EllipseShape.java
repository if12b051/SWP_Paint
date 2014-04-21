package shapes;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;

public class EllipseShape implements Shape {

	private Ellipse ellipse;
	public boolean group;
	
	public EllipseShape() {
		System.out.println("Ellipse created.");
		ellipse = new Ellipse();
		group = false;
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
