package States;

import lejos.utility.Delay;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import LegoPraktikumPackage.Robot;
import Sensors.SingleValueSensorWrapper;

public class LinieFahren implements Runnable, ISection {
  
  private final String NAME = "Linie fahren";
  private static double LIGHT_SENSOR_WHITE_VALUE = 0.5; //typischerweise bum die 60
  private static double LIGHT_SENSOR_BLACK_VALUE = 0.05; //typischerweise um die 20
  static int SPEED_FACTOR = 540;
  private static int treshhold_line_lost = 750;
  
  private static float abgrund_colorRED = 0.015f;
  
  EV3ColorSensor colorSensor;
  EV3LargeRegulatedMotor motorRight;
  EV3LargeRegulatedMotor motorLeft;
  
  boolean obstacleFound = false;
  
  Robot robot;
  private boolean running;

  public LinieFahren(Robot robot) {
	  this.robot = robot;
	  robot.changeSettingsForLineFollower();
	  running = true;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public void onStart() {
	robot.changeSettingsForLineFollower();
	  //Check if other State thread is running 
    
  }

  @Override
  public void onEnd() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void run() {
    onStart();
    Delay.msDelay(2000);
    robot.LookDown();
    //driveParallelToWall();//to test method uncomment this line
    
    boolean blackFound = false;
    int startTachoCountLeft = 0, startTachoCountRight = 0;
    
    while(running) {
    	
    	double brightness = robot.getSensors().getColor();
    	
    	
    	if(brightness <= 0.15) {
    		if (blackFound == false) {
    			startTachoCountLeft = robot.getTachoCountLeftMotor();
        		startTachoCountRight = robot.getTachoCountRightMotor();
        		
        		blackFound = true;
    		} else {
    			int curTachoCountLeft = robot.getTachoCountLeftMotor();
    			int curTachoCountRight = robot.getTachoCountRightMotor();
    			
    			int deltaLeft = curTachoCountLeft - startTachoCountLeft;
    			int deltaRight = curTachoCountRight - startTachoCountRight;
    			
    			//LCD.drawString("D l:" + deltaLeft, 0, 5);
    			LCD.drawString("D r:" + deltaRight, 0, 5);
    			
    			if (Math.abs(deltaLeft) >= treshhold_line_lost || Math.abs(deltaRight) >= treshhold_line_lost) {
    				
    				continueOnLineEnd(deltaLeft, deltaRight);
    			}
    		}    		
    	} else {
    		blackFound = false;
    	}
    	
    	if (robot.getSensors().getDistance() > 0.13f && obstacleFound == false) {
    		robot.stopLeftMotor(true);
    		robot.stopRightMotor();
    		robot.goForwardPilot(-7);
    		while(robot.isMoving()) {/*Wait*/}
    		robot.turnRight(210);
    		while(robot.isMoving()) {/*Wait*/}
    		robot.goForwardPilot(3);
    		while(robot.isMoving()) {/*Wait*/}
    	}
    	
    	//hardcoded obstacle detection
    	if(robot.getSensors().getTouch1() >= 1 || robot.getSensors().getTouch2() >= 1) {

    		robot.goForwardPilot(5);
    		robot.turnRight(100);
    		LCD.clearDisplay();
    		LCD.drawString("Linie suchen", 0, 5);
        

    	    robot.getMediumMotor().rotateTo(170);
    	    if (robot.getSensors().getDistance() > 40) robot.getMediumMotor().rotateTo(173);

    	    Delay.msDelay(2000);
    		LCD.drawString("Look left", 0, 7);
    		Delay.msDelay(1000);
        
    		
    		int curveFactor = 90;
    		robot.goForwardPilot(4);
    		while(robot.isMoving()) {}
    		int startRightMotor = robot.getTachoCountRightMotor();
    		
	        while (robot.getSensors().getColor() <= 0.15) {
	        	if(running == false) return;
	            float distance =  robot.getSensors().getDistance();
	          
	            LCD.drawString("Distance: " + distance, 0, 2);
	            if(distance >0.35) distance = 0.35f;
	            if(robot.getTachoCountRightMotor() - startRightMotor >=20) curveFactor = 0;
	
	            int speedMotorLeft =  (int) ((0.35-distance) * SPEED_FACTOR*0.8) + curveFactor;
	            int speedMotorRight = (int) (distance * SPEED_FACTOR*0.8) + curveFactor;
	              
	            robot.setLeftMotorSpeed(Math.abs(speedMotorLeft));
	            robot.setRightMotorSpeed(Math.abs(speedMotorRight));
	              
	            if(speedMotorRight < 0)
	                robot.setRightMotorGoForward();
	            else robot.setRightMotorGoBackward();
	              
	            if (speedMotorLeft < 0)
	                robot.setLeftMotorGoForward();
	            else robot.setLeftMotorGoBackward();
	        }
        
        	LCD.clearDisplay();
        	obstacleFound = true;
        
    	} 
    	
    	brightness = robot.getSensors().getColor();
    	double relativeBrightness = (brightness - LIGHT_SENSOR_BLACK_VALUE)/(LIGHT_SENSOR_WHITE_VALUE-LIGHT_SENSOR_BLACK_VALUE);
        
        int speedMotorRight =  (int) ((1-relativeBrightness) * SPEED_FACTOR) - 100;
        int speedMotorLeft = (int) (relativeBrightness * SPEED_FACTOR) - 100;
        
        robot.setLeftMotorSpeed(Math.abs(speedMotorLeft));
        robot.setRightMotorSpeed(Math.abs(speedMotorRight));
        
        if(speedMotorRight < 0)
          robot.setRightMotorGoForward();
        else robot.setRightMotorGoBackward();
        
        if (speedMotorLeft < 0)
        	robot.setLeftMotorGoForward();
        else robot.setLeftMotorGoBackward();
    	
    }
    
    //onEnd();
  }
  
  int count;
  boolean lineFound = false;
  int move_count = 0;
  int degree = 10;
  boolean turnLeft = false;
  
  private void continueOnLineEnd(int t_count_left, int t_count_right) {
	  
	  int leftMotorTachoCount = 0;
	  int rightMotorTachoCount = 0;
	  robot.stopLeftMotor(true);
	  robot.stopRightMotor(true);
	  LCD.drawString("Line End!", 0, 5);
	  Delay.msDelay(50);
	  
	  if(robot.getSensors().getColor() >= 0.15) return;
	  
	  //Move to position before line was lost
	  robot.setLeftMotorRotateAsync(t_count_left * (-1));
	  robot.setRightMotorRotateAsync(t_count_right * (-1));
	  
	  
	  
	  int stage = 2;//encodes if we are turning right, left, or second time right
	  while (robot.isMoving()){
		  
	  }
	  
	  LCD.drawString("Start Pos", 0, 5);
	  Delay.msDelay(50);
	  LCD.drawString("Search Line!", 0, 5);
	  Delay.msDelay(50);
	  //robot.goForwardByDegree(80);
	  //robot.goForwardPilot(10);
	  while (robot.isMoving()) {}
	  robot.goForwardPilot(1);
	  while (robot.isMoving()) {}
	  //Find new line
	  int delayValue = 50;
	  while (robot.getSensors().getColor() < 0.2) {
		  if(running == false) return;
		  /*
		  double brightness = robot.getSensors().getColor();
		  if (brightness > 0.3) {
			  lineFound = true;
			  LCD.drawString("Line Found!", 0, 5);
			  Delay.msDelay(2000);
		  }*/
		   //Search for line
/*		  if (stage == -1) {
				  LCD.drawString("go back", 0, 5);
				  Delay.msDelay(delayValue);
				  //Rotate 100 degrees to right
				  robot.goForwardPilot(-5);
				  stage++;				  
			  }	  
*/		  
			  /*
             if (stage == 0) {
				  LCD.drawString("turn right", 0, 5);
				  Delay.msDelay(delayValue);
				  //Rotate 100 degrees to right
				  robot.turnRight(130, true);
				  stage++;				  
			  }
			  
			  else if(stage == 1) {
				  if(!robot.isMoving()) {
					  stage++;
					  robot.stopLeftMotor(true);
					  robot.stopRightMotor(true);
					  LCD.drawString("finished turn", 0, 5);
					  Delay.msDelay(delayValue);
				  }
					  
			  }
			  
			  else */if (stage == 2) {
				  LCD.drawString("turn right", 0, 5);
				  Delay.msDelay(delayValue);
				  //rotate 200 degrees to left
				  robot.turnRight(120, true);
				  stage++;
			  }
			  else if(stage == 3) {
				  if(!robot.isMoving())
				  {
					  stage++;
					  LCD.drawString("finished", 0, 5);
					  Delay.msDelay(delayValue);
				  }
					  
			  }			  
			  else if(stage == 4) {
				  LCD.drawString("turn left", 0, 5);
				  Delay.msDelay(delayValue);
				 //Rotate 100 to right 
				  robot.turnLeft(120, true);
					  stage++;
			  }
			  else if(stage == 5) {
				  if(!robot.isMoving())
				  {
					  stage++;
					  LCD.drawString("finished", 0, 5);
					  Delay.msDelay(delayValue);
				  }
			  }
			  
			  //Go Forward
			  else if(stage == 6) {
				  if (obstacleFound) {
					  driveParallelToWall();
					  return;
				  }
				  
				  LCD.drawString("forward", 0, 5);
				  Delay.msDelay(delayValue);
				  //Move a bit forward
				  /*robot.setLeftMotorSpeed(100);
				  robot.setRightMotorSpeed(100);
				  robot.setLeftMotorGoBackward();
				  robot.setRightMotorGoBackward();*/
				  robot.goForwardPilot(12);
				  //leftMotorTachoCount = robot.getLeftMotor().getTachoCount();
				  //rightMotorTachoCount = robot.getRightMotor().getTachoCount();
				  stage ++;
				  
			  }
			  
			  //if finished moving go to next stage
			  else if (stage == 7) {
				  if(!robot.isMoving()) stage ++;
				  /*int leftDelta = Math.abs(robot.getLeftMotor().getTachoCount() - leftMotorTachoCount);
				  int rightDelta = Math.abs(robot.getRightMotor().getTachoCount() - rightMotorTachoCount);
				  if(leftDelta >= 300 || rightDelta >= 300) {
					  robot.stopLeftMotor();
					  robot.stopRightMotor();
					  stage ++;
					  LCD.drawString("Stop!", 0, 5);
					  Delay.msDelay(delayValue);
				  }	*/		  
			  }
			  
			  //Turn a bit left
			  else if(stage == 8) {
				  robot.turnLeft(65, true);		
				  stage++;
			  }		
			  else if(stage == 9) {
				  if(!robot.isMoving()) stage ++;
			  }
			  else if(stage == 10) {
				  robot.turnRight(110, true);
				  stage++;
			  }
			  else if(stage == 11) {
				  if(!robot.isMoving()) stage ++;
			  }
			  else if(stage == 12) {
				  robot.turnLeft(65, true);
				  stage++;
			  }
			  
			  //Now go back to stage 6, and travel forward again
			  else if(stage == 13) {
				  if(!robot.isMoving()) stage = 6;
			  }
		  	  
	  }
	  robot.stopLeftMotor(true);
	  robot.stopRightMotor();
  }

 
  @Override
  public void setRunningState(boolean state) {
    running = state;
    
  }
  
  //TODO Test this Method
  private void driveParallelToWall() {	  
	  //robot.LookLeft(); //Annahme: er guckt hir schon nach links
	  //Delay.msDelay(1000);
  	LCD.clearDisplay();
  	LCD.drawString("Drive Parallel to Wall", 1, 1);

	  robot.setColorSensorMode("ColorID");
	  
	  //TODO make sure this works because getColor returns float and Color.BLUE is int
	  //I feel like it works (not sure)
	  while (robot.getSensors().getColor() != Color.BLUE) {
		  if(running == false) return;
		  float distance =  robot.getSensors().getDistance();
		  
		  LCD.drawString("Distance: " + distance, 0, 5);
		  
		  
		  int speedMotorLeft =  (int) ((0.44-distance) * SPEED_FACTOR)-20;
	      int speedMotorRight = (int) (distance * SPEED_FACTOR)-20;
	        
	        robot.setLeftMotorSpeed(Math.abs(speedMotorLeft));
	        robot.setRightMotorSpeed(Math.abs(speedMotorRight));
	        
	        if(speedMotorRight < 0)
	          robot.setRightMotorGoForward();
	        else robot.setRightMotorGoBackward();
	        
	        if (speedMotorLeft < 0)
	        	robot.setLeftMotorGoForward();
	        else robot.setLeftMotorGoBackward();
	  }
	  
	  LCD.drawString("BLUE!!!", 0, 5);
	  robot.stopLeftMotor(true);
	  robot.stopRightMotor();
	  robot.goForwardPilot(8);
	  while(robot.isMoving()) {}
	  /*
	  robot.setLeftMotorSpeed(200);
	  robot.setRightMotorSpeed(200);
	  robot.setRightMotorGoBackward();
	  robot.setLeftMotorGoBackward();
	  Delay.msDelay(100);*/
	  robot.setColorSensorMode("Red");
	  //Next state
	  LCD.clear();
	  LCD.drawString("Paket Liefern!", 1, 1);
	  running=false;
	  robot.run(new PaketLiefern(robot));
  }

}
