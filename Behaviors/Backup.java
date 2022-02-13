import java.util.Random;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Backup implements Behavior {

	private EV3UltrasonicSensor us;
	private MovePilot pilot;
	private Random rand = new Random();

	float[] distance = new float[1];
	final static float TWENTY_CM = 0.2f;

	
	public Backup(MovePilot pilot, EV3UltrasonicSensor us) {
		this.pilot = pilot;
		this.us = us;
	}

	public boolean takeControl() {
		SampleProvider sp = us.getDistanceMode();		
		sp.fetchSample(distance, 0);
		float currentDistance = distance[0];
		return (currentDistance < TWENTY_CM);
	}

	public void action() {
		pilot.travel(-200);
		pilot.rotate((2* rand.nextInt(2) - 1) * 30);
	}

	public void suppress() {
		
	}

}
