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
  int fieldsFound = 0;
  boolean foundRed = false;
  boolean foundWhite = false;
  String sound = "";
  
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
            robot.goForwardPilot(10);
            break;
          }
          
        } else if (robot.getSensors().getColor() == Color.WHITE) {
          if(!foundWhite) {
            foundWhite = true;
            robot.stopLeftMotor(true);
            robot.stopRightMotor(true);
            //Sound.playSample(new File("kit.wav"), 20);
            Sound.beep();
            Delay.msDelay(1000);
            if(foundRed && foundWhite) {
              running = false;
            }
            robot.goForwardPilot(10);
            break;
          }
          
        }
      }
      
      robot.stopLeftMotor(true);
      robot.stopRightMotor(true);
      
      robot.goForwardPilot(-5);
      
      while(robot.isMoving()) {
        if (robot.getSensors().getColor() == Color.WHITE && !foundWhite) {
          Sound.beep();
          foundWhite = true;
        }
        if (robot.getSensors().getColor() == Color.RED && !foundRed) {
          Sound.beep();
          foundRed = true;
        }
      }
      
      if(turnLeft) {
        robot.turnLeftPilot(95);
      } else {
        robot.turnRightPilot(90);
      }
      
      while(robot.isMoving()){
        if (robot.getSensors().getColor() == Color.WHITE && !foundWhite) {
          Sound.beep();
          foundWhite = true;
        }
        if (robot.getSensors().getColor() == Color.RED && !foundRed) {
          Sound.beep();
          foundRed = true;
        }
      }
      
      robot.goForwardPilot(2);
      
      while(robot.isMoving()){
        if (robot.getSensors().getColor() == Color.WHITE && !foundWhite) {
          Sound.beep();
          foundWhite = true;
        }
        if (robot.getSensors().getColor() == Color.RED && !foundRed) {
          Sound.beep();
          foundRed = true;
        }
      }
      
      if(turnLeft) {
        robot.turnLeftPilot(95);
        turnLeft = false;
      } else {
        robot.turnRightPilot(90);
        turnLeft = true;
      }
      
      while(robot.isMoving()){
        if (robot.getSensors().getColor() == Color.WHITE && !foundWhite) {
          Sound.beep();
          foundWhite = true;
        }
        if (robot.getSensors().getColor() == Color.RED && !foundRed) {
          Sound.beep();
          foundRed = true;
        }
      }
      
      if(foundRed && foundWhite) {
        running = false;
      }
      
    }
  }

}
