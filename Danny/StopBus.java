import java.util.concurrent.TimeUnit;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

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
		
		if (touch[0] == 1) {
			pilot.forward();
		} else {
		
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void suppress() {
		
	}

	public boolean takeControl() {
		
		rm.fetchSample(light, 0);
		return (light[0] == 1);
	}
}