import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class GoCar {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.D);
		
		mLeft.setSpeed(720);
		mRight.setSpeed(720);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		
		Delay.msDelay(1000);

		mLeft.startSynchronization();
		mLeft.stop();
		Delay.msDelay(100);
		mRight.stop();
		mLeft.endSynchronization();
		
		mLeft.close();
		mRight.close();

	}

}
