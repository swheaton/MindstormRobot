package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {
	public static void main(String[] args)
	{
		// Bind the enter button to escape the program, so we can
		//	get out of infinite looping if necessary
		Button.ESCAPE.addButtonListener(new ButtonListener() {
		   public void buttonPressed(Button b) {
		      System.exit(0);
		   }

		   public void buttonReleased(Button b) {
		      // Nothing
		   }
		});

		MoveBehavior moveBehav = new MoveBehavior();
		Behavior [] behavArray = {moveBehav};
		Arbitrator arby = new Arbitrator(behavArray);
		arby.start();
	}
}