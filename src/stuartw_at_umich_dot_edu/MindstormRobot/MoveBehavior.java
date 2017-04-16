
package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class MoveBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;

	@Override
	// Always want control
	public boolean takeControl() {
		return true;
	}

	// Move forward as the behavior
	// TODO move toward goal line instead of always straight
	@Override
	public void action() {
		Motor.A.forward();
		Motor.B.forward();
		while (!suppressed)
		{
			Thread.yield();
		}
		Motor.A.stop();
		Motor.B.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}	
}
