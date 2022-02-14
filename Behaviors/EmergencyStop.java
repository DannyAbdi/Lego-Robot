import lejos.hardware.Button;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStop implements Behavior {
	private MovePilot pilot;
	private EV3TouchSensor ts;
	private SampleProvider touch;
	float[] touches = new float[1];
	float isTouched = touches[0];
	
	public EmergencyStop(MovePilot pilot, EV3TouchSensor ts) {
		this.pilot = pilot;
		this.ts = ts;
		touch = ts.getTouchMode();
	}
	
	public void action() {
		pilot.stop();
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		touch.fetchSample(touches, 0);
		
		if (isTouched == 1) {
			ts.close();
			return true;
		}
		
		Button.ESCAPE.waitForPressAndRelease();
		
		return true;
	}
}
