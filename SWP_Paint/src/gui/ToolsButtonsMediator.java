package gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ToolsButtonsMediator implements Mediator {
	
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
			if(MainController.preferences.get("action").getStringPreference().equals("draw"))
				return false;
			else
				return true;
		}
	};
	
	@Override
	public BooleanBinding getDisable() {
		return disableSlider;
	}
	
}
