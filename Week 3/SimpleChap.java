import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SimpleChap {

	public static void main(String[] args) {
		
		//Distance sensors
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
		SampleProvider sp = us.getDistanceMode();
		
		
		//Variables
		int SPEED = 200;
		float[] distance = new float[1];
		float FIFTY_CM = 0.5f;
		boolean isFifty = false;
		
		//Motors
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		//Sound sensor
		float[] level = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		SensorMode sound = (SensorMode) ss.getDBAMode();
		SampleProvider clap = new ClapFilter(sound, 0.6f, 100);

		clap.fetchSample(level, 0);
		float hasClap = level[0];

		while (hasClap == 0) { //While it's quiet
			clap.fetchSample(level, 0);
			hasClap = level[0];
		}
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight}); //After a clap, run this
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		
		clap.fetchSample(level, 0);
		hasClap = level[0];
		
		while (hasClap == 0) { //Wait for next clap
			clap.fetchSample(level, 0);
			hasClap = level[0];

		}
		
		mLeft.stop(); //Stop and rotate robot
		mRight.stop();
		mRight.rotate(540);
		
		while(isFifty == false) { //While it's more than 50cm away from the wall
			
			sp.fetchSample(distance, 0);
			float currentDistance = distance[0];

			if(currentDistance > FIFTY_CM) { //Move forward when it's more than 50cm away from the wall
				mLeft.startSynchronization();
				mLeft.forward();
				mRight.forward();
				mLeft.endSynchronization();
			} else { //When 50cm is reached, break loop
				isFifty = true;
			}
		}
		
		mLeft.startSynchronization(); //Stops motors
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
		
		mLeft.close();
		mRight.close();
		ss.close();
		us.close();
	}
}
