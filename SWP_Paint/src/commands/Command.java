package commands;

import javafx.scene.canvas.GraphicsContext;

public interface Command {
	
	public void execute();
	
	public void undo();
	
}
