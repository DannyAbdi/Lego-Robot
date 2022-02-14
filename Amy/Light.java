import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Light implements Behavior {
	
	private MovePilot pilot;
	private EV3ColorSensor cSensor;
	Calibrate cnew;
	
	Light(MovePilot p, EV3ColorSensor sensor) {
		this.pilot = p;
		cSensor = sensor;
		cnew = new Calibrate(cSensor);
	}
	
	public void action() {
		pilot.setLinearSpeed(200);
		pilot.forward();
	}
	
	public void suppress() {
	}
	
	public boolean takeControl() {
		return (cnew.fetchSample() > 0.7 && pilot.getLinearSpeed() <= 100);
	}
	
}
