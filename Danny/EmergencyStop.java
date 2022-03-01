import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Sound;

public class EmergencyStop implements Behavior {
	
	private MovePilot pilot;
	float[] distance = new float[1];
	float TEN_CM = 0.1f;
	boolean isTen = false;
	SampleProvider sp;
	
	EmergencyStop(MovePilot p, EV3UltrasonicSensor us) {
		this.pilot = p;
		sp = us.getDistanceMode();
	}
	
	public void action() {
		Sound.beep();
		pilot.stop();
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		
		while (!isTen) {
			sp.fetchSample(distance, 0);
			float currentDistance = distance[0];
			
			return (currentDistance <= TEN_CM);
		}
		return false;
	}
}