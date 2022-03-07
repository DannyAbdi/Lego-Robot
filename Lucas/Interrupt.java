import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Interrupt implements Behavior {
	
	private MovePilot pilot;
	private EV3TouchSensor ts;
	SampleProvider sp;
	private float[] touchSample = new float[1];
	
	Interrupt(MaovePilot p, EV3TouchSensor sensor) {
		this.pilot = p;
		ts = sensor;
		sp = ts.getTouchMode();
	}
	
	public void action() {
		pilot.stop();
		System.exit(0);
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		sp.fetchSample(touchSample, 0);
		return (touchSample[0] == 1);
	}
	
}
