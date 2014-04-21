package gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ToolsWidthMediator {
	private StringProperty curTool = new SimpleStringProperty();
	
	ChangeListener<String> invalidateSlider = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {
			disableSlider.invalidate();
		}
	};
	
	private BooleanBinding disableSlider = new BooleanBinding() {
		@Override
		protected boolean computeValue() {
			if(curTool.get().equals("Ellipse") || curTool.get().equals("Circle") || curTool.get().equals("Square") || curTool.get().equals("Rectangle") || curTool.get().equals("Triangle"))
				return false;
			else
				return true;
		}
	};
	
	public ToolsWidthMediator() {
		curTool.addListener(invalidateSlider);
	}

	public StringProperty getCurTool() {
		return curTool;
	}

	public void setCurTool(StringProperty curTool) {
		this.curTool = curTool;
	}

	public BooleanBinding getDisableSlider() {
		return disableSlider;
	}
	
}
