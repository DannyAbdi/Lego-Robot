import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class StopBus implements Behavior {
	
	private Navigator navigator;
	private int count = 0;
	private boolean suppress = false;
	private boolean stopping = false;
	private Bluetooth bt;
	
	//Colour sensor
	private EV3ColorSensor cs;
	private SampleProvider rm;
	private float[] light = new float[1];
	
	//Touch Sensor
	private EV3TouchSensor ts;
	private SampleProvider tm;
	private float[] touch = new float[1];
	
	//Sound sensor
	private NXTSoundSensor ss;
	private SensorMode sound;
	private SampleProvider clap;
	private float[] soundLevel = new float[1];
	
	StopBus(Navigator navigator, EV3TouchSensor ts, EV3ColorSensor cs, NXTSoundSensor ss, Bluetooth bt) {
		this.navigator = navigator;
		this.ss = ss;
		this.bt = bt;
		this.ts = ts;
		this.cs = cs;
		tm = this.ts.getTouchMode();
		rm = this.cs.getColorIDMode();
		sound = (SensorMode) this.ss.getDBAMode();
		clap = new ClapFilter(sound, 0.6f, 100);
	}
	
	public void action() {
		navigator.stop();
		
		bt.sendMessage("BUS STOPPED");
		tm.fetchSample(touch, 0);
		count = 0;
		
		while (!suppress) {
			tm.fetchSample(touch, 0);
			count++;
			if (count >= 1000000 || touch[0] == 1) {
				break;
			}
		}
		LCD.clearDisplay();
		stopping = false;
	}
	
	public void suppress() {
		suppress = true;
	}
	
	public boolean takeControl() {
		clap.fetchSample(soundLevel, 0);
		Boolean BTMessage = false;
		if (bt != null) { //Check that bluetooth is connected
			BTMessage = bt.getMessage().equals("STOP");
		}
		if (BTMessage || soundLevel[0] == 1) {
			stopping = true;
			LCD.drawString("STOPPING", 0, 4);
		}
		rm.fetchSample(light, 0);   
		return (light[0] == Color.RED && stopping);
	}
	
}
