import java.io.IOException;
import lejos.hardware.Button;
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
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class Main {
	
	//Sensors
	public static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
	public static EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S2);
	public static EV3TouchSensor ts = new EV3TouchSensor(SensorPort.S3);
	public static NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);

	//Robot dimensions
	private static int AXLE_LENGTH = 120;
	private static int WHEEL_DIAMETER = 43;

	public static void main(String[] args) {
		
//		//Welcome screen
//		LCD.drawString("WELCOME", 0, 2);
//		Delay.msDelay(2000);
//		LCD.drawString("Robot created by", 0, 2);
//		LCD.drawString("Amy, Lucas,", 0, 3);
//		LCD.drawString("Danny & Yashil", 0, 4);
//		LCD.drawString("Enter to start", 0, 6);
//		Button.ENTER.waitForPressAndRelease();
//		LCD.clearDisplay();
//		
		//Connect to bluetooth
		BTConnect btc = new BTConnect();
		Bluetooth bluetooth = null;
		while (Button.ESCAPE.isUp()) {
			LCD.drawString("BT connecting", 0, 2);
			LCD.drawString("Escape to stop", 0, 3);
			bluetooth = getConnection(btc);
			if (bluetooth != null) {
				LCD.drawString("Bluetooth success", 0, 4);
				Delay.msDelay(1000);
				break;
			} else {
				LCD.drawString("Bluetooth failed", 0, 4);
				LCD.drawString("Enter to retry", 0, 5);
				Button.ENTER.waitForPressAndRelease();
			}
			LCD.clearDisplay();
		}
		LCD.clearDisplay();
	
		//Create needed objects and set speed
		Chassis chassis = getChassis(MotorPort.A, MotorPort.D, WHEEL_DIAMETER, AXLE_LENGTH);
		MovePilot pilot = new MovePilot(chassis);
		PoseProvider poseProvider = chassis.getPoseProvider();
		Navigator navigator = new Navigator(pilot, poseProvider);
		pilot.setLinearSpeed(100);
		pilot.setAngularSpeed(100);
		
		//Create behaviours
		Behavior followPath = new FollowPath(navigator);
		Behavior onYellow = new OnYellow(cs, pilot, navigator);
		Behavior onGreen = new OnGreen(cs, pilot, navigator);
		Behavior stopBus = new StopBus(navigator, ts, cs, ss, bluetooth);
		Behavior emergencyStop = new EmergencyStop(navigator, us);
		Behavior interrupt = new Interrupt(navigator, us, cs, ts, ss); 
		Behavior batteryLevel = new BatteryLevel();
		
		Arbitrator ab = new Arbitrator(new Behavior[] {followPath, onYellow, onGreen, stopBus, emergencyStop, batteryLevel, interrupt});
		LCD.drawString("Enter to start", 0, 4);
		Button.ENTER.waitForPressAndRelease();
		LCD.clearDisplay();
		ab.go();
	}
	
	private static Chassis getChassis(Port left, Port right, int diam, int offset) { //Code to create chassis
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(left);
		Wheel wLeft = WheeledChassis.modelWheel(mL, diam).offset(-offset/2);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(right);
		Wheel wRight = WheeledChassis.modelWheel(mR, diam).offset(offset/2);
		return new WheeledChassis((new Wheel[] {wRight, wLeft}), WheeledChassis.TYPE_DIFFERENTIAL);
	}
	
	private static Bluetooth getConnection(BTConnect btc) {
		Bluetooth bluetooth = null;
		try {
			if (btc.connect()) {
				bluetooth = new Bluetooth(btc.getIn(), btc.getOut());
			}
		} catch (IOException e) {
			LCD.drawString("Bluetooth failed", 0, 4);
		}
		return bluetooth;
	}

}
