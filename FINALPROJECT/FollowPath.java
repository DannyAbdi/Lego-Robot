import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.subsumption.Behavior;

public class FollowPath implements Behavior {
	
	private Navigator navigator;
	private Path squarePath;
	
	public FollowPath(Navigator navigator) {
		this.navigator = navigator;
		this.squarePath = getPath();
	}
	
	private static Path getPath() {
		Path squarePath = new Path();
		squarePath.add(new Waypoint(750, 0));
		squarePath.add(new Waypoint(750, 750));
		squarePath.add(new Waypoint(600, 750));
		squarePath.add(new Waypoint(600, 150));
		squarePath.add(new Waypoint(0, 150));
		squarePath.add(new Waypoint(0,0));
		return squarePath;
		}
	
	public void action() {
		if (navigator.pathCompleted()) {
			squarePath = getPath();
			navigator.followPath(squarePath);
		} else if (!navigator.isMoving()) {
			navigator.followPath(squarePath);
		}
	}
	
	public void suppress() {
	}
	
	public boolean takeControl() {
		return true;
	}
}
