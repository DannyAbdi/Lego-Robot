import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.PathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.utility.Delay;

public class FollowThePath {
	
	final static float WHEEL_DIAMETER = 43;
	final static float AXLE_LENGTH = 120;

	public static void main(String[] args) {
		//Create left motor and wheel
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.D);
		Wheel wLeft = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH/2);
						
		//Create right motor and wheel
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wRight = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH/2);
						
		//Create a chassis with two wheels
		Chassis chassis = new WheeledChassis((new Wheel[] {wRight, wLeft}), WheeledChassis.TYPE_DIFFERENTIAL);
						
		//Create a pilot which can drive the chassis
		MovePilot pilot = new MovePilot(chassis);
						
		//Create a PoseProvider
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
				
		//Create a Navigator
		Navigator navigator = new Navigator(pilot, poseProvider);
		
		//Create lines
		Line[] lines = new Line[8];
		lines[0] = new Line(250, 300, 250, 800); //Book left
		lines[1] = new Line(220, 330, 620, 330); //Book bottom
		lines[2] = new Line(600, 330, 600, 800); //Book right
		lines[3] = new Line(220, 770, 620, 770); //Book top
		lines[4] = new Line(420, 200, 420, 420); //Coffee left
		lines[5] = new Line(500, 230, 720, 200); //Coffee bottom
		lines[6] = new Line(700, 230, 700, 420); //Coffee right
		lines[7] = new Line(500, 400, 720, 400); //Coffee top
		
		//Create the obstacles and boundary
		Rectangle bounds = new Rectangle(0, 0, 1000, 1000); //Boundary
		LineMap myMap = new LineMap(lines, bounds); //Create LineMap object
		
		PathFinder pf = new ShortestPathFinder(myMap); //Create PathFinder
		Path route = null; //Initialise root
		try {
			route = pf.findRoute(new Pose(0, 0, 90), new Waypoint(800,700)); //Create the route, the robots pose and destination waypoint
		} catch (DestinationUnreachableException e) { //Catch error
			System.exit(0); //Exit path if no path is found
		}
		navigator.followPath(route);
		navigator.waitForStop();

		LCD.drawString(poseProvider.getPose().toString(), 0, 5);
		Delay.msDelay(2000);

	}

}
