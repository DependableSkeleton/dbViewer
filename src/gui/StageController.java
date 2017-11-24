<<<<<<< HEAD
package gui;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
	public void start(Stage stage) {
		// TODO Implement two buttons to switch scenes
		stage.setScene(new HomePage(new Group(), stage));
		stage.show();
	}

	public static void main(String[] args) {
		/*
		 * 'Platform' lambda expression found:
		 * https://stackoverflow.com/questions/17850191/why-am-i-getting-java-lang-
		 * illegalstateexception-not-on-fx-application-thread
		 */
		// Avoid throwing IllegalStateException by running from a non-JavaFX thread.
		Platform.runLater(() -> {
			// Update UI here.
			try {
				DatabaseController.parseXML();
			} catch (ClassNotFoundException e) {
				new Alert(AlertType.ERROR,
						"Fatal program error! DatabaseController not initialising. Report to system admin. "
								+ e.getMessage()).showAndWait();
			} catch (NullPointerException e) {
				new Alert(AlertType.ERROR, "Fatal program error! Database connection not valid. " + e.getMessage())
						.showAndWait();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		launch(args);
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

	/*
	 * 
	 * ADMIN SCENE
	 * 
	 */
	public class Administration extends Scene {

		Button createTable;
		Button createdb;
		Button show;
		Button exitButton;
		GridPane gridPane;

		public Administration(Group root, Stage stage) {
			super(root, 600, 550);
			createTable = new Button("Create table");
			createdb = new Button("Initalize database");
			show = new Button("View database");
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

			gridPane = new GridPane();
			gridPane.add(createTable, 0, 0);
			gridPane.add(createdb, 1, 0);
			gridPane.add(show, 2, 0);
			gridPane.add(exitButton, 2, 4);
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setHgap(10);
			gridPane.setVgap(10);

			root.getChildren().add(gridPane);
			stage.setTitle("Administration");
		}

	}

	private class User extends Scene {

		Button loginButton;
		Button registerButton;
		Button exitButton;

		public User(Group root, Stage stage) {
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
				stage.setScene(new Register(new Group(), stage));
			});
		}

	}

	private class Login extends Scene {
		Label usernameL;
		TextField unTF;
		Label passwordL;
		PasswordField pwTF;
		Button submitButton;
		VBox vbox;

		public Login(Group root, Stage stage) {
			super(root);

			usernameL = new Label("Username: ");
			unTF = new TextField();
			unTF.setPromptText("unTF");
			unTF.setMaxWidth(200);

			usernameL.setAlignment(Pos.CENTER);
			unTF.setAlignment(Pos.CENTER);

			passwordL = new Label("Password: ");
			pwTF = new PasswordField();
			pwTF.setPromptText("Password");
			pwTF.setMaxWidth(200);

			passwordL.setAlignment(Pos.CENTER);
			pwTF.setAlignment(Pos.CENTER);

			submitButton = new Button("Login");

			submitButton.setOnAction(event -> {
				try {
					if (DatabaseController.validateRecord(unTF.getText(), pwTF.getText())) {
						new Alert(AlertType.INFORMATION, "User: " + unTF.getText() + " found, login complete!")
						.showAndWait();
					} else {
						new Alert(AlertType.ERROR, "User: " + unTF.getText() + " not found, you must be registered to login.").showAndWait();
						stage.setScene(new User(new Group(), stage));
					}
				} catch (SQLException e) {
					new Alert(AlertType.ERROR, "Error submitting query. " + e.getMessage()).showAndWait();
				}
			});

			vbox = new VBox();
			vbox.getChildren().addAll(usernameL, unTF, passwordL, pwTF, submitButton);
			vbox.setSpacing(10);
			vbox.setAlignment(Pos.CENTER);
			
			root.getChildren().add(vbox);
		}

	}

	private class Register extends Scene {
		GridPane gridPane;
		Label firstNameL;
		TextField fnTF;
		Label lastNameL;
		TextField lnTF;
		Label groupNumL;
		TextField gnTF;
		Label usernameL;
		TextField unTF;
		Label passwordL;
		TextField pwTF;
		Label carNameL;
		TextField cnTF;
		Label logoL;
		TextField logoTF;
		Button registerButton;

		public Register(Group root, Stage stage) {
			super(root);
			gridPane = new GridPane();
			
			// initialise girdPane items
			firstNameL = new Label("First Name");
			lastNameL = new Label("Last name");
			groupNumL = new Label("Group");
			usernameL = new Label("New Username");
			passwordL = new Label("New Password");
			carNameL = new Label("New Car Name");
			logoL = new Label("Logo");
			fnTF = new TextField();
			lnTF = new TextField();
			gnTF = new TextField();
			unTF = new TextField();
			pwTF = new TextField();
			cnTF = new TextField();
			logoTF = new TextField();
			registerButton = new Button("Submit");
			
			// setting text field prompt
			fnTF.setPromptText("First name");
			lnTF.setPromptText("Last name");
			gnTF.setPromptText("Group");
			unTF.setPromptText("New Username");
			pwTF.setPromptText("New Password");
			cnTF.setPromptText("New Car Name");
			logoTF.setPromptText("logo");
		
			// add labels to gridPane
			gridPane.add(firstNameL, 0, 0);
			gridPane.add(lastNameL, 0, 1);
			gridPane.add(groupNumL, 0, 2);
			gridPane.add(usernameL, 0, 3);
			gridPane.add(passwordL, 0, 4);
			gridPane.add(carNameL, 0, 5);
			gridPane.add(logoL, 0, 8);
			
			// add text fields to gridPane
			gridPane.add(fnTF, 1, 0);			
			gridPane.add(lnTF, 1, 1);
			gridPane.add(gnTF, 1, 2);
			gridPane.add(unTF, 1, 3);
			gridPane.add(pwTF, 1, 4);
			gridPane.add(cnTF, 1, 5);
			gridPane.add(logoTF, 1, 8);
			
			// add button to gridPane
			gridPane.add(registerButton, 1, 9);
			
			// configure gridPane
			gridPane.setVgap(10);
			gridPane.setAlignment(Pos.CENTER);
			root.getChildren().add(gridPane);
			stage.setTitle("Register");
			
			registerButton.setOnAction(event -> {
				try {
					DatabaseController.validateRecord(fnTF.getText(), lnTF.getText(), Integer.valueOf(gnTF.getText()),
							unTF.getText(), pwTF.getText(), cnTF.getText(), logoTF.getText());
					new Alert(AlertType.INFORMATION, "User: " + fnTF.getText() + " found, registration complete!")
							.showAndWait();
					stage.setScene(new User(new Group(), stage));
				} catch (SQLException e1) {
					new Alert(AlertType.ERROR,
							"User: " + fnTF.getText() + " not found, you must be part of the class to register. " + e1.getMessage())
									.showAndWait();
					
				}
			});

			/*
			 * Label creditL = new Label("Credit"); TextField creditTF = new TextField();
			 * creditTF.setPromptText("Credit"); gridPane.add(creditL, 0, 6);
			 * gridPane.add(creditTF, 1, 6);
			 * 
			 * Label scoreL = new Label("Score"); TextField scoreTF = new TextField();
			 * scoreTF.setPromptText("score"); gridPane.add(scoreL, 0, 7);
			 * gridPane.add(scoreTF, 1, 7);
			 */
		}

	}
}
=======
package gui;

import java.io.FileNotFoundException;
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
	public void start(Stage stage) {
		// TODO Implement two buttons to switch scenes
		stage.setScene(new HomePage(new Group(), stage));
		stage.show();
	}

	public static void main(String[] args) {
		try {
			new DatabaseController();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(AlertType.ERROR, "Fatal program error! DatabaseController not initialising. Report to system admin.").showAndWait();
		}
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

		Button createTable;
		Button createdb;
		Button show;
		Button exitButton;

		public Administration(Group root, Stage stage) {
			super(root, 600, 550);
			createTable = new Button("Create table");
			createdb = new Button("Initalize database");
			show = new Button("View database");
			exitButton = new Button("Exit");

			exitButton.setOnAction(event -> {
				stage.setScene(new HomePage(new Group(), stage));
			});

			createTable.setOnAction(event -> {
				try {
					DatabaseController.createTable();
				} catch (SQLException e) {
					new Alert(AlertType.ERROR, "Connection to database lost unexpectedly. " + e.getMessage())
							.showAndWait();
				} catch (FileNotFoundException e) {
					new Alert(AlertType.ERROR, "Missing student list file. " + e.getMessage())
					.showAndWait();
				}
			});

			createdb.setOnAction(event -> {
				try {
					DatabaseController.createdb();
				} catch (SQLException e) {
					new Alert(AlertType.ERROR, "Connection to database lost unexpectedly. " + e.getMessage())
							.showAndWait();
				}
			});

			GridPane gridpane = new GridPane();
			gridpane.add(createTable, 0, 0);
			gridpane.add(createdb, 1, 0);
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

			Label groupNumL = new Label("Group");
			TextField gnTF = new TextField();
			gnTF.setPromptText("Group");
			gridPane.add(groupNumL, 0, 2);
			gridPane.add(gnTF, 1, 2);

			Label usernameL = new Label("New Username");
			TextField unTF = new TextField();
			unTF.setPromptText("New Username");
			gridPane.add(usernameL, 0, 3);
			gridPane.add(unTF, 1, 3);

			Label passwordL = new Label("New Password");
			TextField pwdTF = new TextField();
			pwdTF.setPromptText("New Password");
			gridPane.add(passwordL, 0, 4);
			gridPane.add(pwdTF, 1, 4);

			Label carNameL = new Label("New Car Name");
			TextField cnTF = new TextField();
			cnTF.setPromptText("New Car Name");
			gridPane.add(carNameL, 0, 5);
			gridPane.add(cnTF, 1, 5);

			/*
			 * Label creditL = new Label("Credit"); TextField creditTF = new TextField();
			 * creditTF.setPromptText("Credit"); gridPane.add(creditL, 0, 6);
			 * gridPane.add(creditTF, 1, 6);
			 * 
			 * Label scoreL = new Label("Score"); TextField scoreTF = new TextField();
			 * scoreTF.setPromptText("score"); gridPane.add(scoreL, 0, 7);
			 * gridPane.add(scoreTF, 1, 7);
			 */

			Label logoL = new Label("Logo");
			TextField logoTF = new TextField();
			logoTF.setPromptText("logo");
			gridPane.add(logoL, 0, 8);
			gridPane.add(logoTF, 1, 8);

			Button registerButton = new Button("Submit");
			registerButton.setOnAction(e -> {
				try {
					DatabaseController.updateRecord(fnTF.getText(), lnTF.getText(), Integer.getInteger(gnTF.getText()),
							unTF.getText(), pwdTF.getText(), cnTF.getText(), logoTF.getText());
					new Alert(AlertType.INFORMATION, "User: " + fnTF.getText() + " found, registration complete!").showAndWait();
				} catch (SQLException e1) {
					new Alert(AlertType.ERROR, "User: " + fnTF.getText() + " not found, you must be part of the class to register.").showAndWait();
				}
			});

			gridPane.add(registerButton, 1, 9);
			gridPane.setVgap(10);

			gridPane.setAlignment(Pos.CENTER);
		}

	}
}
>>>>>>> refs/remotes/origin/master
