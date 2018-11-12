

package States;

import States.ISection;
import lejos.utility.Delay;
import LegoPraktikumPackage.Robot;

public class Test implements Runnable, ISection{
	Robot robot;
	
	public Test(Robot robot) {
		this.robot = robot;
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRunningState(boolean state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {

		
		//robot.turnRight(100, true);
		

		
		robot.turnLeftPilot(90);
		Delay.msDelay(2000);
		robot.turnRightPilot(90);
		Delay.msDelay(2000);
		robot.goForwardPilot(10);
		Delay.msDelay(2000);
		robot.turnLeftPilot(90);
		Delay.msDelay(2000);
		robot.turnRightPilot(90);
		Delay.msDelay(2000);
		robot.goForwardPilot(10);

		/*robot.turnLeft(90);
		robot.turnRight(180);
		robot.turnLeft(90);
		robot.goForwardByDegree(720);*/
		

	}
}