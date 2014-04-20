package gui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class MainControllerModel {
	
	private StringProperty radius = new SimpleStringProperty();
	private DoubleProperty length = new SimpleDoubleProperty();
	private DoubleProperty width = new SimpleDoubleProperty();
	private DoubleProperty size = new SimpleDoubleProperty();
	
//	public BooleanBinding disableCbToolsBinding = new BooleanBinding(String action) {
//		
//		@Override
//		protected boolean computeValue() {
//			if(!action.equals("draw")) {
//				return true;
//			}
//			else {
//				return false;
//			}
//		}
//		
//	}
		
	
	public MainControllerModel() {
		radius.addListener(new ChangeListener<String>()  {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				refreshRadius.invalidate();
			}
		});
	}
	
	
	
	private StringBinding refreshRadius = new StringBinding() {
		@Override
		protected String computeValue() {
			double doubleValue;
			int intValue;
			String strValue;
			System.out.println("Value: " + radius.getValue());
			doubleValue = Double.parseDouble(radius.getValue());
			intValue = (int) doubleValue;
			strValue = Integer.toString(intValue);
			
			return strValue;
		}
	};
	
	public StringBinding refreshRadiusBinding() {
		return refreshRadius;
	}

	public StringProperty getRadiusProperty() {
		return radius;
	}

	public void setRadius(StringProperty radius) {
		this.radius = radius;
	}
	
	
}
