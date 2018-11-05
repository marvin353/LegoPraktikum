package States;

import lejos.utility.Delay;
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
  static int SPEED_FACTOR = 650;
  
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
    
    while(running) {
    	
    	double brightness = robot.getSensors().getColor();
    	
    	int t_count_left_init = 0;
    	int t_count_right_init = 0;
    	
    	if(brightness < 0.1) {
    		count++;
    	} else {
    		t_count_left_init = 0;
    		t_count_right_init = 0;
    		count = 0;
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
    	}
    	
    	double relativeBrightness = (brightness - LIGHT_SENSOR_BLACK_VALUE)/(LIGHT_SENSOR_WHITE_VALUE-LIGHT_SENSOR_BLACK_VALUE);
        
        int speedMotorRight =  (int) ((1-relativeBrightness) * SPEED_FACTOR) - 130;
        int speedMotorLeft = (int) (relativeBrightness * SPEED_FACTOR) - 130;
        
        robot.setLeftMotorSpeed(speedMotorLeft);
        robot.setRightMotorSpeed(speedMotorRight);
        
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
  Boolean lineFound = false;
  int move_count = 0;
  int degree = 10;
  Boolean turnLeft = false;
  
  private void continueOnLineEnd(int t_count_left, int t_count_right) {
	  
	  //Move backward
	  robot.setLeftMotorRotateTo(t_count_left * (-1));
	  robot.setRightMotorRotateTo(t_count_right * (-1));
	  
	  //Find new line
	  while (lineFound == false) {
		  
		  double brightness = robot.getSensors().getColor();
		  
		  if (brightness < 0.6 && brightness > 0.4) {
			  lineFound = true;
		  }
		  
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
		  }
	  }
  }

 
  @Override
  public void setRunningState(boolean state) {
    //running = state;
    
  }

}
