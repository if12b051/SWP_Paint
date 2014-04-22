package shapes;

public class CloneFactory {
	/**
	 * Receives any shape or shape-subclass and makes a copy,
	 * which is stored in its own location of memory
	 * @param shape		the shape to be copied
	 * @return			the copy of the shape
	 */
	public ShapeComponent getClone(ShapeComponent shape) {
		return shape.makeCopy();
	}
}
