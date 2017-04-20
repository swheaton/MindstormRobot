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
	private float currReading = 255.0f;
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
		Feature f = fd.scan();
		if (f != null)
		{
			if (f.getRangeReading().getRange() <= Constants.DETECT_DISTANCE)
			{
				currReading = f.getRangeReading().getRange();
				return true;
			}
		}
		System.out.println((int)poseProvider.getPose().getX() + "," + (int)poseProvider.getPose().getY());
		return false;
	}
	@Override
	public void action() {
		suppressed = false;
		double rotateAmount = -20.0;
		Pose pose = poseProvider.getPose();
		System.out.println(pose.getX() + "," + pose.getY());
		if (pose.getX() > 0 || pose.getX() == 0.0f && Math.random() < 0.5)
		{
			rotateAmount *= -1;
		}
		/*if (currReading <= Constants.SMALL_DETECT_DISTANCE)
		{
			//rotateAmount = -20.0;
		}*/
		pilot.rotate(rotateAmount, true);
		while (pilot.isMoving() && !suppressed)
		{
			Thread.yield();
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
