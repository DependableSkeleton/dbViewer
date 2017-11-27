package gui;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HomePage extends Scene {

	Image img;
	ImageView sheridanImg;
	ImageView groupImg;
	Button adminButton;
	Button userButton;
	BorderPane mainPane;
	HBox hbox;
	HBox hbox2;

	protected HomePage(Group root, Stage stage) {
		super(root);
		
		adminButton = new Button("Admin button");
		userButton = new Button("User");
		sheridanImg = new ImageView(new Image(new File("src/assets/sc.jpg").toURI().toString()));
		groupImg = new ImageView(new Image(new File("src/assets/g2.jpeg").toURI().toString()));
		mainPane = new BorderPane();
		hbox = new HBox();
		hbox2 = new HBox();
		

		hbox.getChildren().addAll(sheridanImg, groupImg);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);

		hbox2.getChildren().addAll(adminButton, userButton);
		hbox2.setSpacing(10);
		hbox2.setAlignment(Pos.CENTER);

		mainPane.setTop(hbox);
		mainPane.setCenter(hbox2);
		mainPane.setPrefHeight(150);

		root.getChildren().add(mainPane);
		stage.setTitle("Home page");
		
		adminButton.setOnAction(e -> {
			stage.close();
			stage.setScene(new Administration(new Group(), stage));
			stage.show();
		});

		userButton.setOnAction(e -> {
			stage.close();
			stage.setScene(new User(new Group(), stage));
			stage.show();
		});
	}
}