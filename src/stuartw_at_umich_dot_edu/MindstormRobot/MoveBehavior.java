
package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class MoveBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;
	
	private static class Constants {
		static double WHEEL_DIAMETER = 56.0; // wheel diameter in mm
		static double TRACK_DIAMETER = 112.0; // distance b/w wheels in mm
		static NXTRegulatedMotor LEFT_MOTOR = Motor.A;
		static NXTRegulatedMotor RIGHT_MOTOR = Motor.B;
	}
	
	private DifferentialPilot pilot = new DifferentialPilot
			(Constants.WHEEL_DIAMETER, Constants.TRACK_DIAMETER,
					Constants.LEFT_MOTOR, Constants.RIGHT_MOTOR);

	@Override
	// Always want control
	public boolean takeControl() {
		return true;
	}

	// Move forward as the behavior
	// TODO move toward goal line instead of always straight
	@Override
	public void action() {
		pilot.forward();
		while (!suppressed)
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
