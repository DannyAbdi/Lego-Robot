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

public class PilotSquare {
	
	final static float WHEEL_DIAMETER = 43; 
	final static float AXLE_LENGTH = 114; 
	final static float ANGULAR_SPEED = 100; 
	final static float LINEAR_SPEED = 200; 

	public static void main(String [] args) {

		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.D);
		Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Wheel[] wheels = new Wheel[] {wR, wL};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		LCD.drawString(poseProvider.getPose().toString(),0,0);
		
		pilot.setAngularSpeed(ANGULAR_SPEED);
		pilot.setLinearSpeed(LINEAR_SPEED);
		
		for (int side = 0 ; side < 4 ; side++) {
			pilot.travel(1000);
			pilot.rotate(90);
		}
		
		LCD.drawString(poseProvider.getPose().toString(),0,0);

	}
}
