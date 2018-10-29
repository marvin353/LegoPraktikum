package Helpers;
import LegoPraktikumPackage.Robot;
import lejos.hardware.Button;


public class MenuHandler implements Runnable{
	
	EV3Menu menu;
	Robot robot;
	
	public MenuHandler(EV3Menu menu, Robot robot) {
		this.menu = menu;
		this.robot = robot;
	}

	@Override
	public void run() {
		while(true) {
			menu.drawMenu();
			int event = Button.getButtons();
			switch(event) {
			case Button.ID_UP:
				menu.up();
				break;
			case Button.ID_DOWN:
				menu.down();
				break;
			case Button.ID_ENTER:
				robot.run(menu.getMarked());
				break;
			default:
				break;
			}
			
			
		}
		
	}

}
