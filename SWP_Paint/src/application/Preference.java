package application;

import javafx.scene.paint.Paint;

public class Preference {
	
	/* types of preferences */
	private String stringPreference;
	private Paint paintPreference;
	private double doublePreference;
	
	public Preference(String preference) {
		stringPreference = preference;
	}
	
	public Preference(Paint preference) {
		paintPreference = preference;
	}
	
	public Preference(double preference) {
		doublePreference = preference;
	}
	
	public String getStringPreference() {
		return stringPreference;
	}
	
	public void setStringPreference(String stringPreference) {
		this.stringPreference = stringPreference;
	}
	
	public Paint getPaintPreference() {
		return paintPreference;
	}
	
	public void setPaintPreference(Paint paintPreference) {
		this.paintPreference = paintPreference;
	}
	
	public double getDoublePreference() {
		return doublePreference;
	}
	
	public void setDoublePreference(double doublePreference) {
		this.doublePreference = doublePreference;
	}
	
}
