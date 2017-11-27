package logic;

import javafx.scene.control.TextArea;

public class UseMyCar {

	// final static Random gasInject = new Random();
	static int gas = 0;
	static int moreGas = 0;

	// Method that generates a random amount of gas to be used for acceleration
	// (between minx10 and maxx10)
	static int createInjection(int min, int max) {
		int myInjection = (int) (Math.random() * 10 * (Math.random() > 0.5 ? max : min));
		return myInjection;
	}

	public static void startGame(Car playerCar, TextArea gameScreen) {

		// Create a car named specified just by its name : �Toyota�.
		// Car playerCar = new Car("YourPreferedCar");

		// Create a car with a specified name: �Mazda� to be driven by a
		// non-experienced person.
		// This Car will start the race with an initial speed given by the player as a
		// parameter to the constructor
		// Let's take 20.
		Car mySecondCar = new Car("ComputerCar");

		// Create the first Driver that will drive the first car
		Driver firstDriver = new Driver();

		// Create the second Driver that will drive the second car
		Driver secondDriver = new Driver();

		String theWinner = "";

		gameScreen.setText(gameScreen.getText() + "/n");
		gameScreen.setText(gameScreen.getText() + "\t\t\t\t" + "CAR RACING");
		gameScreen.setText(gameScreen.getText() + "\t\t\t\t" + "----------");
		gameScreen.setText(gameScreen.getText() + "/n");
		gameScreen.setText(gameScreen.getText() + "/n");

		// Cars start
		playerCar.start(1, 0);
		mySecondCar.start(1, 0);

		// Cars start running. From here they all get the current speed
		playerCar.StartRunning();
		playerCar.setSpeedIncreaseStep(playerCar.getCurrentSpeed());

		mySecondCar.StartRunning();
		mySecondCar.setSpeedIncreaseStep(mySecondCar.getCurrentSpeed());

		// Let's race for 30 seconds
		int i = 1;
		while (i < 60) {
			// if playerCar speedIncreaseStep is positive (Acceleration)
			if (playerCar.getSpeedIncreaseStep() > 0) {
				gameScreen.appendText(playerCar.getSpeedIncreaseStep() + " :: " + playerCar.getCarName() + " >>> : "
						+ playerCar.getCurrentSpeed() + "\t\t");
			}
			// if playerCar current speedIncreaseStep is negative (Deceleration - Slow down)
			else if (playerCar.getSpeedIncreaseStep() < 0) {
				gameScreen.appendText(playerCar.getSpeedIncreaseStep() + " :: " + playerCar.getCarName() + " <<< : "
						+ playerCar.getCurrentSpeed() + "\t\t");
			}
			// if playerCar speedIncreaseStep speed is zero (Constant speed)
			else {
				// increment the car zeroCounter counter
				playerCar.setZeroCounter(playerCar.getZeroCounter() + 1);
				// prepare gas
				gas = createInjection(2, 0);
				// Increase the speed related to the amount of gas alloted
				playerCar.setSpeedIncreaseStep(gas);
				// Get this speed when the driver punches on the accelerator pedal
				firstDriver.punchOnAccelorPedal(playerCar, playerCar.getSpeedIncreaseStep());
				gameScreen.appendText(playerCar.getSpeedIncreaseStep() + " :: " + playerCar.getCarName() + " +++ : "
						+ playerCar.getCurrentSpeed() + "\t\t");
				// if the number of times zeroCounter has passed to zero is multiple of 3
				if (playerCar.getZeroCounter() % 3 == 0) {
					// prepare even more gas
					moreGas = createInjection(playerCar.getZeroCounter(), 0);
					// Automatically increase the speed related to the amount of gas alloted
					playerCar.automaticAccelerationIncrease(gas);
					gameScreen.appendText(playerCar.getSpeedIncreaseStep() + " :: " + playerCar.getCarName() + " *** : "
							+ playerCar.getCurrentSpeed() + "\t\t");
				}
			}
			// if mySecondCar speedIncreaseStep is positive (Acceleration)
			if (mySecondCar.getSpeedIncreaseStep() > 0) {
				gameScreen.appendText(mySecondCar.getSpeedIncreaseStep() + " :: " + mySecondCar.getCarName() + " >>> : "
						+ mySecondCar.getCurrentSpeed());
				// if mySecondCar current speedIncreaseStep is negative (Deceleration - Slow
				// down)
			} else if (mySecondCar.getSpeedIncreaseStep() < 0) {
				gameScreen.appendText(mySecondCar.getSpeedIncreaseStep() + " :: " + mySecondCar.getCarName() + " <<< : "
						+ mySecondCar.getCurrentSpeed());
			}
			// if mySecondCar speedIncreaseStep speed is zero (Constant speed)
			else {
				// increment the car zeroCounter counter
				mySecondCar.setZeroCounter(mySecondCar.getZeroCounter() + 1);
				// increment the car zeroCounter counter
				gas = createInjection(1, 0);
				// Increase the speed related to the amount of gas alloted
				mySecondCar.setSpeedIncreaseStep(gas);
				// Get this speed when the driver punches on the accelerator pedal
				secondDriver.punchOnAccelorPedal(mySecondCar, mySecondCar.getSpeedIncreaseStep());
				gameScreen.appendText(mySecondCar.getSpeedIncreaseStep() + " :: " + mySecondCar.getCarName() + " +++ : "
						+ mySecondCar.getCurrentSpeed() + "\t\t");
				// if the number of times zeroCounter has passed to zero is multiple of 3
				if (mySecondCar.getZeroCounter() % 3 == 0) {
					// prepare even more gas
					moreGas = createInjection(mySecondCar.getZeroCounter(), 0);
					// Automatically increase the speed related to the amount of gas alloted
					mySecondCar.automaticAccelerationIncrease(gas);
					gameScreen.setText(gameScreen.getText() + mySecondCar.getSpeedIncreaseStep() + " :: "
							+ mySecondCar.getCarName() + " *** : " + mySecondCar.getCurrentSpeed() + "\t\t");
				}
			}

			// Keep running both the cars
			playerCar.run(2, -1);
			mySecondCar.run(2, -1);
			// Pass the seconds between two sppeeds
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				gameScreen.setText(gameScreen.getText() + "there is an error");
			}
			i++;
		}
		gameScreen.setText(gameScreen.getText() + "/n");
		gameScreen.setText(gameScreen.getText() + "/n");

		if (playerCar.getCurrentSpeed() == mySecondCar.getCurrentSpeed()) {
			gameScreen.appendText("\t\t" + "There is no winner for this Race ");
		} else if (playerCar.getCurrentSpeed() > mySecondCar.getCurrentSpeed()) {
			theWinner = playerCar.getCarName();
			gameScreen.appendText("\t\t" + "The winner of this Race is :  " + theWinner);
		} else {
			theWinner = mySecondCar.getCarName();
			gameScreen.appendText("\t\t" + "The winner of this Race is :  " + theWinner);
		}

		gameScreen.appendText("/n");
		gameScreen.appendText("/n");
	}
}