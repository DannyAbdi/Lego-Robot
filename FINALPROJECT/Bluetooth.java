import java.io.BufferedInputStream;
import java.io.IOException;
import lejos.hardware.lcd.LCD;

public class Bluetooth {

	private BufferedInputStream in = null;
	private static int MAX_READ = 30;
	private byte[] buffer = new byte[MAX_READ];
	
	public Bluetooth(BufferedInputStream in) {
		this.in = in;
	}
	
	public BufferedInputStream getIn() {
		return in;
	}
	
	public String getMessage() {
		String s = "";
		try {
			if (in.available() > 0) {
				int read;
				try {
					read = in.read(buffer, 0, MAX_READ);
					for (int index= 0 ; index < read ; index++) {		
						s+= (char) buffer[index];
					}
				} catch (IOException e) {
					LCD.drawString("BT went wrong", 0, 3);
				}
			}
		} catch (IOException e) {
			LCD.drawString("BT went wrong", 0, 3);
		}
		return s;
	}
}