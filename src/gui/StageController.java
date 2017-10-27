package gui;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.DatabaseController;

public class StageController extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Implement two buttons to switch scenes
		stage.setScene(new HomePage(new Group(), stage));
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	/*
	 * 
	 * HOMEPAGE SCENE
	 * 
	 */
	private class HomePage extends Scene {

		Image img;
		ImageView sheridanImg;
		ImageView groupImg;
		Button adminButton;
		Button userButton;
		BorderPane borderpane = new BorderPane();
		HBox hbox = new HBox();
		HBox hbox2 = new HBox();

		public HomePage(Group root, Stage stage) {
			super(root, 600, 550);
			adminButton = new Button("Admin button");
			userButton = new Button("User");
			// Sheridan Image object and image viewer
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
				stage.setScene(new Login(new Group(), stage));
			});

			stage.setTitle("Home page");
		}

	}

	/*
	 * 
	 * ADMIN SCENE
	 * 
	 */
	public class Administration extends Scene {

		Button createDB;
		Button init;
		Button show;
		Button exitButton;

		public Administration(Group root, Stage stage) {
			super(root, 600, 550);
			createDB = new Button("Create database");
			init = new Button("Initalize database");
			show = new Button("View database");
			exitButton = new Button("Exit");

			exitButton.setOnAction(e -> {
				stage.setScene(new HomePage(new Group(), stage));
			});

			createDB.setOnAction(e -> {
				boolean dbexist = false;
				try {
					dbexist = DatabaseController.checkConnection();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (dbexist == false) {
					new Alert(AlertType.INFORMATION, "Database Created!").show();
					new Alert(AlertType.INFORMATION, "Table created succesfully!");
					init.setDisable(false);
				} else {
					new Alert(AlertType.INFORMATION, "Database Already exist!");
				}
			});

			init.setOnAction(e -> {
				boolean dbexist = false;
				try {
					dbexist = DatabaseController.checkConnection();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (dbexist == false) {
					new Alert(AlertType.INFORMATION, "Database doesn't exist, create one first");
					init.setDisable(true);
				} else {
					try {
						DatabaseController.readToDB();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});

			GridPane gridpane = new GridPane();
			gridpane.add(createDB, 0, 0);
			gridpane.add(init, 1, 0);
			gridpane.add(show, 2, 0);
			gridpane.add(exitButton, 2, 4);
			gridpane.setAlignment(Pos.CENTER);
			gridpane.setHgap(10);
			gridpane.setVgap(10);

			root.getChildren().add(gridpane);
		}

	}

	private class User extends Scene {

		Button loginButton;
		Button registerButton;

		public User(Parent root, Stage stage) {
			super(root);

			// register-login scene
			loginButton = new Button("Log in");
			registerButton = new Button("Sign up");
			HBox hbox3 = new HBox();
			hbox3.getChildren().addAll(loginButton, registerButton);
			hbox3.setAlignment(Pos.CENTER);
			hbox3.setSpacing(20);

			loginButton.setOnAction(e -> {
				stage.setScene(new Login(new Group(), stage));
			});

			registerButton.setOnAction(e -> {
				stage.setScene(new Register(new Group(), stage));
			});
		}

	}

	private class Login extends Scene {

		public Login(Parent root, Stage stage) {
			super(root);

			Label loginLabel = new Label("Log in");
			TextField loginTF = new TextField();
			loginTF.setPromptText("Log in");
			loginTF.setMaxWidth(200);

			loginLabel.setAlignment(Pos.CENTER);
			loginTF.setAlignment(Pos.CENTER);

			Label passwordLabel = new Label("Password");
			PasswordField passwordTF = new PasswordField();
			passwordTF.setMaxWidth(200);

			passwordLabel.setAlignment(Pos.CENTER);
			passwordTF.setAlignment(Pos.CENTER);

			Button submitButton = new Button("Login");

			submitButton.setOnAction(e -> {

				String loginInfo = loginTF.getText();
				String passwordInfo = passwordTF.getText();
				System.out.println("Login: " + loginInfo + "\nPassword: " + passwordInfo);

				// Some logic to be added here to find out if creditials are correct
			});

			VBox vbox = new VBox();

			vbox.getChildren().addAll(loginLabel, loginTF, passwordLabel, passwordTF, submitButton);
			vbox.setSpacing(10);
			vbox.setAlignment(Pos.CENTER);
		}

	}

	private class Register extends Scene {

		public Register(Parent root, Stage stage) {
			super(root);
			GridPane gridPane = new GridPane();

			Label firstNameL = new Label("First name");
			TextField fnTF = new TextField();
			fnTF.setPromptText("First name");
			gridPane.add(firstNameL, 0, 0);
			gridPane.add(fnTF, 1, 0);

			Label lastNameL = new Label("Last name");
			TextField lnTF = new TextField();
			lnTF.setPromptText("Last name");
			gridPane.add(lastNameL, 0, 1);
			gridPane.add(lnTF, 1, 1);

			Label groupL = new Label("Group");
			TextField groupTF = new TextField();
			groupTF.setPromptText("Group");
			gridPane.add(groupL, 0, 2);
			gridPane.add(groupTF, 1, 2);

			Label loginL = new Label("Login");
			TextField logInTF = new TextField();
			logInTF.setPromptText("log in");
			gridPane.add(loginL, 0, 3);
			gridPane.add(logInTF, 1, 3);

			Label passwordL = new Label("Password");
			TextField pwdTF = new TextField();
			pwdTF.setPromptText("Password");
			gridPane.add(passwordL, 0, 4);
			gridPane.add(pwdTF, 1, 4);

			Label carNameL = new Label("perfered car name");
			TextField carNameTF = new TextField();
			carNameTF.setPromptText("Car name");
			gridPane.add(carNameL, 0, 5);
			gridPane.add(carNameTF, 1, 5);

			Label creditL = new Label("Credit");
			TextField creditTF = new TextField();
			creditTF.setPromptText("Credit");
			gridPane.add(creditL, 0, 6);
			gridPane.add(creditTF, 1, 6);

			Label scoreL = new Label("Score");
			TextField scoreTF = new TextField();
			scoreTF.setPromptText("score");
			gridPane.add(scoreL, 0, 7);
			gridPane.add(scoreTF, 1, 7);

			Label logoL = new Label("Logo");
			TextField logoTF = new TextField();
			logoTF.setPromptText("logo");
			gridPane.add(logoL, 0, 8);
			gridPane.add(logoTF, 1, 8);

			Button registerButton = new Button("Register");
			registerButton.setOnAction(e -> {
				System.out.println("runnig..");
				// some logic here to check if the person is registered
			});

			gridPane.add(registerButton, 1, 9);
			gridPane.setVgap(10);

			gridPane.setAlignment(Pos.CENTER);
		}

	}
}
