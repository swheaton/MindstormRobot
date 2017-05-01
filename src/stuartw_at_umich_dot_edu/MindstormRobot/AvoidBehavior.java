package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.subsumption.Behavior;

public class AvoidBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;
	private boolean objectDetected = false;
	private UltrasonicSensor us;
	private FeatureDetector fd;
	private DifferentialPilot pilot;
	private PoseProvider poseProvider;

	private static class Constants
	{
		static int MAX_DISTANCE = 70;
		static int PERIOD = 500;
		static float DETECT_DISTANCE = 24.0f;
		static float SMALL_DETECT_DISTANCE = 21.0f;
	}

	AvoidBehavior(DifferentialPilot pilot, PoseProvider poseProvider)
	{
		this.pilot = pilot;
		this.poseProvider = poseProvider;
		us = new UltrasonicSensor(SensorPort.S4);
		fd = new RangeFeatureDetector(us, Constants.MAX_DISTANCE, Constants.PERIOD);
	}

	@Override
	public boolean takeControl() {
		// Start taking control when sensor sees an obstacle
		Feature f = fd.scan();
		if (f != null)
		{
			if (f.getRangeReading().getRange() <= Constants.DETECT_DISTANCE)
			{
				objectDetected = true;
			}
		}
		return objectDetected;
	}
	
	private double getRotateAmount()
	{
		double rotateAmount = -20.0;
		Pose pose = poseProvider.getPose();
		
		// If should turn left:
		//	- on the right side
		//	- in the middle? flip a coin
		if (pose.getX() > 0 ||
				Math.abs(pose.getX()) < 0.0001f && Math.random() < 0.5)
		{
			rotateAmount *= -1;
		}
		
		return rotateAmount;
	}
	
	@Override
	public void action() {
		System.out.println("Avoid!");
		suppressed = false;

		// Rotate the robot and hope it turns past the obstacle. If not, it
		//	will sense it again and this loop continues until obstacle is
		//	avoided.
		pilot.rotate(getRotateAmount(), true);
		while (pilot.isMoving() && !suppressed)
		{
			Thread.yield();
		}
		objectDetected = false;
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
