package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
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

	private static class Constants
	{
		static int MAX_DISTANCE = 70;
		static int PERIOD = 500;
		static float DETECT_DISTANCE = 24.0f;
		static float SMALL_DETECT_DISTANCE = 21.0f;
	}

	AvoidBehavior(DifferentialPilot pilot)
	{
		this.pilot = pilot;
		us = new UltrasonicSensor(SensorPort.S4);
		fd = new RangeFeatureDetector(us, Constants.MAX_DISTANCE, Constants.PERIOD);
	}

	@Override
	public boolean takeControl() {
		Feature f = fd.scan();
		if (f != null)
		{
			//System.out.println(f.getRangeReading().getRange());
			if (f.getRangeReading().getRange() <= Constants.DETECT_DISTANCE)
			{
				System.out.println("return true");
				currReading = f.getRangeReading().getRange();
				return true;
			}
		}
		return false;
	}
private static int count = 0;
	@Override
	public void action() {
		suppressed = false;
		double rotateAmount = -20.0;
		if (currReading <= Constants.SMALL_DETECT_DISTANCE)
		{
			//rotateAmount = -20.0;
		}
		System.out.println("ACTION: " + count++);
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
