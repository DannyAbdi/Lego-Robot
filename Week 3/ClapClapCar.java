import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;

public class ClapClapCar {

	public static void main(String[] args) {
		float[] level = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		SampleProvider sound = ss.getDBAMode();
		float maxSoundLevel = 0;
		float minSoundLevel = 1;
		
		while (Button.ENTER.isUp()) {
			sound.fetchSample(level, 0);
			float currentSound = level[0];
			
			if (currentSound < minSoundLevel) {
				minSoundLevel = currentSound;
			}
			
			if (currentSound > maxSoundLevel) {
				maxSoundLevel = currentSound;
			}
			
			LCD.drawString("Min: " + minSoundLevel, 2, 2);
			LCD.drawString("Max: " + maxSoundLevel, 2, 4);
		}
		
		ss.close();

	}

}
