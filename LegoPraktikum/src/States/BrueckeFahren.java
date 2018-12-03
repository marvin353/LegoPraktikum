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
	  static int SPEED_FACTOR = 100;
	  private static float abgrund_color = 0.015f;
	  private static float distance_to_bridge = 0.12f;
	  int abgrundCount;
	  int turnCount;
	  
	  float touched1;
	  float touched2;
	  
	  EV3ColorSensor colorSensor;
	  EV3UltrasonicSensor ulSensor;
	  EV3LargeRegulatedMotor motorRight;
	  EV3LargeRegulatedMotor motorLeft;
	  
	  Robot robot;

	  public BrueckeFahren(Robot robot) {
		  this.robot = robot;
		  abgrundCount = 0;
		  turnCount = 0;
	  }

	  @Override
	  public String getName() {
	    return NAME;
	  }

	  @Override
	  public void onStart() {
		abgrundCount = 0;
		turnCount = 0;
		touched1 = 0;
		touched2 = 0;
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
			  float touched1 = robot.getSensors().getTouch1();
			  float touched2 = robot.getSensors().getTouch2();
			  
			  if(color <= abgrund_color) {
				  LCD.drawString("Am Abgrund: " + color , 0, 1);
				  /*if(abgrundCount >= 1) {
					  abgrundFound();
				  }*/
				  if(testForAbgrund()) {
					  abgrundFound();
					  abgrundCount++;
				  }
				  LCD.drawString("AC:" + abgrundCount , 0, 3);
			  }
			  
			  if (touched1 >= 1) {
				  //Hit the wall
				  hitWallOnLeftSide();
			  }
			  if (touched2 >= 1) {
				  //Hit the wall
				  hitWallOnRightSide();
			  }
			  
			  
			  float distanceFactorL = 0;
			  float distanceFactorR = 0;


			  if (distance > distance_to_bridge) {
				  //distanceFactor = 0.5f;
				  distanceFactorL = 1.0f;
				  distanceFactorR = 4.9f;
			  } else {
				  //distanceFactor = -0.5f;
				  distanceFactorL = 3.9f;
				  distanceFactorR = 1.4f;
			  }
			  
			  int speedMotorLeft =  (int) ((1) * distanceFactorL * SPEED_FACTOR);
		      int speedMotorRight = (int) ((1) * distanceFactorR * SPEED_FACTOR);
			  			  
			  LCD.drawString("Color:" + color , 0, 5);
			  LCD.drawString("Distance: " + distance, 0, 7);
			  
		        		
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
	  
	  int delayValue = 1000;
	  private boolean testForAbgrund() {
		  robot.stopLeftMotor(true);
		  robot.stopRightMotor(true);
		  Delay.msDelay(delayValue);
		  robot.turnLeft(10,true);
		  Delay.msDelay(delayValue);
		  
		  if(robot.getSensors().getDistance() > distance_to_bridge 
				  && robot.getSensors().getColor() <= abgrund_color) {
			  robot.turnRight(10,true);
			  Delay.msDelay(delayValue);
			  return true;
		  }
		  
		  robot.turnRight(10,true);
		  Delay.msDelay(delayValue);
		  robot.goForwardPilot(7);
		  Delay.msDelay(delayValue);
		  return false;
	  }
	  
	  private boolean abgrundFound() {
		  robot.goForwardPilot(-5);
		  Delay.msDelay(2000);
		  robot.turnLeft(90,true);
		  Delay.msDelay(1500);
		  turnCount++;
		  return true;
	  }
	  
	  private boolean hitWallOnRightSide() {
		  robot.goForwardPilot(-5);
		  Delay.msDelay(2000);
		  robot.turnLeft(30,true);
		  Delay.msDelay(1500);
		  return true;
	  }
	  
	  private boolean hitWallOnLeftSide() {
		  robot.goForwardPilot(-5);
		  Delay.msDelay(2000);
		  robot.turnRight(30,true);
		  Delay.msDelay(1500);
		  return true;
	  }
	  
	  
	  
	  @Override
	  public void setRunningState(boolean state) {
	    //running = state;
	    
	  }
	  
	  

}
