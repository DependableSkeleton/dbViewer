package gui;

import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.DatabaseController;

public class Registration extends Scene {
	GridPane gridPane;
	Label firstNameL;
	TextField fnTF;
	Label lastNameL;
	TextField lnTF;
	Label groupNumL;
	TextField gnTF;
	Label usernameL;
	TextField unTF;
	Label passwordL;
	TextField pwTF;
	Label carNameL;
	TextField cnTF;
	Label logoL;
	TextField logoTF;
	Button registerButton;

	protected Registration(Group root, Stage stage) {
		super(root);
		gridPane = new GridPane();
		
		// initialise girdPane items
		firstNameL = new Label("First Name");
		lastNameL = new Label("Last name");
		groupNumL = new Label("Group");
		usernameL = new Label("New Username");
		passwordL = new Label("New Password");
		carNameL = new Label("New Car Name");
		logoL = new Label("Logo");
		fnTF = new TextField();
		lnTF = new TextField();
		gnTF = new TextField();
		unTF = new TextField();
		pwTF = new TextField();
		cnTF = new TextField();
		logoTF = new TextField();
		registerButton = new Button("Submit");
		
		// setting text field prompt
		fnTF.setPromptText("First name");
		lnTF.setPromptText("Last name");
		gnTF.setPromptText("Group");
		unTF.setPromptText("New Username");
		pwTF.setPromptText("New Password");
		cnTF.setPromptText("New Car Name");
		logoTF.setPromptText("logo");
	
		// add labels to gridPane
		gridPane.add(firstNameL, 0, 0);
		gridPane.add(lastNameL, 0, 1);
		gridPane.add(groupNumL, 0, 2);
		gridPane.add(usernameL, 0, 3);
		gridPane.add(passwordL, 0, 4);
		gridPane.add(carNameL, 0, 5);
		gridPane.add(logoL, 0, 8);
		
		// add text fields to gridPane
		gridPane.add(fnTF, 1, 0);			
		gridPane.add(lnTF, 1, 1);
		gridPane.add(gnTF, 1, 2);
		gridPane.add(unTF, 1, 3);
		gridPane.add(pwTF, 1, 4);
		gridPane.add(cnTF, 1, 5);
		gridPane.add(logoTF, 1, 8);
		
		// add button to gridPane
		gridPane.add(registerButton, 1, 9);
		
		// configure gridPane
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		root.getChildren().add(gridPane);
		stage.setTitle("Register");
		
		registerButton.setOnAction(event -> {
			try {
				DatabaseController.validateRecord(fnTF.getText(), lnTF.getText(), Integer.valueOf(gnTF.getText()),
						unTF.getText(), pwTF.getText(), cnTF.getText(), logoTF.getText());
				new Alert(AlertType.INFORMATION, "User: " + fnTF.getText() + " found, registration complete!")
						.showAndWait();
				stage.setScene(new User(new Group(), stage));
			} catch (SQLException e1) {
				new Alert(AlertType.ERROR,
						"User: " + fnTF.getText() + " not found, you must be part of the class to register. " + e1.getMessage())
								.showAndWait();
				
			}
		});
	}
}