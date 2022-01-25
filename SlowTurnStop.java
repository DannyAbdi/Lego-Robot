import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class SlowTurnStop {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		mLeft.setSpeed(400);
		mLeft.forward();
		

		while (mLeft.isMoving() == true) {
			String s = String.valueOf(mLeft.getSpeed());
			LCD.drawString(s, 2, 2);
			
		}
		
			Sound.beepSequenceUp();
		
		mLeft.close();
	}

}
