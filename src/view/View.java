package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import variables.Variables;

public class View extends VBox {
	private Model model;
	private WavChooserDialog wavChooserDialog;
	private LpcParameterDialog lpcParameterDialog;
	private Stage primaryStage;

	private View() {
	}

	public View(Model model, Stage primaryStage) {
		this.model = model;
		this.primaryStage = primaryStage;
		initializeStage();
		initalize();
	}

	private void initializeStage() {
		primaryStage.setTitle("Wave reader RAF 2.0");
		primaryStage.getIcons().add(new Image(Variables.ICON));
	}

	public void initalize() {
		wavChooserDialog = new WavChooserDialog();
		
		HBox hb = new HBox(20);
		hb.setAlignment(Pos.CENTER_LEFT);
		lpcParameterDialog = new LpcParameterDialog();
		
		hb.getChildren().addAll(lpcParameterDialog);
		
		getChildren().addAll(wavChooserDialog, hb);
		primaryStage.setScene(new Scene(this, 1250, 750));
		primaryStage.show();
	}

	public WavChooserDialog getWavChooserDialog() {
		return wavChooserDialog;
	}
	
	public LpcParameterDialog getLpcParameterDialog() {
		return lpcParameterDialog;
	}
}
