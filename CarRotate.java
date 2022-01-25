import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class CarRotate {
	
	final static int NINETY_ROTATE = 560;
	final static int L_MOTOR_SPEED = 720;
	final static int R_MOTOR_SPEED = 720;

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		// Tell JVM what the left motor is synchronized with.
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.setSpeed(L_MOTOR_SPEED);// 2 Revolutions Per Second (RPS)
		mRight.setSpeed(R_MOTOR_SPEED);
		// Do a for loop here to run FOUR times
		for (int i = 0; i < 4; i++) {
		mLeft.rotate(NINETY_ROTATE); // one motor turns to go around a corner
		// start a synchronized edge of the square
		mLeft.startSynchronization();
		// these rotates will not begin until we end the synchronization
		// Both rotate calls must immediately return,
		// ... rather than finishing their rotation before returnqing.
		mLeft.rotate(NINETY_ROTATE, true);
		mRight.rotate(NINETY_ROTATE, true);
		// actually begin both motors rotating at exactly the same time
		mLeft.endSynchronization();
		mLeft.waitComplete();
		mRight.waitComplete(); // wait for both motors to finish turning
		}
		mLeft.close();
		mRight.close();

	}

}
