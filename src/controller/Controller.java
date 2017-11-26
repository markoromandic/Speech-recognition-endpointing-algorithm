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

						if (current.equals(Variables.RECORDED_WAV)) {
							view.getWavChooserDialog().getBtRead().setText(Variables.RECORD);
						} else {
							view.getWavChooserDialog().getBtRead().setText(Variables.READ);
						}
					}
				});

		view.getWavChooserDialog().getBtRead().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String current = view.getWavChooserDialog().getWavSounds().getSelectionModel().getSelectedItem()
						.toString();

				if (current.equals(Variables.RECORDED_WAV)) {
					model.startRecording();
					System.out.println("Reading wav");
				} else {
					System.out.println("Reading wav");
				}
			}
		});
	}

}
