package TestRobo;

import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class TestRoboClass {

	//Hier die Werte für weiß und schwarz ausfüllen 
    static double LIGHT_SENSOR_WHITE_VALUE = 0.5; //typischerweise um die 60
    static double LIGHT_SENSOR_BLACK_VALUE = 0.05; //typischerweise um die 20
    
    //100 heißt 100 Prozent Speed
    static int SPEED_FACTOR = 200;
	
	public static void main(String[] args) {
		
		//Sensors
	    EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
				
		//Motors
		EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);

		System.out.println("Lets go!");
		
		while(true) {
			SensorMode color = colorSensor.getRedMode();
			float[] colorSample = new float[color.sampleSize()];
			color.fetchSample(colorSample, 0);
			
			//readInBrightness soll zurückgeben welche Helligkeit gemessen wurde (soll Reflected Light Intensity von dem Lichtsensor auslesen)
	        double brightness = colorSample[0];
	        
	        //Berechne relative Helligkeit als Wert zwischen 0 und 1
	        double relativeBrightness = (brightness - LIGHT_SENSOR_BLACK_VALUE)/(LIGHT_SENSOR_WHITE_VALUE-LIGHT_SENSOR_BLACK_VALUE);
	        
	        int speedMotorRight =  (int) ((1-relativeBrightness) * SPEED_FACTOR) - 60;
	        int speedMotorLeft = (int) (relativeBrightness * SPEED_FACTOR) - 60;
	        //int speedMotorRight = 30;
	        //int speedMotorLeft = 30;
	        //speedMotorRight =  (int) ((-1) * (1-relativeBrightness) * SPEED_FACTOR);
	        //int speedMotorLeft = (int) ((-1) * relativeBrightness * SPEED_FACTOR);
	        
	        
	        //System.out.println("Brigthness is: " + brightness);
	        
	        //Set Motor Speeds
			motorRight.setSpeed(speedMotorRight);
			motorLeft.setSpeed(speedMotorLeft);
			
			if(speedMotorRight < 0)
				motorRight.forward();
			else motorRight.backward();
			
			if (speedMotorLeft < 0)
				motorLeft.forward();
			else motorLeft.backward();
			
			//System.out.println("Left motor " + speedMotorLeft);
			//System.out.println("Right motor: " + speedMotorRight);
		}
		
	}

}
