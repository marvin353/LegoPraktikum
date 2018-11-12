package States;

import LegoPraktikumPackage.Robot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class PaketLiefern implements Runnable, ISection {
	
	private final String NAME = "Paket liefern";
	  private static double LIGHT_SENSOR_WHITE_VALUE = 0.5; //typischerweise um die 60
	  private static double LIGHT_SENSOR_BLACK_VALUE = 0.05; //typischerweise um die 20
	  static int SPEED_FACTOR = 540;
	  private static int treshhold_line_lost = 300;
	  private static int distanceToWall = 100; //KP...
	  private static int distanceToPackage = 50; //KP
			  
	  
	  EV3ColorSensor colorSensor;
	  EV3UltrasonicSensor ulSensor;
	  EV3LargeRegulatedMotor motorRight;
	  EV3LargeRegulatedMotor motorLeft;
	  EV3MediumRegulatedMotor mediumMotor;
	  
	  Robot robot;

	  public PaketLiefern(Robot robot) {
		  this.robot = robot;
		  robot.changeSettingsForPackageDelivery();
	  }

	  @Override
	  public String getName() {
	    return NAME;
	  }

	  @Override
	  public void onStart() {
		  //Check if other State thread is running 
		  robot.LookLeft();
	  }

	  @Override
	  public void onEnd() {
	    // TODO Auto-generated method stub
	    
	  }

	  @Override
	  public void run() {
	    onStart();
	    
	    while(running) {
	    	
	    	double distance = robot.getSensors().getDistance();
	    	boolean packageHit = false;
	    	
	    	
	    	if(distance > distanceToPackage) {
	    		if (packageHit == false) {
	    			
	    		} else {
	    			
	    		}
	    	} else {
	    		
	    	}
	    }
	    
	  }

	 @Override
	 public void setRunningState(boolean state) {
	 	// TODO Auto-generated method stub
		
	 }

}
