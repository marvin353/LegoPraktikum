package States;

import LegoPraktikumPackage.Robot;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
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
	  private boolean running;
	  
	  Robot robot;

	  public PaketLiefern(Robot robot) {
		  this.robot = robot;
		  robot.changeSettingsForPackageDelivery();
		  running = true;
	  }

	  @Override
	  public String getName() {
	    return NAME;
	  }

	  @Override
	  public void onStart() {
		  //Check if other State thread is running 
		  
		  robot.LookDownPaket();
	  }

	  @Override
	  public void onEnd() {
	    // TODO Auto-generated method stub
	    
	  }

	  @Override
	  public void run() {
	    onStart();
	    LCD.clear();
	    robot.goForwardPilot(500);
	    while(running) {
	    	
	    	double distance = robot.getSensors().getDistance();
	    	int delayTime = 2000;
	    	LCD.drawString("d " + distance, 1, 1);
	    	
	    	if (distance <= 0.45) {
	    		robot.stopLeftMotor(true);
	    		robot.stopRightMotor();
	    		Delay.msDelay(delayTime);
	    		
	    		//Go to "middle of package
	    		robot.goForwardPilot(10);
	    		Delay.msDelay(delayTime);

	    		//turn around
	    		robot.turnRightPilot(95);
	    		while(robot.isMoving()) {}
	    		Delay.msDelay(delayTime);
	    		
	    		//Push package to wall
	    		robot.goForwardPilot(60);
	    		while (robot.isMoving()){}
	    		Delay.msDelay(delayTime);
	    		
	    		//Go a bit back to have enough space for turning around
	    		robot.goForwardPilot(-10);
	    		Delay.msDelay(delayTime);
	    		
	    		//turn around
	    		robot.turnLeftPilot(100);
	    		while(robot.isMoving()) {}
	    		Delay.msDelay(delayTime);
	    		
	    		//Go a bit forward to get on other side of packages
	    		robot.goForwardPilot(15);
	    		Delay.msDelay(delayTime);
	    		
	    		//turn around
	    		robot.turnRightPilot(90);
	    		while(robot.isMoving()) {}
	    		Delay.msDelay(delayTime);
	    		
	    		//Go forward until touch sensor gets active (wall)
	    		robot.setLeftMotorSpeed(500);
	    		robot.setRightMotorSpeed(500);
	    		robot.setLeftMotorGoBackward();
	    		robot.setRightMotorGoBackward();
	    		while (robot.getSensors().getTouch1() == 0 || robot.getSensors().getTouch2() == 0) {
	    			
	    		}
	    		robot.stopLeftMotor(true);
	    		robot.stopRightMotor();
	    		Delay.msDelay(delayTime);
	    		
	    		//Go Back to have space for turning around
	    		robot.goForwardPilot(-5);
	    		
	    		//turn around
	    		robot.turnRightPilot(95);
	    		while(robot.isMoving()) {}
	    		Delay.msDelay(delayTime);
	    		
	    		//Push package towards wall
	    		robot.goForwardPilot(60);
	    		while (robot.isMoving()){}
	    		Sound.beep();
	    		running = false;
	    		Sound.beep();
	    	}
	    	
	    	start_transition();
	    	
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

	 private void start_transition() {
      robot.goForwardPilot(-5);
      while(robot.isMoving()) {
        
      }
      robot.turnRightPilot(110);
      while(robot.isMoving()) {
        
      }
      robot.goForwardPilot(10);
      while(robot.isMoving()) {
        
      }
      robot.turnLeftPilot(95);
      while(robot.isMoving()) {
        
      }
      robot.goForwardPilot(20);
      while(robot.isMoving()) {
        
      }
      robot.run(new BrueckeFahren(robot));
    }

  @Override
	 public void setRunningState(boolean state) {
	 	// TODO Auto-generated method stub
		running = state;
	 }

}
