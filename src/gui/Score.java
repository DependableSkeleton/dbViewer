package gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.Car;
import logic.DatabaseController;

public class Score extends Scene {
	
	Label label;
	Button exitButton;
	BorderPane mainPane;
	public Score(Group root, Stage stage, Car playerCar) {
		super(root, 250, 50);
		mainPane = new BorderPane();
		
		label = new Label("Score: " + DatabaseController.getScore(playerCar.getCarName()));
		
		exitButton = new Button("Exit");

		exitButton.setOnAction(event -> {
			stage.setScene(new Game(new Group(), stage, playerCar));
		});
		
		mainPane.setTop(label);
		mainPane.setBottom(exitButton);
		root.getChildren().add(mainPane);
		
		
	}

}
