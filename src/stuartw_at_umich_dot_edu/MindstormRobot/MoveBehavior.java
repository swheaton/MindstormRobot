
package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class MoveBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;
	private DifferentialPilot pilot;

	MoveBehavior(DifferentialPilot pilot)
	{
		this.pilot = pilot;
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
