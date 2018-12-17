package States;

import States.ISection;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import LegoPraktikumPackage.Robot;

public class Test2 implements Runnable, ISection{
	Robot robot;
	
	public Test2(Robot robot) {
		this.robot = robot;
	}

	@Override
	public String getName() {
		return "Test 2";
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

		LCD.drawString("Test 2", 0, 4);
		Delay.msDelay(3000);
		LCD.clear();
		Delay.msDelay(1000);
        robot.run(new Test(robot));
		
	}
}