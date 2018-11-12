

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
		

		robot.LookLeft();
		Delay.msDelay(1000);
		robot.LookRight();
		Delay.msDelay(1000);
		robot.LookLeft();
		Delay.msDelay(1000);
		robot.LookRight();
<<<<<<< HEAD
		
		robot.turnLeftPilot(90);
		Delay.msDelay(2000);
		robot.turnRightPilot(90);
		Delay.msDelay(2000);
		robot.turnLeftPilot(90);
		Delay.msDelay(2000);
		robot.turnRightPilot(90);
=======

>>>>>>> bc1b503d7ebe33fe1f3efb5647059f9b74a32abb
		/*robot.turnLeft(90);
		robot.turnRight(180);
		robot.turnLeft(90);
		robot.goForwardByDegree(720);*/
		

		/*for (int i = 0; i < 2; i++) {

			robot.turnLeft(90);
			robot.turnRight(180);
			robot.turnLeft(90);
			robot.goForwardByDegree(360);
		}*/
		for (int i = 0; i < 1; i++) {
			//robot.turnRight(360);
		}*/
		//robot.turnRight(180);
	}
}