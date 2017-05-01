package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {
	private static class Constants
	{
		static double WHEEL_DIAMETER = 2.20472; // wheel diameter in in.
		static double TRACK_DIAMETER = 4.40945; // distance b/w wheels in in.
		static NXTRegulatedMotor LEFT_MOTOR = Motor.B;
		static NXTRegulatedMotor RIGHT_MOTOR = Motor.A;
	}
	
	private static DifferentialPilot pilot = new DifferentialPilot
			(Constants.WHEEL_DIAMETER, Constants.TRACK_DIAMETER,
					Constants.LEFT_MOTOR, Constants.RIGHT_MOTOR);
	private static OdometryPoseProvider poseProvider =
			new OdometryPoseProvider(pilot);
	public static void main(String[] args)
	{
		// Bind the escape button to escape the program, so we can
		//	get out of infinite looping if necessary
		Button.ESCAPE.addButtonListener(new ButtonListener() {
		   public void buttonPressed(Button b) {
		      System.exit(0);
		   }

		   public void buttonReleased(Button b) {
		      // Nothing
		   }
		});
		
		// Set robot to origin with 90 deg heading (along Y axis)
		poseProvider.setPose(new Pose(0.0f, 0.0f, 90.0f));

		// Add behaviors to the arbitrator so we can do stuff
		MoveBehavior moveBehav = new MoveBehavior(pilot);
		CorrectiveBehavior correctiveBehav = new CorrectiveBehavior(pilot, poseProvider);
		AvoidBehavior avoidBehav = new AvoidBehavior(pilot, poseProvider);
		LineBehavior lineBehav = new LineBehavior(pilot, poseProvider);
		
		// Let's calibrate the light sensor before starting
		lineBehav.calibrate();
		
		System.out.println("Press a button to start");
		Button.waitForAnyPress();
				
		Behavior [] behavArray = {moveBehav, correctiveBehav, avoidBehav, lineBehav};
		Arbitrator arby = new Arbitrator(behavArray);
		arby.start();
	}
}