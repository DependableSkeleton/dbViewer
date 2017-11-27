package logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	
	private SimpleStringProperty firstName = new SimpleStringProperty();
	private SimpleStringProperty lastName = new SimpleStringProperty();
	private SimpleIntegerProperty groupNum = new SimpleIntegerProperty();
	private SimpleStringProperty carName = new SimpleStringProperty();
	private SimpleStringProperty logo = new SimpleStringProperty();
	private SimpleIntegerProperty score = new SimpleIntegerProperty();
	private SimpleStringProperty username = new SimpleStringProperty();
	private SimpleIntegerProperty credits = new SimpleIntegerProperty();
	
	protected Player() {
		
	}
	
	public SimpleStringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public StringProperty firstNameProperty() { 
        if (firstName == null) firstName = new SimpleStringProperty(this, "firstName");
        return firstName; 
    }

	public SimpleStringProperty getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public StringProperty lastNameProperty() { 
        if (lastName == null) lastName = new SimpleStringProperty(this, "lastName");
        return lastName; 
    } 

	public SimpleIntegerProperty getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum.set(groupNum);
	}
	
	public IntegerProperty groupNumProperty() { 
        if (groupNum == null) groupNum = new SimpleIntegerProperty(this, "groupNum");
        return groupNum; 
    }

	public SimpleStringProperty getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName.set(carName);
	}
	
	public StringProperty carNameProperty() { 
		if (carName == null) carName = new SimpleStringProperty(this, "carName");
        	return carName; 
    }

	public SimpleStringProperty getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo.set(logo);
	}
	
	public StringProperty logoProperty() { 
		if (logo == null) logo = new SimpleStringProperty(this, "logo");
        	return logo; 
    }

	public SimpleIntegerProperty getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score.set(score);
	}
	
	public IntegerProperty scoreProperty() { 
        if (score == null) score = new SimpleIntegerProperty(this, "score");
        return score; 
    }

	public SimpleStringProperty getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public StringProperty usernameProperty() { 
        if (username == null) username = new SimpleStringProperty(this, "username");
        return username; 
    }

	public SimpleIntegerProperty getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits.set(credits);
	}
	
	public IntegerProperty creditsProperty() { 
        if (credits == null) credits = new SimpleIntegerProperty(this, "credits");
        return credits; 
    }
}
