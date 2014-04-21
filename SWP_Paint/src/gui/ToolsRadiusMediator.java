package gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ToolsRadiusMediator implements Mediator {
	private StringProperty curTool = new SimpleStringProperty();
	
	private ChangeListener<String> invalidateSlider = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			disableSlider.invalidate();
		}
	};
	
	private BooleanBinding disableSlider = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			if((curTool.get().equals("Ellipse") || curTool.get().equals("Circle")) && MainController.preferences.get("action").getStringPreference().equals("draw"))
				return false;
			else
				return true;
		}
	};
	
	public ToolsRadiusMediator() {
		curTool.addListener(invalidateSlider);
	}

	public StringProperty getCurTool() {
		return curTool;
	}

	public void setCurTool(StringProperty curTool) {
		this.curTool = curTool;
	}
	
	@Override
	public BooleanBinding getDisable() {
		return disableSlider;
	}
	
}
