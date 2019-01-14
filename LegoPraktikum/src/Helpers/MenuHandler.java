package Helpers;
import LegoPraktikumPackage.Robot;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class MenuHandler implements Runnable{
	
	EV3Menu menu;
	Robot robot;
	
	public MenuHandler(EV3Menu menu, Robot robot) {
		this.menu = menu;
		this.robot = robot;
	}

	@Override
	public void run() {
		menu.drawMenu();
		while(true) {
			
			int event = Button.getButtons();
			switch(event) {
			case Button.ID_UP:
				menu.up();
				menu.drawMenu();
				Delay.msDelay(100);
				break;
			case Button.ID_DOWN:
				menu.down();
				menu.drawMenu();
				Delay.msDelay(100);
				break;
			case Button.ID_ENTER:
			  robot.run(menu.getMarked());
			  //Button.waitForAnyPress();
			  //robot.stopCurrentSection();
			  Delay.msDelay(200);
				break;
			case Button.ID_LEFT:
				LCD.drawString("LEFT Pressed", 1, 1);
				robot.stopCurrentSection();
				LCD.drawString("LEFT Pressed", 4, 1);
				Delay.msDelay(500);
				LCD.clear();
				menu.drawMenu();
				break;
			case Button.ID_RIGHT:
				LCD.drawString("Right Pressed", 1, 1);
				robot.stopCurrentSection();
				LCD.drawString("RIGHT Pressed", 1, 1);
				Delay.msDelay(500);
				LCD.clear();
				menu.drawMenu();
				break;
			default:
				break;
			}
		}
	}
}
