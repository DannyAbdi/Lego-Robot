import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
	
	//Create sensors
	public static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
	public static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
	public static EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
//	NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
	

	public static void main(String[] args) {
		
		//Create Pilot
		MovePilot pilot = getPilot(MotorPort.A, MotorPort.D, 43, 120);
		pilot.setLinearSpeed(100);
		
		//Create Behaviours
		Behavior trundle = new Trundle(pilot);
		Behavior backup = new Backup(pilot, us);
		Behavior dark = new Dark(pilot, cs);
		Behavior light = new Light(pilot, cs);
		Behavior emergencyStop = new EmergencyStop(pilot, ts);
		Behavior batteryLevel = new BatteryLevel();
		Behavior bluetooth = new Bluetooth();
		Behavior calibrate = new Calibrate(cs);

		//Create Arbitrator and start it
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, light, dark, backup, bluetooth, calibrate, emergencyStop, batteryLevel});
		ab.go();
		
	}
	
	private static MovePilot getPilot(Port left, Port right, int diam, int offset) { //Code to create pilot
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
		Wheel wLeft = WheeledChassis.modelWheel(mL, diam).offset(-offset/2);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
		Wheel wRight = WheeledChassis.modelWheel(mR, diam).offset(offset/2);
		Chassis chassis = new WheeledChassis((new Wheel[] {wRight, wLeft}), WheeledChassis.TYPE_DIFFERENTIAL);
		return new MovePilot(chassis);
		
	}

}
