import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Distance {

	public static void main(String[] args) {

		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider sp = us.getDistanceMode();
		
		float[] distance = new float[1];
		float FIFTY_CM = 0.5f;
		int SPEED = 200;
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		Boolean isFifty = false;
		
		while(isFifty == false) {
			
			sp.fetchSample(distance, 0);
			float currentDistance = distance[0];

			if(currentDistance > FIFTY_CM) {
				mLeft.startSynchronization();
				mLeft.forward();
				mRight.forward();
				mLeft.endSynchronization();
			} else {
				isFifty = true;
			}
		}
		
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
		
		us.close();
		mLeft.close();
		mRight.close();
	}
}