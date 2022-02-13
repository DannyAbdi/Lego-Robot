import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class NavigatorSquare {
		
	final static float WHEEL_DIAMETER = 43; 
	final static float AXLE_LENGTH = 120; 

	public static void main(String [] args) {

		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.D);
		Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Wheel[] wheels = new Wheel[] {wR, wL};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		Navigator navigator = new Navigator(pilot, poseProvider);
		
		Path squarePath = new Path();
		squarePath.add(new Waypoint(100,0));
		squarePath.add(new Waypoint(100,100));
		squarePath.add(new Waypoint(0,100));
		squarePath.add(new Waypoint(0,0));
		
		navigator.setPath(squarePath);
		navigator.followPath();
		
		navigator.waitForStop();
		LCD.drawString(poseProvider.getPose().toString(),0,0);
		
	}
}
