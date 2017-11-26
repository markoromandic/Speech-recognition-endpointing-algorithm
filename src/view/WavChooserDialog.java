package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Model;
import model.wavFile.WavFile;
import variables.Variables;

public class WavChooserDialog extends HBox{
	private ComboBox<String> wavSounds;
	private Button btRead, btExport;
	
	public WavChooserDialog() {
		initilaize();
	}
	
	private void initilaize() {
		wavSounds = new ComboBox<>();
		wavSounds.setMaxWidth(Integer.MAX_VALUE);
		wavSounds.getItems().addAll(Variables.WAV_FILES);
		wavSounds.getSelectionModel().selectFirst();
		wavSounds.setMinWidth(100);
		
		btRead = new Button(Variables.READ);
		btRead.setMinWidth(100);
		
		btExport = new Button(Variables.EXPORT);
		btExport.setMinWidth(100);
		
		setSpacing(15);
		setPadding(new Insets(15));
		
		setHgrow(wavSounds, Priority.ALWAYS);
		setAlignment(Pos.CENTER);
		
		getChildren().addAll(wavSounds, btRead, btExport);
	}

	public ComboBox<String> getWavSounds() {
		return wavSounds;
	}

	public Button getBtRead() {
		return btRead;
	}
	
	public Button getBtExport() {
		return btExport;
	}
}
