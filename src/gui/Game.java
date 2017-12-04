package gui;

import java.io.File;
import java.net.MalformedURLException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import logic.Car;
import logic.DatabaseController;
import logic.UseMyCar;

public class Game extends Scene {
	
	BorderPane mainPane;
	GridPane gamePane;
	VBox gameOptions;
	HBox header;
	ImageView sheridanImg;
	ImageView groupImg;
	ImageView creditRefill;
	ImageView musicControls;
	ImageView score;
	Label headerL;
	Media media;
	MediaPlayer mediaPlayer;
	MediaView mediaView;
	Button playButton;
	Button homeButton;
	Label creditLabel;
	static TextArea gameScreen;

	protected Game(Group root, Stage stage, Car playerCar) {
		super(root);
		
		mainPane = new BorderPane();
		gamePane = new GridPane();
		gameOptions = new VBox();
		header = new HBox();
								
		sheridanImg = new ImageView(new Image(new File("src/assets/sc.jpg").toURI().toString()));
		groupImg = new ImageView(new Image(new File("src/assets/g2.jpeg").toURI().toString()));
		creditRefill = new ImageView(new Image(new File("src/assets/creditImg.png").toURI().toString()));
		musicControls = new ImageView(new Image(new File("src/assets/musicImg.jpg").toURI().toString()));
		score = new ImageView(new Image(new File("src/assets/scoreImg.png").toURI().toString()));
		
		
		headerL = new Label("Car racing game");
		
		playButton = new Button("Start Game");
		homeButton = new Button("Exit");
		
		creditLabel = new Label("Credits: " + DatabaseController.getCredits(playerCar.getCarName()));
		
		gameScreen = new TextArea();
		gameScreen.setPrefColumnCount(50);
		gameScreen.setPrefRowCount(25);
		
		header.getChildren().addAll(sheridanImg,headerL,groupImg);
		header.setAlignment(Pos.CENTER); 
		
		//Music: https://www.bensound.com/royalty-free-music
		//Track used: https://www.bensound.com/royalty-free-music/track/slow-motion
		try {
			media = new Media(new File("src/assets/bensound-slowmotion.mp3").toURI().toURL().toString());
		} catch (MalformedURLException e) {
			new Alert(AlertType.ERROR, "Error accessing application assets. Please validate or reinstall.").showAndWait();
			stage.setScene(new User(new Group(), stage));
		}
		mediaPlayer = new MediaPlayer(media);
		
		gameOptions.getChildren().addAll(creditRefill, musicControls, score);
		gameOptions.setAlignment(Pos.BASELINE_RIGHT);	
		
		//gamePane.add(gameScreen, 0, 0);
		gamePane.add(playButton, 0, 1);
		gamePane.add(homeButton, 1, 1);
		gamePane.add(creditLabel, 2, 1);
		gamePane.setAlignment(Pos.CENTER);
				
		mainPane.setTop(header);
		mainPane.setRight(gameOptions);
		mainPane.setCenter(gamePane);
				
		stage.setTitle("Car Racing");
		root.getChildren().add(mainPane);
		
		creditRefill.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.setScene(new Refill(new Group(), stage, playerCar));
		});

		musicControls.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
				mediaPlayer.play();
			} else {
				mediaPlayer.stop();
			}
		});
		
		score.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			stage.setScene(new Score(new Group(), stage, playerCar));
		});

		playButton.setOnAction(event -> {
			if (DatabaseController.getCredits(playerCar.getCarName()) > 0) {
				UseMyCar.startGame(playerCar);
			} else {
				new Alert(AlertType.ERROR, "Error submitting query. ");
			}
		});

		homeButton.setOnAction(event -> {
			stage.setScene(new HomePage(new Group(), stage));
		});
	}

	public static TextArea getGameScreen() {
		return gameScreen;
	}
}