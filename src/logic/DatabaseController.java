package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class DatabaseController {

	final static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static Connection database;
	private static Statement stm;
	private static String url = null;
	private static String user = null;
	private static String pass = null;

	public static void parseXML() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		XPath xpath;
		String xpathExpression;
		InputSource inputSource;
		NodeList firstRoot;
		NodeList firstChild;

		xpath = XPathFactory.newInstance().newXPath();
		xpathExpression = "/settings";
		inputSource = new InputSource("src/assets/jdbcSettings.xml");
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
						case "use_ssl":
							if (firstChild.item(i).getTextContent().compareTo("false") == 0) {
								url += "?useSSL=false";
							}
					}
				}
			}
			// connect driver
			Class.forName(jdbcDriver);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

	}

	public static void createdb() throws SQLException {
		database = DriverManager.getConnection(url, user, pass);
		try {
			database.setCatalog("students");
		} catch (SQLException e) {
			// Create database
			stm = database.createStatement();
			stm.executeUpdate("CREATE DATABASE students;");
			stm.close();
			database.close();
		}
	}

	public static void createTable() throws SQLException, FileNotFoundException {
		Scanner inputStream;
		ArrayList<String> lastName = new ArrayList<>();
		ArrayList<String> firstName = new ArrayList<>();
		ArrayList<Integer> groupNum = new ArrayList<>();
		database = DriverManager.getConnection(url, user, pass);
		database.setCatalog("students");
		inputStream = new Scanner(new File("src/logic/Prog32758Students.txt"));
		inputStream.useDelimiter(",");
		while (inputStream.hasNext()) {
			lastName.add(inputStream.next());
			firstName.add(inputStream.next());
			groupNum.add(new Integer(inputStream.nextLine().substring(1)));
		}
		// Since database created successfully, program creates a table.
		// sql statement to create the table
		stm = database.createStatement();
		stm.executeUpdate("CREATE TABLE players (FirstName VARCHAR (20) NOT NULL, LastName VARCHAR (20) NOT NULL, "
				+ "GroupNumber int, PerferedCarName VARCHAR (20), Logo VARCHAR (20), Score int, Username VARCHAR(20), "
				+ "Pass VARCHAR (20), Credits int);");
		// use for loop to insert all student records
		for (int i = 0; i < lastName.size(); i++) {
			stm.executeUpdate("INSERT INTO players (FirstName, LastName, GroupNumber) Values ('" + firstName.get(i)
					+ "', '" + lastName.get(i) + "', " + groupNum.get(i) + ");");
		}
		stm.close();
		// close the statement stream and the database connection
		stm.close();
		database.close();
		inputStream.close();
	}

	public static boolean validateRecord(String username, String password) throws SQLException {
		String recordCheck = ("SELECT * FROM players WHERE Username = '" + username + "' AND Password ='" + password
				+ "';");
		ResultSet rs = stm.executeQuery(recordCheck);
		// if the record is not found and SQLException will be thrown and caught
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validateRecord(String firstName, String lastName, int groupNumber, String username,
			String password, String carName, String logo) throws SQLException {
		String recordCheck = ("SELECT * FROM players WHERE FirstName = '" + firstName + "' AND LastName ='" + lastName
				+ "' AND GroupNum ='" + groupNumber + "'");
		ResultSet rs = stm.executeQuery(recordCheck);
		// if the record is not found and SQLException will be thrown and caught
		if (rs.next()) {
			// Now create an update statement to add all the other fields;
			String updateRecord = "UPDATE players SET Username = '" + username + "', " + "Password = '" + password
					+ "', Car  = '" + carName + "', Logo = '" + logo
					+ "',  Score = 0,   Credits = 0 WHERE FirstName LIKE '" + firstName + "' AND LastName LIKE '"
					+ lastName + "' AND GroupNumber =" + groupNumber + ";";
			stm.executeUpdate(updateRecord);
			return true;
		} else {
			return false;
		}
	}

	public static String getUrl() {
		return url;
	}

	public static String getUser() {
		return user;
	}
}