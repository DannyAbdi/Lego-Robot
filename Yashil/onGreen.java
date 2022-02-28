import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class onGreen implements Behavior {
	EV3ColorSensor cs; 
	float[] colour = new float[3];
	
	MovePilot pilot;
	
	public onGreen (EV3ColorSensor cs, MovePilot pilot) {
		this.cs = cs;
		this.pilot = pilot;
	}
	
	public void action() {
		pilot.setAngularSpeed(100);
		pilot.setLinearSpeed(150);
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		cs.fetchSample(colour, 0);
		return colour[1] == 1;
	}
}