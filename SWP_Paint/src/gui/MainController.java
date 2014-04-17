package gui;

import java.net.URL;
import java.util.ResourceBundle;
import businesslayer.Businesslayer;
import bussinessobjects.GuiData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MainController implements Initializable{
	/*Comboboxen*/
	@FXML private ComboBox<String> cbColor;
	@FXML private ComboBox<String> cbShape;
	@FXML private ComboBox<String> cbPen;
	/*Comboboxeninhalte*/
	private ObservableList<String> cbColorData = FXCollections.observableArrayList();
	private ObservableList<String> cbShapeData = FXCollections.observableArrayList();
	private ObservableList<String> cbPenData = FXCollections.observableArrayList();
	/*Textfelder*/
	@FXML TextField tfWidth;
	@FXML TextField tfLength;
	/*Buttons*/
	@FXML private Button bClear;
	@FXML private Button bGroup;
	@FXML private Button bStart;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initComboboxColor();
		initComboboxShape();
		initComboboxPen();
	}
	
	public void initComboboxColor(){
		cbColorData.add("Rot");
		cbColorData.add("Grün");
		cbColorData.add("Gelb");
		cbColorData.add("Schwarz");
		cbColorData.add("Blau");
		cbColorData.add("Weiß");
	}
	
	public void initComboboxShape(){
		cbShapeData.add("Rechteck");
		cbShapeData.add("Kreis");
		cbShapeData.add("Ellipse");
		cbShapeData.add("Dreieck");
		cbShapeData.add("Quadrat");
	}
	
	public void initComboboxPen(){
		cbPenData.add("Dünn");
		cbPenData.add("Dick");
	}
	
	public void start(){
		System.out.println("Applikation ist gestartet");
		
		GuiData g = new GuiData();
		
		/*TODO: Pen sperren wenn Form ausgewählt wurde und umgekehrt*/
		g.setColor(cbColor.getSelectionModel().getSelectedItem());
		g.setShape(cbShape.getSelectionModel().getSelectedItem());
		//g.setPen(cbPen.getSelectionModel().getSelectedItem());
		
		g.setWidth(Integer.parseInt(tfWidth.getText()));
		g.setLength(Integer.parseInt(tfLength.getText()));
		
		Businesslayer b = new Businesslayer(g);
	}
	
}
