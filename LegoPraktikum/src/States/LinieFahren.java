package States;

import lejos.utility.Delay;
import LegoPraktikumPackage.ISection;
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
  static int SPEED_FACTOR = 200;
  
  EV3ColorSensor colorSensor;
  EV3LargeRegulatedMotor motorRight;
  EV3LargeRegulatedMotor motorLeft;
  
  Robot robot;

  public LinieFahren(Robot robot) {
	  this.robot = robot;
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public void onStart() {
	
	  //Check if other State thread is running 

	  
	  
    colorSensor = new EV3ColorSensor(SensorPort.S4);
    motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
    motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
    
  }

  @Override
  public void onEnd() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void run() {
    onStart();
    
    while(running) {
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
    }
    
    onEnd();
  }

 
  @Override
  public void setRunningState(boolean state) {
    running = state;
    
  }

}
