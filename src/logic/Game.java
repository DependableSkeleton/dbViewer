package logic;

import javafx.scene.control.TextField;

public class Game {
	
		// The purpose of this is to update your credits on the database
		public static void refillCredit(TextField txf) {
			System.out.println("Update database credits by" + txf.getText());
		}

//		public static void musicControl(Boolean play) {
//
//			if (play == true) {
//				AudioPlayer audioPlayer = AudioPlayer.player;
//
//				try {
//					System.out.println("Music is on");
//					// File musicFile = new
//					// File("file:///Users/elbanby/Documents/microProjectv.1/msuic.wav");
//					// AudioInputStream ais = AudioSystem.getAudioInputStream(musicFile);
//					// AudioFormat format = ais.getFormat();
//					// DataLine.Info info = new DataLine.Info(Clip.class, format);
//					//
//					// Clip clip = (Clip) AudioSystem.getLine(info);
//					// clip.open(ais);
//					// clip.start();
//
//				} catch (Exception exp) {
//					exp.printStackTrace();
//					;
//				}
//
//			} else {
//				System.out.print("MUSIC off");
//			}
//		}

}
