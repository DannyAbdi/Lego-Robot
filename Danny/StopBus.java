import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class StopBus implements Behavior {
	
	private MovePilot pilot;
	private EV3TouchSensor ts;
	private EV3ColorSensor cs;
	private SampleProvider tm = ts.getTouchMode();
	private SampleProvider rm = cs.getRedMode();
	float[] light = new float[1];
	float[] touch = new float[1];
	
	StopBus(MovePilot p, EV3TouchSensor ts, EV3ColorSensor cs) {
		this.pilot = p;
	}
	
	public void action() {
		
		tm.fetchSample(touch, 0);
		int count = 0;
		
		while (touch[0] != 1 || count < 10000) {
			Delay.msDelay(1);
			count++;
		}
		pilot.forward();
	}
	
	public void suppress() {
		
	}

	public boolean takeControl() {
		
		rm.fetchSample(light, 0);
		return (light[0] == 1);
	}
}