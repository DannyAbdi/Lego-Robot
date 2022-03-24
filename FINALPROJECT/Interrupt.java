import lejos.hardware.Button;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class Interrupt implements Behavior {
	
	private Navigator navigator;
	
	Interrupt(Navigator navigator) {
		this.navigator = navigator;
	}
	
	public void action() {
		navigator.stop();
		System.exit(0);
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (Button.ESCAPE.isDown());
	}
}
