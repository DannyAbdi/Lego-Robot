import java.util.Random;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Backup implements Behavior {

	private EV3UltrasonicSensor us;
	SampleProvider sp;
	private MovePilot pilot;
	private Random rand = new Random();
	float[] distance = new float[1];
	final static float STOP_DISTANCE = 0.2f;
	
	Backup(MovePilot p, EV3UltrasonicSensor sensor) {
		this.pilot = p;
		us = sensor;
		sp = us.getDistanceMode();
	}

	public boolean takeControl() {	
		sp.fetchSample(distance, 0);
		return (distance[0] < STOP_DISTANCE);
	}

	public void action() {
		pilot.travel(-100);
		pilot.rotate((2* rand.nextInt(2) - 1) * 30);
	}

	public void suppress() {
	}

}