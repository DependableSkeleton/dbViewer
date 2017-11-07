package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseController {

	private static Connection database;
	private static PreparedStatement ps;
	private static Statement stm;
	final static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String url = null;
	private static String user = null;
	private static String pass = null;
	private static String schema = null;
	private static ArrayList<String> lastName = new ArrayList<>();
	private static ArrayList<String> firstName = new ArrayList<>();
	private static ArrayList<Integer> groupNum = new ArrayList<>();

	public DatabaseController() throws ClassNotFoundException, SQLException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		String xpathExpression = "/settings";
		InputSource inputSource = new InputSource("src/assets/jdbcSettings.xml");
		try {

			NodeList lstRoot = (NodeList) xpath.compile(xpathExpression).evaluate(inputSource, XPathConstants.NODESET);
			NodeList lstChilds = lstRoot.item(0).getChildNodes();

			// parse jdbcSettings.xml for config
			for (int i = 3; i < lstChilds.getLength(); i++) {
				if (lstChilds.item(i).getLocalName() != null) {

					switch (lstChilds.item(i).getLocalName()) {
					case "user":
						user = lstChilds.item(i).getTextContent();
						break;
					case "pass":
						pass = lstChilds.item(i).getTextContent();
						break;
					case "url":
						url = lstChilds.item(i).getTextContent();
						break;
					case "schema":
						schema = lstChilds.item(i).getTextContent();
						url += schema;
						break;
					case "use_ssl":
						if (lstChilds.item(i).getTextContent().compareTo("false") == 0) {
							url += "?useSSL=false";
						}
						break;
					}
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		// connect driver
		Class.forName(jdbcDriver);
	}

	private static boolean connectToServer() {
		// establish connection
		try {
			database = DriverManager.getConnection(url, user, pass);
			return true;
		} catch (Exception SQLException) {
			new Alert(AlertType.ERROR, "Could not connect to server.").showAndWait();
			return false;
		}
	}

	private static boolean connectTodb() {
		try {
			database.setSchema(schema);
			new Alert(AlertType.INFORMATION, "Connected to database: " + schema).showAndWait();
			return true;
		} catch (Exception SQLException) {
			new Alert(AlertType.CONFIRMATION, "Database doesn't exist, creating new one.").showAndWait();
			return false;
		}
	}

	public static void createdb() throws SQLException {
		if (connectToServer() && !connectTodb()) {
			// Create database
			ps = database.prepareStatement("CREATE DATABASE ?");
			ps.setString(1, schema);
			System.out.println(ps);
			ps.execute();
			ps.close();
			database.close();
		}
	}

	public static void createTable() throws SQLException {
		if (connectToServer() && connectTodb()) {
			Scanner inputStream = null;
			lastName = new ArrayList<>();
			firstName = new ArrayList<>();
			groupNum = new ArrayList<>();
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
			// Since database created successfully, program creates a table.
			stm = database.createStatement();
			// sql statement to create the table
			String createTable = "CREATE TABLE Players (" + "First_Name VARCHAR (20)     NOT NULL, "
					+ "Last_Name VARCHAR (20)     NOT NULL, " + "Group_number int, " + "Password VARCHAR (20), "
					+ "Perfered_Car_Name VARCHAR (20) , "
					+ "logo VARCHAR (20) , score int, logIn VARCHAR(20), Credits int);";
			stm.executeUpdate(createTable);
			// close the statement stream and the database connection
			stm.close();
			database.close();
			inputStream.close();
		}
	}

	public static void checkRecord() {
		// Check info to record
		// Compare record
	}

	public static void updateRecord() {
		// Add data into record
	}

	public static Connection getDatabase() {
		return database;
	}

}