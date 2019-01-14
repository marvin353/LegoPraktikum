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
	  static int SPEED_FACTOR = 55;
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
		  robot.LookDown();
		  
		  //TODO make sure this works because getColor returns float and Color.BLUE is int
		  while (running) {
			  			  
			  float distance =  robot.getSensors().getDistance();
			  float color = robot.getSensors().getColor();
			  float touched1 = robot.getSensors().getTouch1();
			  float touched2 = robot.getSensors().getTouch2();
			  
			  if (robot.getSensors().getColor() == Color.BLUE) {
				 LCD.clear();
				 LCD.drawString("Blue Found", 0, 3);
				 
				 running = false;;
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
				  distanceFactorL = -4.0f;
				  distanceFactorR = 0.0f;
			  } else {
				  distanceFactorL = 2.8f;
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
		  
		  LCD.drawString("BLUE!!!", 0, 6);
		  robot.stopLeftMotor(true);
		  robot.stopRightMotor();
		  running = false;
		  robot.run(new FarbfeldFinden(robot));
	  }
	  
	  int delayValue = 1000;
	 
	  
	  private boolean hitWallOnRightSide() {
		  //robot.LookUp();
		  robot.goForwardPilot(-6);
		  while(robot.isMoving()) {/*Wait*/}
		  //Delay.msDelay(2000);
		  robot.turnLeft(20,true);
		  while(robot.isMoving()) {/*Wait*/}
		  //Delay.msDelay(1500);
		  return true;
	  }
	  
	  private boolean hitWallOnLeftSide() {
		  //robot.LookUp();
		  robot.goForwardPilot(-6);
		  while(robot.isMoving()) {/*Wait*/}
		  //Delay.msDelay(2000);
		  robot.turnRight(20,true);
		  while(robot.isMoving()) {/*Wait*/}
		  //Delay.msDelay(1500);
		  return true;
	  }
	  
	  
	  
	  @Override
	  public void setRunningState(boolean state) {
	    running = state;
	    
	  }
	  
	  

}
