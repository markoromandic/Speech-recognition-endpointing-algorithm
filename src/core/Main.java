package core;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view.View;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Model model = new Model();
		View view = new View(model, primaryStage);
		Controller controller = new Controller(model, view);
	}

}
