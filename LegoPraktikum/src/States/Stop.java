package States;

import States.ISection;
import LegoPraktikumPackage.Robot;

public class Stop implements Runnable, ISection{
	Robot robot;
	
	public Stop(Robot robot) {
		this.robot = robot;
	}

	@Override
	public String getName() {
		return "Stop";
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
		robot.stopLeftMotor();
		robot.stopRightMotor();
	}

}
