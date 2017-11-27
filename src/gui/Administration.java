package gui;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.DatabaseController;

public class Administration extends Scene {

	Button createTable;
	Button createdb;
	Button viewdb;
	Button exitButton;
	GridPane gridPane;

	protected Administration(Group root, Stage stage) {
		super(root, 600, 550);
		createTable = new Button("Create table");
		createdb = new Button("Initalize database");
		viewdb = new Button("View database");
		exitButton = new Button("Exit");

		exitButton.setOnAction(event -> {
			stage.setScene(new HomePage(new Group(), stage));
		});

		createTable.setOnAction(event -> {
			try {
				DatabaseController.createTable();
				new Alert(AlertType.INFORMATION, "Table 'players', filled with student list.").showAndWait();
			} catch (SQLException e) {
				new Alert(AlertType.ERROR, "Unable to access database. " + e.getMessage())
						.showAndWait();
			} catch (FileNotFoundException e) {
				new Alert(AlertType.ERROR, "Missing student list file. " + e.getMessage()).showAndWait();
			}
		});

		createdb.setOnAction(event -> {
			try {
				DatabaseController.createdb();
				new Alert(AlertType.INFORMATION, "Successfully connected to database: students").showAndWait();
			} catch (SQLException e) {
				new Alert(AlertType.ERROR, "Unable to access database. " + e.getMessage())
						.showAndWait();
			}
		});
		
		viewdb.setOnAction(event -> {
			stage.setScene(new ViewTable(new Group(), stage));
		});

		gridPane = new GridPane();
		gridPane.add(createTable, 0, 0);
		gridPane.add(createdb, 1, 0);
		gridPane.add(viewdb, 2, 0);
		gridPane.add(exitButton, 2, 4);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		root.getChildren().add(gridPane);
		stage.setTitle("Administration");
	}

}
