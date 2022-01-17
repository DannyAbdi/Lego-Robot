import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class HelloWorld {

	public static void main(String[] args) {
		Button.LEDPattern(4); // Start the LEDs flashing Green
		Sound.beepSequenceUp();
		LCD.clear();
		LCD.drawString("HOLY", 2, 2);
		Delay.msDelay(1000);
		LCD.drawString("SMOKE",2,3);


	}

}
