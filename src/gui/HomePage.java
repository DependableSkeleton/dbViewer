package gui;

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
	BorderPane borderpane = new BorderPane();
	HBox hbox = new HBox();
	HBox hbox2 = new HBox();

	protected HomePage(Group root, Stage stage) {
		super(root, 600, 550);
		adminButton = new Button("Admin button");
		userButton = new Button("User");
		// Sheridan image object and image viewer
		img = new Image(
				"file:///C:/Users/Lucas%20Turner/Google%20Drive/Code/workspace/dbViewer/src/assets/g2.jpeg");
		sheridanImg = new ImageView(img);
		// Group Image object and image viewer
		img = new Image("file:///C:/Users/Lucas%20Turner/Google%20Drive/Code/workspace/dbViewer/src/assets/sc.jpg");
		groupImg = new ImageView(img);

		hbox.getChildren().addAll(sheridanImg, groupImg);
		hbox.setSpacing(10);
		hbox.setAlignment(Pos.CENTER);

		hbox2.getChildren().addAll(adminButton, userButton);
		hbox2.setSpacing(10);
		hbox2.setAlignment(Pos.CENTER);

		borderpane.setTop(hbox);
		borderpane.setCenter(hbox2);

		root.getChildren().add(borderpane);

		adminButton.setOnAction(e -> {
			stage.setScene(new Administration(new Group(), stage));
		});

		userButton.setOnAction(e -> {
			stage.setScene(new User(new Group(), stage));
		});

		stage.setTitle("Home page");
	}
}