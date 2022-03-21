import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class OnYellow implements Behavior {
	private EV3ColorSensor cs; 
	private float[] colour = new float[1];
	private SampleProvider sp;
	private MovePilot pilot;
	private Navigator navigator;
	
	private int linearSpeed = 150;
	private int angularSpeed = 150;
	
	public OnYellow (EV3ColorSensor cs, MovePilot pilot, Navigator navigator) {
		this.navigator = navigator;
		this.cs = cs;
		this.pilot = pilot;
		sp = this.cs.getColorIDMode();
	}
	
	public void action() {
		pilot.setLinearSpeed(linearSpeed);
		pilot.setAngularSpeed(linearSpeed);
		navigator.stop();
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		sp.fetchSample(colour, 0);
		return (colour[0] == Color.YELLOW) && (pilot.getLinearSpeed() < linearSpeed);
	}
}
