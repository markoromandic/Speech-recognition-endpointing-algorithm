package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import variables.Variables;

public class LpcParameterDialog extends VBox{
	private ComboBox<String> cbFrameFuncs;
	private Button btCalucalte;
	private TextField tfLpcWindowWidth;
	private TextField tfOverlap;
	private TextField tfVecNum;
	
	public LpcParameterDialog() {
		initialize();
	}
	
	private void initialize() {
		tfLpcWindowWidth = new TextField();
		tfOverlap = new TextField();
		tfVecNum = new TextField();
		
		cbFrameFuncs = new ComboBox();
		cbFrameFuncs.setMaxWidth(100);
		cbFrameFuncs.getItems().addAll(Variables.FRAME_FUNCS);
		cbFrameFuncs.getSelectionModel().selectFirst();
		cbFrameFuncs.setMinWidth(100);
		
		btCalucalte = new Button(Variables.CALCUALTE);
		
		setPadding(new Insets(20));
		setSpacing(20);
		
		getChildren().addAll(tfLpcWindowWidth, tfOverlap, tfVecNum, cbFrameFuncs, btCalucalte);
	}
	
	public ComboBox<String> getCbFrameFuncs() {
		return cbFrameFuncs;
	}
}
