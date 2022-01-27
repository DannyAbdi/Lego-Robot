import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class SimpleChap {

	public static void main(String[] args) {
		
		int SPEED = 200;
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		float[] level = new float[1]; // A sound sample is just one number
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		SensorMode sound = (SensorMode) ss.getDBAMode();
		SampleProvider clap = new ClapFilter(sound, 0.6f, 100);

		clap.fetchSample(level, 0);
		float hasClap = level[0];

		while (hasClap == 0) {
			clap.fetchSample(level, 0);
			hasClap = level[0];
		}
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		
		clap.fetchSample(level, 0);
		hasClap = level[0];
		
		while (hasClap == 0) {
			clap.fetchSample(level, 0);
			hasClap = level[0];

		}
		
		mLeft.stop();
		mRight.stop();
		mRight.rotate(540);
		
		
		
		mLeft.close();
		mRight.close();
		ss.close();
	}
}
	
