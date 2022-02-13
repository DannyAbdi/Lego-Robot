import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class SquareCar {

	public static void main(String[] args) {
		
		final int SPEED = 720;
		final int FOUR = 4;
		final int ONE_SECOND = 1000;
		final int NINETY_DEG = 260;
		final int STOP_DELAY = 100;
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.D);
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		
		
		for (int i = 0; i < FOUR; i++) {
			mLeft.startSynchronization();
			mLeft.forward();
			mRight.forward();
			mLeft.endSynchronization();
			
			Delay.msDelay(ONE_SECOND);
			
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			
			Delay.msDelay(STOP_DELAY);
			
			mLeft.startSynchronization();
			mLeft.forward();
			mRight.backward();
			mLeft.endSynchronization();
			
			Delay.msDelay(NINETY_DEG);
			
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
		}
		
		
		mLeft.close();
		mRight.close();

	}

}
