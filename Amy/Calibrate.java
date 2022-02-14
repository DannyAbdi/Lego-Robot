import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Calibrate implements Behavior {
	
	private boolean isNotCalibrated = true;
	private EV3ColorSensor cSensor;
	SampleProvider sp;
	private float range = 1.0f;
	private float offset = 0.0f;
	boolean suppress = false;
	float colorSamples[] = new float[1];
	
	Calibrate(EV3ColorSensor sensor) {
		cSensor = sensor;
		sp = cSensor.getAmbientMode();
	}

	public void action() {
		float highValue = 0.0f;
		float lowValue = 1.0f;
		LCD.drawString("Move sensor around to calibrate", 0, 2);
		LCD.drawString("Press enter when done", 0, 3);
		while (!Button.ENTER.isDown() && !suppress) {
			sp.fetchSample(colorSamples, 0); //Get samples
			highValue = Math.max(highValue, colorSamples[0]); //Assign the higher of two values
			lowValue = Math.min(lowValue, colorSamples[0]); //Assign the lower of two values
		}
		LCD.clearDisplay();
		offset = lowValue;
		range = highValue - lowValue; //Store range and offset for future use
		
		isNotCalibrated = false;
	}
	
	public void suppress() {
		suppress = true;
	}
	
	public boolean takeControl() {
		return isNotCalibrated;
	}
	
	public float fetchSample() {
		sp.fetchSample(colorSamples, 0);
		return (colorSamples[0] - offset) / range;
		
	}
}
