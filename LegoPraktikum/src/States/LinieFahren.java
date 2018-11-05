package States;

import lejos.utility.Delay;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

import LegoPraktikumPackage.Robot;

public class LinieFahren implements Runnable, ISection {
  
  private final String NAME = "Linie fahren";
  private static double LIGHT_SENSOR_WHITE_VALUE = 0.5; //typischerweise bum die 60
  private static double LIGHT_SENSOR_BLACK_VALUE = 0.05; //typischerweise um die 20
  static int SPEED_FACTOR = 540;
  
  EV3ColorSensor colorSensor;
  EV3LargeRegulatedMotor motorRight;
  EV3LargeRegulatedMotor motorLeft;
  
  Robot robot;

  public LinieFahren(Robot robot) {
	  this.robot = robot;
	  robot.changeSettingsForLineFollower();
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public void onStart() {
	
	  //Check if other State thread is running 
    
  }

  @Override
  public void onEnd() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void run() {
    onStart();
    
   /* while() {
      SensorMode color = colorSensor.getRedMode();
      float[] colorSample = new float[color.sampleSize()];
      color.fetchSample(colorSample, 0);
      
      double brightness = colorSample[0];
          
      double relativeBrightness = (brightness - LIGHT_SENSOR_BLACK_VALUE)/(LIGHT_SENSOR_WHITE_VALUE-LIGHT_SENSOR_BLACK_VALUE);
          
      int speedMotorRight =  (int) ((1-relativeBrightness) * SPEED_FACTOR) - 60;
      int speedMotorLeft = (int) (relativeBrightness * SPEED_FACTOR) - 60;
      
      motorRight.setSpeed(speedMotorRight);
      motorLeft.setSpeed(speedMotorLeft);
      
      if(speedMotorRight < 0)
        motorRight.forward();
      else motorRight.backward();
      
      if (speedMotorLeft < 0)
        motorLeft.forward();
      else motorLeft.backward();
    }*/
    boolean blackFound = false;
    int startTachoCountLeft = 0, startTachoCountRight = 0; 
    while(running) {
    	
    	double brightness = robot.getSensors().getColor();
    	
    	
    	if(brightness <= 0.1) {
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
    			
    			if (Math.abs(deltaLeft) >= 300 || Math.abs(deltaRight) >= 300) {
    				continueOnLineEnd(deltaLeft, deltaRight);
    			}
    		}    		
    	} else {
    		blackFound = false;
    	}
    	
    	//hardcoded obstacle detection
    	if(robot.getSensors().getTouch1() >= 1 || robot.getSensors().getTouch2() >= 1) {
    	  //touch left or right
    	  int initTachoCountLeft = robot.getTachoCountLeftMotor();
        int initTachoCountRight = robot.getTachoCountRightMotor();
        int deltaLeft = 0, deltaRight = 0;
        
        while(Math.abs(deltaRight) <= 280) {
          robot.stopLeftMotor();
          robot.setRightMotorSpeed(200);
          
          robot.setRightMotorGoForward();
          
          deltaLeft = robot.getTachoCountLeftMotor() - initTachoCountLeft;
          deltaRight = robot.getTachoCountRightMotor() - initTachoCountRight;
          LCD.drawString("Zurück LR: " + deltaLeft + " " + deltaRight, 0, 5);
        }
        initTachoCountLeft = robot.getTachoCountLeftMotor();
        initTachoCountRight = robot.getTachoCountRightMotor();
        deltaLeft = 0;
        deltaRight = 0;
        while(Math.abs(deltaLeft) <= 720 || Math.abs(deltaRight) <= 720) {
          robot.setLeftMotorSpeed(200);
          robot.setRightMotorSpeed(200);
          
          robot.setRightMotorGoBackward();
          robot.setLeftMotorGoBackward();
          
          deltaLeft = robot.getTachoCountLeftMotor() - initTachoCountLeft;
          deltaRight = robot.getTachoCountRightMotor() - initTachoCountRight;
          LCD.drawString("Vorwärts LR: " + deltaLeft + " " + deltaRight, 0, 5);

        }
        initTachoCountLeft = robot.getTachoCountLeftMotor();
        initTachoCountRight = robot.getTachoCountRightMotor();
        deltaLeft = 0;
        deltaRight = 0;
        while(Math.abs(deltaLeft) <= 280) {
          robot.stopRightMotor();
          robot.setLeftMotorSpeed(200);
          
          robot.setLeftMotorGoForward();
          
          deltaLeft = robot.getTachoCountLeftMotor() - initTachoCountLeft;
          deltaRight = robot.getTachoCountRightMotor() - initTachoCountRight;
          LCD.drawString("Zurück LR: " + deltaLeft + " " + deltaRight, 0, 5);
        }
        LCD.drawString("Linie suchen", 0, 5);
        while(brightness <= 0.1) {
          robot.setLeftMotorSpeed(200);
          robot.setRightMotorSpeed(200);
          
          robot.setRightMotorGoBackward();
          robot.setLeftMotorGoBackward();
        }
        LCD.clearDisplay();
        
    	} 
    	/*
    	//int t_count_left_init = 0;
    	//int t_count_right_init = 0;
    	
    	if(brightness < 0.1) {
    		//robot.getLeftMotor().resetTachoCount();
    		//robot.getRightMotor().resetTachoCount();
    		//count++;
    	} else {
    		//t_count_left_init = 0;
    		//t_count_right_init = 0;
    		//count = 0;
    	}
    	
        if (count >= 100) {  //Keine Ahnung welcher Wert hier gut ist
    		//hier zählen anfangen
        	System.out.println("Count >= 100");
        	t_count_left_init = robot.getTachoCountLeftMotor();
        	t_count_right_init = robot.getTachoCountRightMotor();
        	
    	}
    	
    	if (count >= 1000) {  //Keine Ahnung welcher Wert hier gut ist
    		
    		System.out.println("Continue on Line End");
    		int t_count_left = robot.getTachoCountLeftMotor();
        	int t_count_right = robot.getTachoCountRightMotor();
    		continueOnLineEnd(t_count_left - t_count_left_init, t_count_right - t_count_right_init);
    	} */
    	
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
	  robot.stopLeftMotor();
	  robot.stopRightMotor();
	  LCD.drawString("Line End!", 0, 5);
	  Delay.msDelay(2000);
	  
	  //Move to position before line was lost
	  robot.setLeftMotorRotate(t_count_left * (-1));
	  robot.setRightMotorRotate(t_count_right * (-1));
	  
	  int stage= 0;//encodes if we are turning right, left, or sencond time right
	  
	  //Find new line
	  while (lineFound == false) {
		  
		  double brightness = robot.getSensors().getColor();
		  
		  if ( brightness > 0.3) {
			  lineFound = true;
		  }
		  else { //Search for line
			  
			  if (stage == 0) {
				  //Rotate 100 degrees to right
				  stage++;
			  }
			  
			  else if(stage == 1) {
				  if(!robot.isMoving())
					  stage++;
			  }
			  
			  else if (stage == 2) {
				  //rotate 200 degrees to left
				  
					  stage++;
			  }
			  else if(stage == 3) {
				  if(!robot.isMoving())
					  stage++;
			  }
			  
			  if(stage == 4) {
				 //Rotate 100 to right 
				  if(!robot.isMoving())
					  stage++;
			  }
			  else if(stage == 5) {
				  if(!robot.isMoving())
					  stage++;
			  }
			  
			  else if(stage == 6) {
				  //Move a bit forward
				  robot.setLeftMotorSpeed(100);
				  robot.setRightMotorSpeed(100);
				  robot.setLeftMotorGoBackward();
				  robot.setRightMotorGoBackward();
				  leftMotorTachoCount = robot.getLeftMotor().getTachoCount();
				  rightMotorTachoCount = robot.getRightMotor().getTachoCount();
				  stage ++;
				  
			  }
			  if (stage == 7) {
				//TODO Check how much moved forward with tacho and then change stage
				  int leftDelta = Math.abs(robot.getLeftMotor().getTachoCount() - leftMotorTachoCount);
				  if(leftDelta == 200) {
					  
				  }
				  stage = 0;
			  }
			  
		  }
		  /*
		  if (move_count == 100) {
			  if (turnLeft == true) {
				  robot.setLeftMotorRotateTo(degree * move_count * (-1));
			  } else {
				  robot.setRightMotorRotateTo(degree * move_count * (-1));
			  }
		  } else {
			  if (turnLeft == true) {
				  move_count++;
				  robot.setLeftMotorRotateTo(degree);
			  } else {
				  move_count++;
				  robot.setRightMotorRotateTo(degree);
			  }
		  } */
	  }
  }

 
  @Override
  public void setRunningState(boolean state) {
    //running = state;
    
  }

}
