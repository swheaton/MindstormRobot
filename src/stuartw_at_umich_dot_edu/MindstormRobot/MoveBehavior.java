
package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class MoveBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private OdometryPoseProvider poseProvider;

	MoveBehavior(DifferentialPilot pilot, OdometryPoseProvider poseProvider)
	{
		this.pilot = pilot;
		this.poseProvider = poseProvider;
	}

	@Override
	// Always want control
	public boolean takeControl() {
		return true;
	}

	// Move forward as the behavior
	@Override
	public void action() {
		suppressed = false;
		pilot.forward();
		while (!suppressed)
		{
			Thread.yield();
		}
		this.pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}	
}
