package stuartw_at_umich_dot_edu.MindstormRobot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Robot {
	public static void main(String[] args)
	{
		MoveBehavior moveBehav = new MoveBehavior();
		Behavior [] behavArray = {moveBehav};
		Arbitrator arby = new Arbitrator(behavArray);
		arby.start();
	}
}