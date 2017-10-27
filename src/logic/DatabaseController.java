package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseController {

	static Connection database = null;
	static Statement stm = null;
	final static String jdbcDriver = "com.mysql.jdbc.Driver";
	final static String url = "jdbc:mysql://localhost:3306/?SSL=false";
	final static String USER = "root";
	final static String PWD = "root";
	final static String dbName = "prog";
	final static ArrayList<String> lastName = new ArrayList<>();
	final static ArrayList<String> firstName = new ArrayList<>();
	final static ArrayList<Integer> groupNum = new ArrayList<>();

	public static void readToDB() throws SQLException, ClassNotFoundException {
		Scanner inputStream = null;
		ArrayList<String> lastName = new ArrayList<>();
		ArrayList<String> firstName = new ArrayList<>();
		ArrayList<Integer> groupNum = new ArrayList<>();
		try {
			inputStream = new Scanner(new File("src/logic/Prog32758Students.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		inputStream.useDelimiter(",");
		while (inputStream.hasNext()) {
			lastName.add(inputStream.next());
			firstName.add(inputStream.next());
			groupNum.add(new Integer(inputStream.nextLine().substring(1)));
		}
		// register JDBC driver
		Class.forName(jdbcDriver);

		// establish connection
		database = DriverManager.getConnection(url, USER, PWD);

		// Create database
		stm = database.createStatement();
		String createDB = "CREATE DATABASE " + dbName;
		stm.executeUpdate(createDB);

		// close the statement stream and the database connection
		stm.close();
		database.close();
		// Since database created successfully, program creates a table.
		// Establish connection with database required
		DatabaseController.checkConnection();
		try {
			stm = database.createStatement();
			String createTable = "CREATE TABLE Players (" + "First_Name VARCHAR (20)     NOT NULL, "
					+ "Last_Name VARCHAR (20)     NOT NULL, " + "Group_number int, " + "Password VARCHAR (20), "
					+ "Perfered_Car_Name VARCHAR (20) , " + "logo VARCHAR (20) , score int);";

			stm.executeUpdate(createTable);
			System.out.println("Create table created");
		}

		catch (Exception exp) {
			System.out.println(exp.getMessage());
		}

		inputStream.close();
	}

	public static boolean checkConnection() throws ClassNotFoundException, SQLException {
		// checks if the database exists
		try {
			database = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?SSL=false", "root",
					"root");
			return true;
		} catch (Exception exp) {
			// If it doesn't exist it returns false hence alert database doesn't
			// exist
			System.out.println("creating database");
			// Since database doesn't exist, the program creates it.
			DatabaseController.readToDB();
			return false;
		}
	}

	public static void checkRecord() {
		// Check info to record
		// Compare record
	}

	public static void updateRecord() {
		// Add data into record
	}

}