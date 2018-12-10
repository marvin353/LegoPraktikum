package States;

import java.io.File;

import LegoPraktikumPackage.Robot;
import lejos.robotics.Color;
import lejos.utility.Delay;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;


public class FarbfeldFinden implements Runnable, ISection {
  
  Robot robot;
  boolean running = true;
  boolean turnLeft = true;
  int fieldsFound = 0;
  boolean foundRed = false;
  boolean foundWhite = false;
  String sound = "";
  
  public FarbfeldFinden(Robot robot) {
    
    this.robot = robot;
    
    
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
    running = state;
    
  }

  @Override
  public void run() {
	onStart();
    while(running) {
      robot.setLeftMotorSpeed(250);
      robot.setRightMotorSpeed(250);
      robot.setLeftMotorGoBackward();
      robot.setRightMotorGoBackward();
      
      while(robot.getSensors().getTouch1() != 1 && robot.getSensors().getTouch1() != 1) {
        LCD.drawString(String.valueOf(robot.getSensors().getColor()), 0, 5);
        if(robot.getSensors().getColor() == Color.RED) {
          if(!foundRed) {
            foundRed = true;
            robot.stopLeftMotor(true);
            robot.stopRightMotor(true);
            //Sound.playSample(new File("kit.wav"), 20);
            Sound.beep();
            Delay.msDelay(1000);
            if(foundRed && foundWhite) {
              running = false;
            }
          }
          
        } else if (robot.getSensors().getColor() == Color.WHITE) {
          if(!foundWhite) {
            foundWhite = true;
            robot.stopLeftMotor(true);
            robot.stopRightMotor(true);
            //Sound.playSample(new File("kit.wav"), 20);
            Sound.beep();
            if(foundRed && foundWhite) {
              running = false;
            }
          }
          
        }
      }
      
      robot.goForwardPilot(-5);
      
      while(robot.isMoving()) {
        
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
        robot.turnLeft(100, true);
        turnLeft = false;
      } else {
        robot.turnRight(100,true);
        turnLeft = true;
      }
      
      while(robot.isMoving()){
        
      }
      
    }
  }

}
