package gui;

import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
			try {
				if (DatabaseController.validateRecord(unTF.getText(), pwTF.getText())) {
					new Alert(AlertType.INFORMATION, "User: " + unTF.getText() + " found, login complete!")
					.showAndWait();
				} else {
					new Alert(AlertType.ERROR, "User: " + unTF.getText() + " not found, you must be registered to login.").showAndWait();
					stage.setScene(new User(new Group(), stage));
				}
			} catch (SQLException e) {
				new Alert(AlertType.ERROR, "Error submitting query. " + e.getMessage()).showAndWait();
			}
		});
		vbox = new VBox();
		vbox.getChildren().addAll(usernameL, unTF, passwordL, pwTF, submitButton);
		vbox.setSpacing(10);
		vbox.setAlignment(Pos.CENTER);	
		root.getChildren().add(vbox);
	}
}