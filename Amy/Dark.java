import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Dark implements Behavior {
	
	private MovePilot pilot;
	private EV3ColorSensor cSensor;
	Calibrate newc;

	
	Dark(MovePilot p, EV3ColorSensor sensor) {
		this.pilot = p;
		cSensor = sensor;
		newc = new Calibrate(cSensor);
	}
	
	public void action() {
		pilot.setLinearSpeed(50);
		pilot.forward();
	}
	
	public void suppress() {
	}
	
	public boolean takeControl() {
		return (newc.fetchSample() < 0.02 && pilot.getLinearSpeed() >= 100);
	}
	
}
