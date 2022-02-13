import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	
	boolean suppress = false;
	private MovePilot pilot;
	
	public Trundle(MovePilot p) {
		this.pilot = p;
	}
	
	public boolean takeControl() {
		return true;
	}

	public void action() {
		
		while (!suppress) {
			pilot.forward();
		}
		
		pilot.stop();
	}

	public void suppress() {
		suppress = true;
	}
}
