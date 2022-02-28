import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class onBlue implements Behavior {
	EV3ColorSensor cs; 
	float[] colour = new float[3];
	
	MovePilot pilot;
	
	public onBlue (EV3ColorSensor cs, MovePilot pilot) {
		this.cs = cs;
		this.pilot = pilot;
	}
	
	public void action() {
		pilot.setAngularSpeed(200);
		pilot.setLinearSpeed(300);
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		cs.fetchSample(colour, 0);
		return colour[2] == 1;
	}
}
