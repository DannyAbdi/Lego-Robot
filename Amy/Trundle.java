import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Trundle implements Behavior {
	
	boolean suppress = false;
	private MovePilot pilot;
	
	Trundle(MovePilot p) {
		this.pilot = p;
	}
	
	public boolean takeControl() {
		return true;
	}

	public void action() {
		if (!pilot.isMoving()) {
			pilot.forward();
		}
	}

	public void suppress() {

	}
}