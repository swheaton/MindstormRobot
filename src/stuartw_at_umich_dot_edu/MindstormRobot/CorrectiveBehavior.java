
package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class CorrectiveBehavior implements Behavior {
	// True if behavior should be suppressed
	private boolean suppressed = false;
	private DifferentialPilot pilot;
	private OdometryPoseProvider poseProvider;
	
	static float TOO_FAR_LATERALLY = 12.0f * 2.0f; // 24 inches
	static float SMALL_ANGLE = 5.0f; // degrees

	CorrectiveBehavior(DifferentialPilot pilot, OdometryPoseProvider poseProvider)
	{
		this.pilot = pilot;
		this.poseProvider = poseProvider;
	}

	@Override
	// Take control if we're getting too far to the side of the maze,
	public boolean takeControl() {
		return Math.abs(poseProvider.getPose().getX()) > TOO_FAR_LATERALLY;
	}

	// Turn to face goal line, minus a few degrees
	@Override
	public void action() {
		System.out.println("Correcting");
		suppressed = false;
		float desiredHeading = 90.0f - poseProvider.getPose().getHeading();
		//System.out.println("hdg: " + heading);
		
		// Add a small angle pointing toward the center, so that it meander its way back
		//	to the center.
		desiredHeading += poseProvider.getPose().getX() < 0.0f ? -SMALL_ANGLE : SMALL_ANGLE;
		pilot.rotate(desiredHeading, true);
		while (pilot.isMoving() && !suppressed)
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
