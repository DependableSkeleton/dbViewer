package logic;

public class GameThread extends Thread{
	
	public static void run(Car playerCar){
		UseMyCar.startGame(playerCar);
	}
}
