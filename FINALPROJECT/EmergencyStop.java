import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStop implements Behavior {
	
	private Navigator navigator;
	private float[] distance = new float[1];
	private float stopDistance = 0.1f;
	private EV3UltrasonicSensor us;
	private SampleProvider sp;
	private boolean suppress = false;
	
	EmergencyStop(Navigator navigator, EV3UltrasonicSensor us) {
		this.navigator = navigator;
		this.us = us;
		sp = this.us.getDistanceMode();
	}
	
	public void action() {
		navigator.stop();
		TunePlayer sound = new TunePlayer();
		sound.run();
		while (distance[0] <= stopDistance || !suppress) {
			sp.fetchSample(distance, 0);
		}
	}
	
	public void suppress() {
		suppress = true;
	}
	
	public boolean takeControl() {
		sp.fetchSample(distance, 0);
		return (distance[0] <= stopDistance);
	}
}

class TunePlayer extends Thread {
	public void run() {
		playTune(); 
	}
	private void playTune() {
		Sound.playSample(new File("CarHorn.wav"), 100);
	}
}
