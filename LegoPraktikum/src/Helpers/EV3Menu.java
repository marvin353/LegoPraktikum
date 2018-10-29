package Helpers;

import java.util.HashMap;
import java.util.Map;
import lejos.hardware.lcd.LCD;
import States.ISection;
import LegoPraktikumPackage.SECTION_ENUM;

public class EV3Menu {
		
	Map<SECTION_ENUM, ISection> sectionMap = new HashMap<>();
	private int markedIndex = 0;
	
	public EV3Menu() {
		
   }
	
  public void addSection(SECTION_ENUM sectionID, ISection section) {
	  sectionMap.put(sectionID, section);
	}
	
	public void drawMenu() {
	  int index = 0;
	  for(ISection sec: sectionMap.values()){
	    if(index == markedIndex) {
	      LCD.drawString(sec.getName(), 0, index * 2, true);
	    } else {
	      LCD.drawString(sec.getName(), 0, index * 2, false);
	    }
	    index++;
	  }
	}
	
	public ISection getMarked() {
	  int index = 0;
    for(ISection sec: sectionMap.values()){
      if(index == markedIndex) {
        return sec;
      }
      index++;
    }
    
    return null;
}

}
