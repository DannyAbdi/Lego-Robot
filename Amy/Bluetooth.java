import java.io.BufferedInputStream;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;

public class Bluetooth implements Behavior {
	
	private BufferedInputStream in = null;
	private static int MAX_READ = 30;
	byte[] buffer = new byte[MAX_READ];
	
	public Bluetooth(BufferedInputStream in) {
		this.in = in;
	}
	
	public void action() {
		int read;
		try {
			read = in.read(buffer, 0, MAX_READ);
			for (int index= 0 ; index < read ; index++) {		
				LCD.drawChar((char)buffer[index], index + 4, 3);
			}
		} catch (IOException e) {
			LCD.drawString("Something went wrong", 0, 3);
		}
	}
	
	public void suppress() {

	}
	
	public boolean takeControl() {
		try {
			return (in.available() > 0);
		} catch (IOException e) {
			LCD.drawString("Something went wrong in BT", 0, 0);
			return false;
		}
	}
	
}
