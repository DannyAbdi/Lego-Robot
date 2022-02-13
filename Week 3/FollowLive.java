import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class FollowLine {

	public static void main(String[] args) {
		float[] level = new float[1];
		EV3ColorSensor red = new EV3ColorSensor(SensorPort.S1);
		red.setFloodlight(true);
		float LIGHT_AVERAGE = 0.6f; //CALIBRATED FOR CARDBOARD + WHITE PAPER
		int SPEED = 200;
		
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		SampleProvider light = red.getRedMode();
		
		while (Button.ENTER.isUp()) {
			light.fetchSample(level, 0);
			float currentLight = level[0];
			
			if (currentLight > LIGHT_AVERAGE) {
				mLeft.forward();
				mRight.stop();
			} else {
				mRight.forward();
				mLeft.stop();
			}
			
		}
		
		red.close();
		mLeft.close();
		mRight.close();

	}

}
