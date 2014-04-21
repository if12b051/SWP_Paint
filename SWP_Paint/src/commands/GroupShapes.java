package commands;

import javafx.scene.Group;
import javafx.scene.shape.Ellipse;

public class GroupShapes implements Command{
	private Group group = new Group();
	
	public GroupShapes() {
		
	}

	@Override
	public void execute() {
	}
	
	public void execute(Ellipse e) {
		group.getChildren().add(e);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}
	
	public Group getGroup() {
		return group;
	}
	
}
