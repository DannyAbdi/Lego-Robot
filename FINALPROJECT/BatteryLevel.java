import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BatteryLevel implements Behavior {
	
	private int lowBatteryPercent = 5;
	
	public BatteryLevel() {
	}
	
	public void action() {
		while (true) {
			LCD.drawString("BATTERY LOW", 0, 4);
			Sound.beep();
			Delay.msDelay(500);
		}
	}
	
	public void suppress() {

	}
	
	public boolean takeControl() {
		return (Battery.getVoltage() < lowBatteryPercent);
	}	
}
