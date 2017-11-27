package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.DatabaseController;

public class StageController extends Application {

	@Override
	public void start(Stage stage) {
		// TODO Implement two buttons to switch scenes
		stage.setScene(new HomePage(new Group(), stage));
		stage.show();
	}

	public static void main(String[] args) {
		/*
		 * 'Platform' lambda expression found:
		 * https://stackoverflow.com/questions/17850191/why-am-i-getting-java-lang-
		 * illegalstateexception-not-on-fx-application-thread
		 */
		// Avoid throwing IllegalStateException by running from a non-JavaFX thread.
		Platform.runLater(() -> {
			// Update UI here.
			try {
				DatabaseController.parseXML();
			} catch (ClassNotFoundException e) {
				new Alert(AlertType.ERROR,
						"Fatal program error! DatabaseController not initialising. Report to system admin. "
								+ e.getMessage()).showAndWait();
			} catch (NullPointerException e) {
				new Alert(AlertType.ERROR, "Fatal program error! Database connection not valid. " + e.getMessage())
						.showAndWait();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		launch(args);
	}
}