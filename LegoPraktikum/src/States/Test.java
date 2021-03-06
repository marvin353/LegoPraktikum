

package States;

import States.ISection;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import LegoPraktikumPackage.Robot;

public class Test implements Runnable, ISection{
	Robot robot;
	
	public Test(Robot robot) {
		this.robot = robot;
	}

	@Override
	public String getName() {
		return "Test1";
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
		
		drawTest();
		
	}
	
	public void drawTest() {
		int i = 0;
		while(i < 3) {
			i++;
		}

		LCD.drawString("Test1", 0, 2);
		Delay.msDelay(3000);        
		LCD.clear();
		Delay.msDelay(1000);
        robot.run(new Test2(robot));
	}
}