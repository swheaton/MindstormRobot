package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class LineBehavior implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;
	private DifferentialPilot pilot;
	private OdometryPoseProvider poseProvider;

	LineBehavior(DifferentialPilot pilot, OdometryPoseProvider poseProvider)
	{
		ls = new LightSensor(SensorPort.S3);
		this.pilot = pilot;
		this.poseProvider = poseProvider;
	}
	@Override
	public boolean takeControl() {
		// Experience tells me 50 is a decent number to detect the tape line
		int reading = ls.readValue();
		System.out.println("read: " + reading);
		return reading > 50;
	}

	@Override
	public void action() {
		suppressed = false;
		
		// First rotate to face initial direction
		float heading = poseProvider.getPose().getHeading();
		System.out.println("hdg: " + heading);
		pilot.rotate(90-heading, true);
		while (pilot.isMoving() && !suppressed)
		{
			Thread.yield();
		}
		
		// Now travel 12" after finish line
		pilot.travel(12.0, true);
		while (pilot.isMoving() && !suppressed)
		{
			Thread.yield();
		}
		
		System.out.println("WHAT THE F");
		
		// Now stop
		pilot.stop();
		Button.waitForAnyPress();
		//System.exit(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	public void calibrate()
	{
		System.out.println("Calibrate over floor");
		Button.waitForAnyPress();
		calibrateFloor();
		System.out.println("Calibrate over line");
		Button.waitForAnyPress();
		calibrateLine();
	}
	
	public void calibrateFloor()
	{
		ls.calibrateLow();
	}
	
	public void calibrateLine()
	{
		ls.calibrateHigh();
	}
}
