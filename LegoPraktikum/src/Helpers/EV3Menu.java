package Helpers;

import java.util.HashMap;
import java.util.Map;
import lejos.nxt.LCD;

import LegoPraktikumPackage.ISection;
import LegoPraktikumPackage.SECTION_ENUM;

public class EV3Menu {
		
	Map<SECTION_ENUM, ISection> sectionMap = new HashMap<>();
	private int markedIndex = 0;
	LCD lcd;
	
	public EV3Menu() {
	  lcd = new LCD ();
	}
	
	public void addSection(SECTION_ENUM sectionID, ISection section) {
	  sectionMap.put(sectionID, section);
	}
	
	public void drawMenu() {
	  int index = 0;
	  for(ISection sec: sectionMap.values()){
	    if(index == markedIndex) {
	      lcd.drawString(sec.getName(), 5, index * 10, true);
	    } else {
	      lcd.drawString(sec.getName(), 5, index * 10, false);
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
