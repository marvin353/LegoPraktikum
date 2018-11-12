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
		menu.drawMenu();
		while(true) {
			
			int event = Button.getButtons();
			switch(event) {
			case Button.ID_UP:
				menu.up();
				menu.drawMenu();
				break;
			case Button.ID_DOWN:
				menu.down();
				menu.drawMenu();
				break;
			case Button.ID_ENTER:
			  new Thread(new Runnable() {
			    public void run() {
			      robot.run(menu.getMarked());
			      System.out.println("start running from menu");
			    }
			    }).start(); 
			  
				break;
			default:
				break;
			}
		}
	}
}
