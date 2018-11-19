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
	  static int SPEED_FACTOR = 540;
	  private static float abgrund_color = 0;
	  private static float distance_to_bridge = 0.13f;
	  
	  EV3ColorSensor colorSensor;
	  EV3UltrasonicSensor ulSensor;
	  EV3LargeRegulatedMotor motorRight;
	  EV3LargeRegulatedMotor motorLeft;
	  
	  Robot robot;

	  public BrueckeFahren(Robot robot) {
		  this.robot = robot;
		  
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
	    driveOnBridge();
	  }
	  
	  private void driveOnBridge() {
		  Delay.msDelay(1000);
		  robot.setColorSensorMode("ColorID");
		  robot.LookDown();
		  
		  //TODO make sure this works because getColor returns float and Color.BLUE is int
		  while (robot.getSensors().getColor() != Color.BLUE) {
			  float distance =  robot.getSensors().getDistance();
			  float color = robot.getSensors().getColor();
			  
			  if(color == abgrund_color) {
				  LCD.drawString("Am Abgrund: " + color , 0, 5);
				  abgrundFound();
			  }
			  
			  
			  LCD.drawString("Distance: " + distance, 0, 5);
			  
			  
			  int speedMotorLeft =  (int) ((0.60-distance) * SPEED_FACTOR);
		      int speedMotorRight = (int) (distance * SPEED_FACTOR);
		        
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
	  
	  private void abgrundFound() {
		  robot.goForwardPilot(10);
		  robot.turnLeftPilot(90);
	  }
	  
	  @Override
	  public void setRunningState(boolean state) {
	    //running = state;
	    
	  }
	  
	  

}
