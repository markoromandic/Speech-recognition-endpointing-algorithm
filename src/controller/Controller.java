package controller;


import model.Model;
import view.View;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(Model model, View view) {
		this.view = view;
		this.model = model;
	}
}
