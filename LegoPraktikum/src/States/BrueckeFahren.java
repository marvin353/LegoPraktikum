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
	  static int SPEED_FACTOR = 90;
	  private static float abgrund_colorRED = 0.015f;
	  private static float abgrund_color = -1f;
	  
	  private static float distance_to_bridge = 0.13f;
	  private static boolean blueFound = false;
	  int abgrundCount;
	  int turnCount;
	  private boolean running;
	  
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
		  running = true;
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
		  
		  //TODO make sure this works because getColor returns float and Color.BLUE is int
		  while (running == true) {//robot.getSensors().getColor() != Color.BLUE) {
			  float distance =  robot.getSensors().getDistance();
			  float color = robot.getSensors().getColor();
			  float touched1 = robot.getSensors().getTouch1();
			  float touched2 = robot.getSensors().getTouch2();
			  
			  if (robot.getSensors().getColor() == Color.BLUE) {
				 LCD.clear();
				 LCD.drawString("Blue Found", 0, 3);
				 
				 running = false;;
			  }
			  
			  if(color <= abgrund_color) {
				  LCD.drawString("Am Abgrund: " + color , 0, 1);
				 
				  if(testForAbgrund2()) {
					  abgrundFound();
					  ++abgrundCount;
				  }
				  if (abgrundCount == 2) {
					  //robot.LookUp();
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
				  distanceFactorL = -1.0f;
				  distanceFactorR = 5.0f;
			  } else {
				  //distanceFactor = -0.5f;
				  distanceFactorL = 2.9f;
				  distanceFactorR = 2.0f;
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
		  robot.stopLeftMotor(true);
		  robot.stopRightMotor();
		  running = false;
		  robot.run(new FarbfeldFinden(robot));
		  //robot.setColorSensorMode("Red");
		  //Delay.msDelay(3000);
	  }
	  
	  int delayValue = 1000;
	  private boolean testForAbgrund() {
		  robot.stopLeftMotor(true);
		  robot.stopRightMotor(true);
		  //Delay.msDelay(delayValue);
		  //robot.goForwardPilot(-1.5);
		  Delay.msDelay(delayValue);
		  robot.turnLeft(15,true);
		  Delay.msDelay(delayValue);
		  
		  /*if(robot.getSensors().getDistance() > distance_to_bridge 
				  && robot.getSensors().getColor() <= abgrund_color) {
			  robot.turnRight(15,true);
			  Delay.msDelay(delayValue);
			  return true;
		  }*/
		  
		  //if (abgrundCount >= 2 ) return true;
		  
		  if(robot.getSensors().getDistance() > distance_to_bridge) {
			  robot.turnRight(15,true);
			  Delay.msDelay(delayValue);
			  robot.goForwardPilot(-2);
			  //robot.turnLeft(15,true);
			  Delay.msDelay(delayValue);
			  return true;
		  }
		  
		  robot.turnRight(15,true);
		  Delay.msDelay(delayValue);
		  robot.goForwardPilot(7);
		  Delay.msDelay(delayValue);
		  return false;
	  }
	  
	  private boolean testForAbgrund2() {
		  robot.stopLeftMotor(true);
		  robot.stopRightMotor(true);
		  //Delay.msDelay(delayValue);
		  //robot.goForwardPilot(-1.5);
		  Delay.msDelay(delayValue);
		  robot.turnLeft(15,true);
		  Delay.msDelay(delayValue);
		  
		  /*if(robot.getSensors().getDistance() > distance_to_bridge 
				  && robot.getSensors().getColor() <= abgrund_color) {
			  robot.turnRight(15,true);
			  Delay.msDelay(delayValue);
			  return true;
		  }*/
		  
		  //if (abgrundCount >= 2 ) return true;
		  
		  if(robot.getSensors().getDistance() > distance_to_bridge) {
			  //robot.turnRight(15,true);
			  robot.getRightMotor().rotate(200);
			  //Delay.msDelay(delayValue);
			  //robot.goForwardPilot(-1);
			  //robot.turnLeft(15,true);
			  Delay.msDelay(delayValue);
			  robot.getLeftMotor().rotate(200);
			  Delay.msDelay(delayValue);
			  return true;
		  }
		  
		  robot.turnRight(15,true);
		  Delay.msDelay(delayValue);
		  robot.goForwardPilot(7);
		  Delay.msDelay(delayValue);
		  return false;
	  }
	  
	  private boolean abgrundFound() {
		  robot.goForwardPilot(-3);
		  Delay.msDelay(2000);
		  robot.turnLeft(90,true);
		  Delay.msDelay(1500);
		  turnCount++;
		  return true;
	  }
	  
	  private boolean hitWallOnRightSide() {
		  robot.LookUp();
		  robot.goForwardPilot(-6);
		  Delay.msDelay(2000);
		  robot.turnLeft(30,true);
		  Delay.msDelay(1500);
		  return true;
	  }
	  
	  private boolean hitWallOnLeftSide() {
		  robot.LookUp();
		  robot.goForwardPilot(-6);
		  Delay.msDelay(2000);
		  robot.turnRight(30,true);
		  Delay.msDelay(1500);
		  return true;
	  }
	  
	  
	  
	  @Override
	  public void setRunningState(boolean state) {
	    running = state;
	    
	  }
	  
	  

}
