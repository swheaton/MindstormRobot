package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Behavior;

public class LineBehavior implements Behavior {
	private boolean suppressed = false;
	private LightSensor ls;

	LineBehavior()
	{
		ls = new LightSensor(SensorPort.S3);
	}
	@Override
	public boolean takeControl() {
		int reading = ls.readValue();
		System.out.println("read: " + reading);
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed)
		{
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
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
