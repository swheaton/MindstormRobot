package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {
	private static class Constants
	{
		static double WHEEL_DIAMETER = 56.0; // wheel diameter in mm
		static double TRACK_DIAMETER = 112.0; // distance b/w wheels in mm
		static NXTRegulatedMotor LEFT_MOTOR = Motor.B;
		static NXTRegulatedMotor RIGHT_MOTOR = Motor.A;
	}
	
	private static DifferentialPilot pilot = new DifferentialPilot
			(Constants.WHEEL_DIAMETER, Constants.TRACK_DIAMETER,
					Constants.LEFT_MOTOR, Constants.RIGHT_MOTOR);
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

		Button.waitForAnyPress();
		// Add behaviors to the arbitrator so we can do stuff
		MoveBehavior moveBehav = new MoveBehavior(pilot);
		AvoidBehavior avoidBehav = new AvoidBehavior(pilot);
		Behavior [] behavArray = {moveBehav, avoidBehav};
		Arbitrator arby = new Arbitrator(behavArray);
		arby.start();
	}
}