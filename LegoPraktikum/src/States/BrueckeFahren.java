package States;

import LegoPraktikumPackage.Robot;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import LegoPraktikumPackage.Robot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class BrueckeFahren implements Runnable, ISection {
	
	private final String NAME = "Bruecke fahren";
	  static int SPEED_FACTOR = 250;
	  private static float abgrund_color = 0;
	  private static float distance_to_bridge = 0.13f;
	  int abgrundCount;
	  
	  EV3ColorSensor colorSensor;
	  EV3UltrasonicSensor ulSensor;
	  EV3LargeRegulatedMotor motorRight;
	  EV3LargeRegulatedMotor motorLeft;
	  
	  Robot robot;

	  public BrueckeFahren(Robot robot) {
		  this.robot = robot;
		  abgrundCount = 0;
	  }

	  @Override
	  public String getName() {
	    return NAME;
	  }

	  @Override
	  public void onStart() {
		abgrundCount = 0;
		LCD.clear();
		robot.changeSettingsForBridge();
		  //Check if other State thread is running 
	    
	  }

	  @Override
	  public void onEnd() {
	    // TODO Auto-generated method stub
	    
	  }

	  @Override
	  public void run() {
	    onStart();
	    driveOnBridge();
	  }
	  
	  private void driveOnBridge() {
		  Delay.msDelay(1000);
		  //robot.setColorSensorMode("ColorID");
		  robot.LookDown();
		  int i = 0;
		  
		  //TODO make sure this works because getColor returns float and Color.BLUE is int
		  while (i==0) {//robot.getSensors().getColor() != Color.BLUE) {
			  float distance =  robot.getSensors().getDistance();
			  float color = robot.getSensors().getColor();
			  
			  if(color <= abgrund_color) {
				  LCD.drawString("Am Abgrund: " + color , 0, 5);
				  if(abgrundCount == 1) {
					  abgrundFound();
				  }
				  
				  abgrundCount++;
				  LCD.drawString("AC:" + abgrundCount , 0, 3);
			  }
			  
			  float distanceFactor = 0;
			  if (distance > distance_to_bridge) {
				  distanceFactor = 0.7f;
			  } else {
				  distanceFactor = -0.5f;
			  }
			  
			  
			  LCD.drawString("Color:" + color , 0, 5);
			  LCD.drawString("Distance: " + distance, 0, 7);
			  
			  
			  //int speedMotorLeft =  (int) ((0.4 - (distance + distance_to_bridge)) * SPEED_FACTOR);
		      //int speedMotorRight = (int) ((distance + distance_to_bridge) * SPEED_FACTOR);
		        
			    int speedMotorLeft =  (int) ((1) * distanceFactor * SPEED_FACTOR);
		        int speedMotorRight = (int) ((-1) * distanceFactor * SPEED_FACTOR);
		        		
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
		  robot.setColorSensorMode("Red");
		  Delay.msDelay(3000);
	  }
	  
	  private boolean abgrundFound() {
		  robot.goForwardPilot(-5);
		  Delay.msDelay(2000);
		  robot.turnLeft(90,true);
		  Delay.msDelay(1500);
		  return true;
	  }
	  
	  @Override
	  public void setRunningState(boolean state) {
	    //running = state;
	    
	  }
	  
	  

}
