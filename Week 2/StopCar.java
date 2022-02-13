import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class StopCar {

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
		
		Button.ENTER.waitForPressAndRelease();

		mLeft.startSynchronization();
		mLeft.stop();
		Delay.msDelay(100);
		mRight.stop();
		mLeft.endSynchronization();
		
		
		LCD.drawInt(mLeft.getTachoCount(),0,0);
		Button.ENTER.waitForPressAndRelease();
		
		mLeft.close();
		mRight.close();
		

	}

}
