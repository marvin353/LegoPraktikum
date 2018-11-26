package States;

import java.io.File;

import LegoPraktikumPackage.Robot;
import lejos.robotics.Color;
import lejos.utility.Delay;
import lejos.hardware.Sound;

public class FarbfeldFinden implements Runnable, ISection {
  
  Robot robot;
  boolean running = true;
  boolean turnLeft = true;
  
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
    // TODO Auto-generated method stub
    
  }

  @Override
  public void run() {
	onStart();
    while(running) {
      robot.goForwardPilot(1000);
      
      while(robot.getSensors().getTouch1() != 1 && robot.getSensors().getTouch1() != 1) {
        if(robot.getSensors().getColor() == Color.RED) {
          Sound.playSample(new File("kit.wav"), 20);
          Delay.msDelay(1000);
        } else if (robot.getSensors().getColor() == Color.WHITE) {
          Sound.playSample(new File("kit.wav"), 20);
          running = false;
          return;
        }
      }
      
      if(turnLeft) {
        robot.turnLeft(90, true);
      } else {
        robot.turnRight(90,true);
      }
      
      Delay.msDelay(1000);
      robot.goForwardPilot(5);
      Delay.msDelay(1000);
      
      if(turnLeft) {
        robot.turnLeft(90, true);
        turnLeft = false;
      } else {
        robot.turnRight(90,true);
        turnLeft = true;
      }
      
      Delay.msDelay(1000);
      
    }
  }

}
