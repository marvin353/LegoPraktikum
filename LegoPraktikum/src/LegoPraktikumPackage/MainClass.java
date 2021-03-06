package LegoPraktikumPackage;

import Helpers.EV3Menu;
import Helpers.MenuHandler;
import States.ISection;
import States.LinieFahren;
import States.PaketLiefern;
import States.BrueckeFahren;
import States.FarbfeldFinden;
import States.Stop;
import States.Test;
import lejos.hardware.Button;


public class MainClass {
  
  static EV3Menu menu = new EV3Menu();
  static Robot robot = new Robot();

	public static void main(String[] args) {
	  
		ISection linie = new LinieFahren(robot);
		//ISection stop = new Stop(robot);
		ISection test = new Test(robot);
		ISection paket = new PaketLiefern(robot);
		ISection farbe = new FarbfeldFinden(robot);
		ISection bruecke = new BrueckeFahren(robot);

        menu.addSection(SECTION_ENUM.LINIE, linie);
		//menu.addSection(SECTION_ENUM.TEST, test);
		menu.addSection(SECTION_ENUM.PAKET, paket);
		menu.addSection(SECTION_ENUM.SUCHE, farbe);
        menu.addSection(SECTION_ENUM.BRUECKE, bruecke);

		

    MenuHandler handler = new MenuHandler(menu, robot);
    Thread t = new Thread(handler);
    t.start();
   
	}

}
