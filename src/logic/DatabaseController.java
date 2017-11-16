package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	// -- DatabaseController
	XPath xpath;
	String xpathExpression;
	InputSource inputSource;
	NodeList firstRoot;
	NodeList firstChild;
	
	// -- updateRecord
	String recordCheck;
	String updateRecord;

	public DatabaseController() throws ClassNotFoundException, SQLException {
		xpath = XPathFactory.newInstance().newXPath();
		xpathExpression = "/settings";
		inputSource = new InputSource("src/assets/jdbcSettings.xml");
		stm = database.createStatement();
		try {

			firstRoot = (NodeList) xpath.compile(xpathExpression).evaluate(inputSource, XPathConstants.NODESET);
			firstChild = firstRoot.item(0).getChildNodes();

			// parse jdbcSettings.xml for config
			for (int i = 3; i < firstChild.getLength(); i++) {
				if (firstChild.item(i).getLocalName() != null) {

					switch (firstChild.item(i).getLocalName()) {
					case "user":
						user = firstChild.item(i).getTextContent();
						break;
					case "pass":
						pass = firstChild.item(i).getTextContent();
						break;
					case "url":
						url = firstChild.item(i).getTextContent();
						break;
					case "schema":
						schema = firstChild.item(i).getTextContent();
						url += schema;
						break;
					case "use_ssl":
						if (firstChild.item(i).getTextContent().compareTo("false") == 0) {
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
			// sql statement to create the table
			String createTable = "CREATE TABLE Players (" + "FirstName VARCHAR (20)     NOT NULL, "
					+ "LastName VARCHAR (20)     NOT NULL, " + "GroupNumber int, " + "Password VARCHAR (20), "
					+ "Perfered_Car_Name VARCHAR (20) , "
					+ "logo VARCHAR (20) , score int, logIn VARCHAR(20), Credits int);";
			stm.executeUpdate(createTable);
			// close the statement stream and the database connection
			stm.close();
			database.close();
			inputStream.close();
		}
	}

	public static void updateRecord(String firstName, String lastName, int groupNumber, String username, String password, String carName, String logo) throws SQLException {
		String recordCheck = ("SELECT * FROM Players WHERE FirstName = '" + firstName + "' AND LastName ='" + lastName + "' AND GroupNum ='" + groupNum + "'");
		ResultSet rs = stm.executeQuery(recordCheck);
		
		//if the record is not found and SQLException will be thrown and caught
		if(rs.next()) {
			//Now create an update statement to add all the other fields;
			String updateRecord = "UPDATE Players SET Username = '" + username + "', "
					+ "Password = '" + password +"', Car  = '" + carName + "', Logo = '"
							+ logo + "',  Score = 0,   Credits = 0 WHERE FirstName LIKE '" + firstName
							+"' AND LastName LIKE '" + lastName
							+ "' AND GroupNumber =" + groupNumber + ";" ; 
			stm.executeUpdate(updateRecord);
		}
	}

	public static Connection getDatabase() {
		return database;
	}

}