package gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ToolsResizeMediator implements Mediator {
	private StringProperty curTool = new SimpleStringProperty();
	public DoubleProperty sliderValue = new SimpleDoubleProperty();
	
	ChangeListener<String> invalidateSlider = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			disableSlider.invalidate();
		}
	};
	
	public BooleanBinding disableSlider = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			if(MainController.preferences.get("action").getStringPreference().equals("edit")) {
				return false;
			}
			else
				return true;
		}
	};
	
	public ToolsResizeMediator() {
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
