package gui;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.DatabaseController;
import logic.Player;

public class ViewTable extends Scene {
	
	TableView<Player> tbl;
	BorderPane mainPane;
	Button homeButton;
	Button exitButton;
	
	protected ViewTable (Group root, Stage stage) {
		super(root);
		
		mainPane = new BorderPane();
		
		Button homeButton = new Button("homeButton");
		exitButton = new Button("Exit");

		
		
		tbl = new TableView<Player>();
		tbl.setItems(FXCollections.observableArrayList(DatabaseController.getPlayers()));
		TableColumn<Player,String> firstNameCol = new TableColumn<Player,String>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
		TableColumn<Player,String> lastNameCol = new TableColumn<Player,String>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
		TableColumn<Player,Integer> groupNumCol = new TableColumn<Player,Integer>("Group Number");
		groupNumCol.setCellValueFactory(new PropertyValueFactory("groupNum"));
		TableColumn<Player,String> carNameCol = new TableColumn<Player,String>("Car Name");
		carNameCol.setCellValueFactory(new PropertyValueFactory("carName"));
		TableColumn<Player,String> logoCol = new TableColumn<Player,String>("Logo");
		logoCol.setCellValueFactory(new PropertyValueFactory("logo"));
		TableColumn<Player,Integer> scoreCol = new TableColumn<Player,Integer>("Score");
		scoreCol.setCellValueFactory(new PropertyValueFactory("score"));
		TableColumn<Player,String> usernameCol = new TableColumn<Player,String>("Username");
		usernameCol.setCellValueFactory(new PropertyValueFactory("username"));
		TableColumn<Player,Integer> creditsCol = new TableColumn<Player,Integer>("Credits");
		creditsCol.setCellValueFactory(new PropertyValueFactory("credits"));
		 
		tbl.getColumns().setAll(firstNameCol, lastNameCol, groupNumCol, carNameCol, logoCol, scoreCol, usernameCol, creditsCol);
		mainPane.setTop(tbl);
		mainPane.setBottom(exitButton);
		root.getChildren().add(mainPane);
		
		exitButton.setOnAction(event -> {
			stage.setScene(new HomePage(new Group(), stage));
		});
	}

}