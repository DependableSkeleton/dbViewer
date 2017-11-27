package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import gui.Login;

public class User extends Scene {

	Button loginButton;
	Button registerButton;
	Button exitButton;
	BorderPane mainPane;
	HBox hbox;

	protected User(Group root, Stage stage) {
		super(root);

		loginButton = new Button("Log in");
		registerButton = new Button("Sign up");
		exitButton = new Button("Back");
		hbox = new HBox();
		mainPane = new BorderPane();
		
		hbox.getChildren().addAll(loginButton, registerButton, exitButton);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);
		
		mainPane.setCenter(hbox);
		mainPane.setPrefSize(300, 100);
	
		root.getChildren().add(mainPane);
		stage.setTitle("User");
		
		exitButton.setOnAction(e -> {
			stage.close();
			stage.setScene(new HomePage(new Group(), stage));
			stage.show();
		});

		loginButton.setOnAction(e -> {
			stage.close();
			stage.setScene(new Login(new Group(), stage));
			stage.show();
		});

		registerButton.setOnAction(e -> {
			stage.close();
			stage.setScene(new Registration(new Group(), stage));
			stage.show();
		});
	}
}