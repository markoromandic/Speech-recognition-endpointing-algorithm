package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import variables.Variables;

public class WavChooserDialog extends HBox {
	private ComboBox<String> wavSounds;
	private ComboBox<String> frameFuncs;
	private Button btRead, btExport;
	private TextField tfDuration;

	public WavChooserDialog() {
		initilaize();
	}

	public void initilaize() {
		wavSounds = new ComboBox<>();
		wavSounds.setMaxWidth(Integer.MAX_VALUE);
		wavSounds.getItems().addAll(Variables.WAV_FILES);
		wavSounds.getSelectionModel().selectFirst();
		wavSounds.setMinWidth(100);

		frameFuncs = new ComboBox();
		frameFuncs.setMaxWidth(100);
		frameFuncs.getItems().addAll(Variables.FRAME_FUNCS);
		frameFuncs.getSelectionModel().selectFirst();
		frameFuncs.setMinWidth(100);

		tfDuration = new TextField();
		tfDuration.setMinWidth(50);
		tfDuration.setMaxWidth(50);
		tfDuration.setDisable(true);

		btRead = new Button(Variables.READ);
		btRead.setMinWidth(100);

		btExport = new Button(Variables.EXPORT);
		btExport.setMinWidth(100);
		btExport.setDisable(true);

		setSpacing(15);
		setPadding(new Insets(15));

		setHgrow(wavSounds, Priority.ALWAYS);
		setAlignment(Pos.CENTER);

		getChildren().addAll(wavSounds, frameFuncs, tfDuration, btRead, btExport);
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

	public TextField getTfDuration() {
		return tfDuration;
	}

	public ComboBox<String> getFrameFuncs() {
		return frameFuncs;
	}
}
