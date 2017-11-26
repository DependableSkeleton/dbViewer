package gui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Car;

public class Refill extends Scene {

	Label creditsL;
	TextField creditsTF;
	Button refillButton;
	Button exitButton;
	VBox creditRefillForm;

	protected Refill(Group root, Stage stage, Car playerCar) {
		super(root);

		// Credits refill scene
		creditsL = new Label("Credits refill");
		creditsTF = new TextField();
		refillButton = new Button("Refill");
		exitButton = new Button("Back");

		creditsTF.setMaxWidth(200);
		creditsTF.setPromptText("1000");

		creditRefillForm = new VBox();
		creditRefillForm.getChildren().addAll(creditsL, creditsTF, refillButton, exitButton);
		creditRefillForm.setSpacing(20);
		creditRefillForm.setAlignment(Pos.CENTER);

		exitButton.setOnAction(event -> {
			stage.setScene(new Game(new Group(), stage, playerCar));
		});

		root.getChildren().add(creditRefillForm);
	}
}