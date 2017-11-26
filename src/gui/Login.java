package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Car;
import logic.DatabaseController;

public class Login extends Scene {
	Label usernameL;
	TextField unTF;
	Label passwordL;
	PasswordField pwTF;
	Button submitButton;
	VBox vbox;

	protected Login(Group root, Stage stage) {
		super(root);

		usernameL = new Label("Username: ");
		unTF = new TextField();
		unTF.setPromptText("unTF");
		unTF.setMaxWidth(200);

		usernameL.setAlignment(Pos.CENTER);
		unTF.setAlignment(Pos.CENTER);

		passwordL = new Label("Password: ");
		pwTF = new PasswordField();
		pwTF.setPromptText("Password");
		pwTF.setMaxWidth(200);

		passwordL.setAlignment(Pos.CENTER);
		pwTF.setAlignment(Pos.CENTER);

		submitButton = new Button("Login");

		submitButton.setOnAction(event -> {
			// validate login
			if (DatabaseController.validateRecord(unTF.getText(), pwTF.getText())) {
				// start game with car
				stage.setScene(new Game(new Group(), stage,
						new Car(DatabaseController.getPrefCarName(unTF.getText(), pwTF.getText()))));
			} else {
				// return to previous scene
				stage.setScene(new User(new Group(), stage));
			}
		});
		vbox = new VBox();
		vbox.getChildren().addAll(usernameL, unTF, passwordL, pwTF, submitButton);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);
		root.getChildren().add(vbox);
	}
}