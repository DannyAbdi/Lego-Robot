import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class Dark {
	
	public void action() {
		
		float WHEEL_DIAMETER = 43f; 
		float AXLE_LENGTH = 114f; 
		float FAST = 200f;
		float SLOW = 100f;
		
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.D);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.A);
		
		Wheel wL = WheeledChassis.modelWheel(mL, WHEEL_DIAMETER).offset(-AXLE_LENGTH / 2);
		Wheel wR = WheeledChassis.modelWheel(mR, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Wheel[] wheels = new Wheel[] {wR, wL};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		if (takeControl()) {
			
			pilot.setLinearSpeed(SLOW);
		} else {
			
			pilot.setLinearSpeed(FAST);
		}
	}

	public static boolean takeControl() {
		
		NXTLightSensor red = new NXTLightSensor(SensorPort.S1);
		SampleProvider light = red.getRedMode();
		
		float[] lightLevel = new float[1];
		light.fetchSample(lightLevel, 0);
		float currentLight = lightLevel[0];
		
		if (currentLight > 0.5f) {
			red.close();
			return true;
		}
		
		red.close();
		return false;	
	}
	
	public void suppress() {
	
	}
}