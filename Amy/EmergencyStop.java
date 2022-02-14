import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class EmergencyStop implements Behavior {
	
	private MovePilot pilot;
	private EV3TouchSensor ts;
	SampleProvider sp;
	private float[] touchSample = new float[1];
	
	EmergencyStop(MovePilot p, EV3TouchSensor sensor) {
		this.pilot = p;
		ts = sensor;
		sp = ts.getTouchMode();
	}
	
	public void action() {
		pilot.stop();
		while (true) {
			LCD.drawString("EMERGENCY STOP", 0, 5);
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		sp.fetchSample(touchSample, 0);
		return (Button.ESCAPE.isDown() || touchSample[0] == 1);
	}
	
}
