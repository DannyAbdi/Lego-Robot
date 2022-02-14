import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Dark implements Behavior {
	
	private EV3ColorSensor cs;
	private MovePilot pilot;
	private SampleProvider light;
	float[] lightLevel = new float[1];

	
	final static float FAST = 200f;
	final static float SLOW = 100f;
	
	public Dark(MovePilot pilot, EV3ColorSensor cs) {
		
		this.pilot = pilot;
		this.cs = cs;
		light = cs.getRedMode();
	}
	
	public boolean takeControl() {
		
		light.fetchSample(lightLevel, 0);
		float currentLight = lightLevel[0];
		
		if (currentLight > 0.5f) {
			cs.close();
			return true;
		}
		cs.close();
		return false;	
	}
	
	public void action() {
		
		if (takeControl()) {
			pilot.setLinearSpeed(SLOW);
		} else {
			pilot.setLinearSpeed(FAST);
		}
	}

	public void suppress() {
	
	}
}