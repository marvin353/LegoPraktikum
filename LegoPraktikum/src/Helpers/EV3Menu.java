package Helpers;

import java.util.HashMap;
import java.util.Map;
import lejos.hardware.lcd.LCD;
import States.BrueckeFahren;
import States.FarbfeldFinden;
import States.ISection;
import States.LinieFahren;
import States.PaketLiefern;
import lejos.hardware.Button;
import LegoPraktikumPackage.Robot;
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
	      LCD.drawString(sec.getName(), 0, index, true);
	    } else {
	      LCD.drawString(sec.getName(), 0, index, false);
	    }
	    index++;
	  }
	}
	
	public ISection getMarked(Robot robot) {
	  int index = 0;
    for(ISection sec: sectionMap.values()){
      if(index == markedIndex) {
        
        if(sec instanceof LinieFahren) {
          return new LinieFahren(robot);
        } else if (sec instanceof PaketLiefern) {
          return new PaketLiefern(robot);
        } else if (sec instanceof BrueckeFahren) {
          return new BrueckeFahren(robot);
        } else if (sec instanceof FarbfeldFinden) {
          return new FarbfeldFinden(robot);
        }
      }
      index++;
    }
    
    return null;
}
	
	public void up(){		
		markedIndex = (markedIndex - 1) % sectionMap.size();
		if (markedIndex < 0) markedIndex += sectionMap.size();
	}
	
	public void down(){
		markedIndex = (markedIndex + 1) % sectionMap.size();
	}
}
