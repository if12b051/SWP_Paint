package commands;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Ellipse;

public class GroupShapes implements Command{
	//private static Group group = new Group();
	//private static ObservableList<Node> shapeGroups;
	private static ArrayList<Node> group = new ArrayList<Node>();
	
	public GroupShapes() {	
	}

	@Override
	public void execute() {
	}
	
	public void execute(Ellipse e) {
		group.add(e);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}
	
	public static ArrayList getGroup() {
		return group;
	}
	
}
