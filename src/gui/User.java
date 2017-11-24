package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import gui.Login;

public class User extends Scene {

	Button loginButton;
	Button registerButton;
	Button exitButton;

	protected User(Group root, Stage stage) {
		super(root, 600, 550);

		// register-login scene
		loginButton = new Button("Log in");
		registerButton = new Button("Sign up");
		exitButton = new Button("Back");
		HBox hbox3 = new HBox();
		hbox3.getChildren().addAll(loginButton, registerButton, exitButton);
		hbox3.setAlignment(Pos.CENTER);
		hbox3.setSpacing(20);

		root.getChildren().add(hbox3);
		stage.setTitle("User");
		
		exitButton.setOnAction(e -> {
			stage.setScene(new HomePage(new Group(), stage));
		});

		loginButton.setOnAction(e -> {
			stage.setScene(new Login(new Group(), stage));
		});

		registerButton.setOnAction(e -> {
			stage.setScene(new Registration(new Group(), stage));
		});
	}
}