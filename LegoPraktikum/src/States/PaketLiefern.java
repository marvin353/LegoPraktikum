package States;

import LegoPraktikumPackage.Robot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;

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
		  //robot.LookRight();
	  }

	  @Override
	  public void onEnd() {
	    // TODO Auto-generated method stub
	    
	  }

	  @Override
	  public void run() {
	    onStart();
	    robot.goForwardPilot(500);
	    while(running) {
	    	
	    	double distance = robot.getSensors().getDistance();
	    	int delayTime = 2000;
	    	
	    	if (distance <= 0.4) {
	    		robot.stopLeftMotor(true);
	    		robot.stopRightMotor();
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(10);
	    		Delay.msDelay(delayTime);

	    		robot.turnRightPilot(90);
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(50);
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(-10);
	    		Delay.msDelay(delayTime);
	    		robot.turnLeftPilot(90);
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(30);
	    		Delay.msDelay(delayTime);
	    		robot.turnRightPilot(90);
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(30);
	    		Delay.msDelay(delayTime);
	    		robot.turnRightPilot(90);
	    		Delay.msDelay(delayTime);
	    		robot.goForwardPilot(100);
	    	}
	    	
	    	/*
	    	boolean packageHit = false;
	    	
	    	
	    	if(distance < distanceToPackage) {
	    		if (packageHit == false) {
	    			robot.goForwardPilot(10);
	    			Delay.msDelay(2000);
	    			robot.turnRightPilot(90);
	    			Delay.msDelay(2000);
	    		} else {
	    			
	    		}
	    	} else {
	    		
	    	}
	    	
	    	double relativeDistance = (distance - distanceToWall);
	        
	        int speedMotorRight =  (int) ((1-relativeDistance) * SPEED_FACTOR) - 100;
	        int speedMotorLeft = (int) (relativeDistance * SPEED_FACTOR) - 100;
	        
	        robot.setLeftMotorSpeed(Math.abs(speedMotorLeft));
	        robot.setRightMotorSpeed(Math.abs(speedMotorRight));
	        
	        if(speedMotorRight < 0)
	          robot.setRightMotorGoForward();
	        else robot.setRightMotorGoBackward();
	        
	        if (speedMotorLeft < 0)
	        	robot.setLeftMotorGoForward();
	        else robot.setLeftMotorGoBackward(); */
	    	
	    }
	    
	  }

	 @Override
	 public void setRunningState(boolean state) {
	 	// TODO Auto-generated method stub
		
	 }

}
