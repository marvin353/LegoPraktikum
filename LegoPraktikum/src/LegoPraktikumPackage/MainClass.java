package LegoPraktikumPackage;

import Helpers.EV3Menu;
import States.LinieFahren;

public class MainClass {
  
  static EV3Menu menu = new EV3Menu();
  static Robot robot = new Robot();

	public static void main(String[] args) {
		ISection linie = new LinieFahren(robot);
		menu.addSection(SECTION_ENUM.LINIE, linie);
		menu.drawMenu();
		
		//robot.runLinieFahren();
		
		
		// TODO Auto-generated method stub
		
		
		//Menu anzeigen
		
		//Je nach Auswahl entsprechende Section starten oder Rennen starten
		
		//Sensor Thread starten

	}

}
