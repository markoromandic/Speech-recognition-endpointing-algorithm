package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Model;
import variables.Variables;
import view.View;

public class Controller {
	private Model model;
	private View view;

	public Controller(Model model, View view) {
		this.view = view;
		this.model = model;
		initializeWavChooserDialogActions();
	}

	private void initializeWavChooserDialogActions() {
		view.getWavChooserDialog().getWavSounds().getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<?> ov, Object t, Object t1) {
						String current = view.getWavChooserDialog().getWavSounds().getSelectionModel().getSelectedItem()
								.toString();

						if (current.equals(Variables.RECORD_WAV)) {
							view.getWavChooserDialog().getBtRead().setText(Variables.RECORD);
							view.getWavChooserDialog().getTfDuration().setDisable(false);
						} else {
							view.getWavChooserDialog().getBtRead().setText(Variables.READ);
							view.getWavChooserDialog().getTfDuration().setDisable(true);
						}
					}
				});

		view.getWavChooserDialog().getBtRead().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String choosenWav = view.getWavChooserDialog().getWavSounds().getSelectionModel().getSelectedItem();

				if (choosenWav.equals(Variables.RECORD_WAV)) {
					String durationS = view.getWavChooserDialog().getTfDuration().getText();
					long duration = 1;

					if (!durationS.equals(""))
						duration = Long.parseLong(durationS);

					model.startRecording(duration);

					model.startReadingWav(Variables.RECORDED_AUDIO);
					if (model.isRead())
						view.getWavChooserDialog().getBtExport().setDisable(false);
				} else {
					model.startReadingWav(choosenWav);
					System.out.println("Reading wav");
				}
			}
		});

		view.getWavChooserDialog().getBtExport().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Do export");
			}
		});
	}

}
