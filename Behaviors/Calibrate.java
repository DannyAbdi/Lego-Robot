import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class Calibrate implements Behavior {
	
	private boolean isCalibrated = false;
	private EV3ColorSensor sensor;
	private SampleProvider sp = sensor.getAmbientMode();
	private float range = 1.0f;
	private float offset = 0.0f;
	boolean suppress = false;
	
	public Calibrate(EV3ColorSensor sensor) {
		this.sensor = sensor;
	}

	public void action() {
		float colorSamples[] = new float[1];
		float highValue = 0.0f;
		float lowValue = 1.0f;
		LCD.drawString("Move sensor around to calibrate", 0, 0);
		LCD.drawString("Press enter when done", 0, 1);
		while (!Button.ENTER.isDown() && !suppress) {
			sp.fetchSample(colorSamples, 0); //Get samples
			highValue = Math.max(highValue, colorSamples[0]); //Assign the higher of two values
			lowValue = Math.min(lowValue, colorSamples[0]); //Assign the lower of two values
		}
		LCD.clearDisplay();
		offset = lowValue;
		range = highValue - lowValue; //Store range and offset for future use
		
		isCalibrated = true;
	}
	
	public void suppress() {
		suppress = true;
	}
	
	public boolean takeControl() {
		return !isCalibrated;
	}
	
	public void fetchSample(float[] sample, int index) {
		sp.fetchSample(sample, index);
		sample[index] = (sample[index] - offset) / range;
	}

}
