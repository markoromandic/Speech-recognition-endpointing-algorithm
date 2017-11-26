package view;

import javafx.scene.layout.VBox;
import model.Model;

public class MainPanel extends VBox {
	private Model model;
	private WavChooserDialog wavChooserDialog;
	
	private MainPanel() {}
	
	public MainPanel(Model model) {
		this.model = model;
		initalize();
	}
	
	private void initalize() {
		wavChooserDialog = new WavChooserDialog();
		
		getChildren().addAll(wavChooserDialog);
	}
}
