package States;

import java.io.File;

import LegoPraktikumPackage.Robot;
import lejos.robotics.Color;
import lejos.utility.Delay;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;


public class FarbfeldFinden implements Runnable, ISection {
  
  Robot robot;
  boolean running;
  boolean turnLeft = true;
  
  public FarbfeldFinden(Robot robot) {
    
    this.robot = robot;
    running = true;
    
  }

  @Override
  public String getName() {
    return "FarbfeldFinden";
  }

  @Override
  public void onStart() {
    robot.changeSettingsForFarbfeldFinden();
    Sound.setVolume(20);
    
  }

  @Override
  public void onEnd() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setRunningState(boolean state) {
    // TODO Auto-generated method stub
    running=state;
  }

  @Override
  public void run() {
	onStart();
    while(running) {
      robot.setLeftMotorSpeed(200);
      robot.setRightMotorSpeed(200);
      robot.setLeftMotorGoBackward();
      robot.setRightMotorGoBackward();
      
      while(robot.getSensors().getTouch1() != 1 && robot.getSensors().getTouch1() != 1) {
        LCD.drawString(String.valueOf(robot.getSensors().getColor()), 0, 5);
        if(robot.getSensors().getColor() == Color.RED) {
          robot.stopLeftMotor(true);
          robot.stopRightMotor(true);
          Sound.playSample(new File("kit.wav"), 20);
          Delay.msDelay(1000);
        } else if (robot.getSensors().getColor() == Color.WHITE) {
          robot.stopLeftMotor(true);
          robot.stopRightMotor(true);
          Sound.playSample(new File("kit.wav"), 20);
          running = false;
          return;
        }
      }
      
      if(turnLeft) {
        robot.turnLeft(90, true);
      } else {
        robot.turnRight(90, true);
      }
      
      while(robot.isMoving()){
        
      }
      
      robot.goForwardPilot(5);
      
      while(robot.isMoving()){
        
      }
      
      if(turnLeft) {
        robot.turnLeft(90, true);
        turnLeft = false;
      } else {
        robot.turnRight(90,true);
        turnLeft = true;
      }
      
      while(robot.isMoving()){
        
      }
      
    }
  }

}
