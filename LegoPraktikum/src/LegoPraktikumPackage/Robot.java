package LegoPraktikumPackage;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

import Sensors.SensorThread;
import Sensors.SingleValueSensorWrapper;

import java.io.File;

import Helpers.EV3Menu;
import States.ISection;
import States.LinieFahren;



public class Robot {
	
	// Date need to be measured and adjusted
		//private final double WHEEL_DIAMETER = 3.2d; // unit:cm
		//private final double TRACK_WIDTH = 11d; // unit: cm
		private final Port LEFT_MOTOR = MotorPort.B;
		private final Port MEDIUM_MOTOR = MotorPort.C;
		private final Port RIGHT_MOTOR = MotorPort.A;

		//TODO Ueberall Touch Sensor 1 und 2 in Right und Left umbennenen!!!
		private static final Port COLOR_SENSOR = SensorPort.S4;
		private static final Port TOUCH_SENSOR_1 = SensorPort.S3; //Left
		private static final Port TOUCH_SENSOR_2 = SensorPort.S2; //Right
		private static final Port ULTRASONIC_SENSOR = SensorPort.S1;

		private SensorThread sensors;
		private final EV3ColorSensor colorS = new EV3ColorSensor(COLOR_SENSOR);
		private final EV3TouchSensor touchS1 = new EV3TouchSensor(TOUCH_SENSOR_1);
		private final EV3TouchSensor touchS2 = new EV3TouchSensor(TOUCH_SENSOR_2);
		private final EV3UltrasonicSensor ultraS = new EV3UltrasonicSensor(ULTRASONIC_SENSOR);
		
		private EV3Menu menu;
		
		private EV3LargeRegulatedMotor leftMotor;
		private EV3LargeRegulatedMotor rightMotor;
		private EV3MediumRegulatedMotor mediumMotor;
		
		private static DifferentialPilot pilot;		


		//private Drive drive;
		private LinieFahren linieFahren; 
		private ISection currentSection;
		private Thread sectionThread;

		public Robot() {

			this.leftMotor = new EV3LargeRegulatedMotor(LEFT_MOTOR);
			this.rightMotor = new EV3LargeRegulatedMotor(RIGHT_MOTOR);
			this.mediumMotor = new EV3MediumRegulatedMotor(MEDIUM_MOTOR);

			SingleValueSensorWrapper color = new SingleValueSensorWrapper(colorS, "Red");
			SingleValueSensorWrapper touch1 = new SingleValueSensorWrapper(touchS1, "Touch");
			SingleValueSensorWrapper touch2 = new SingleValueSensorWrapper(touchS2, "Touch");
			SingleValueSensorWrapper distance = new SingleValueSensorWrapper(ultraS, "Distance");
			
			pilot = new DifferentialPilot(6.88f, 12.0f, leftMotor, rightMotor);	
			//pilot.setRotateSpeed(30);
			//pilot.setTravelSpeed(50);

			this.sensors = new SensorThread(color, touch1, touch2, distance);
			new Thread(this.sensors).start();
			
			this.menu = new EV3Menu();
			//this.drive = new Drive(this);
			
			//changeSettingsForLineFollower();
			//this.linieFahren = new LinieFahren(this);
			//linieFahren.run();
			
		}
		
		//Possible Modes: "Red", "Color ID", "RGB"
		public void setColorSensorMode(String mode) {
			this.sensors.setColorSensor(new SingleValueSensorWrapper(colorS, mode));
		}
		
		public void mainLoop() throws InterruptedException {
			//// new Thread(this.sensors).start();
			//Button.LEDPattern(1); // green light
			//this.missionMenu.startGUI(this); // show menu on the brick's screen
			System.exit(0);
		}
		
		public void changeSettingsForLabyrinth() {
			//this.sensors.setsColor(new SingleValueSensorWrapper(colorS, 2));
//			//this.colorS.setCurrentMode("Color ID");
		}
		
		public void changeSettingsForLineFollower() {
			this.sensors.setColorSensor(new SingleValueSensorWrapper(colorS, "Red"));
		}
		
		public void changeSettingsForFarbfeldFinden() {
			this.sensors.setColorSensor(new SingleValueSensorWrapper(colorS, "ColorID"));
		}
		
		public void changeSettingsForBridge() {
			this.sensors.setColorSensor(new SingleValueSensorWrapper(colorS, "Red"));
		}
		
		public void changeSettingsForPackageDelivery() {
			
		}
		
		/*
		public int getColorID() {
			//return this.colorS.getColorID();
			return 0;
		}*/
		
		
		
		
		//Set/Run State
		public void run(Runnable section)
		{
			if(sectionThread != null) {
				sectionThread.stop();
			}
			sectionThread = new Thread(section);
			sectionThread.run();
		}
		
		//Drive
		public void setLeftMotorSpeed(int motorSpeed) {
		    leftMotor.setSpeed(motorSpeed);
		}
		
		public void setRightMotorSpeed(int motorSpeed) {
			rightMotor.setSpeed(motorSpeed);
		}
		
		public void setLeftMotorGoForward() {
			leftMotor.forward();
		}
		
		public void setRightMotorGoForward() {
			rightMotor.forward();
		}
		
		public void setLeftMotorGoBackward() {
			leftMotor.backward();
		}
		
		public void setRightMotorGoBackward() {
			rightMotor.backward();
		}
		
		public void setLeftMotorRotate(int angle) {
			leftMotor.stop();
			leftMotor.rotate(angle);
		}
		
		public void setRightMotorRotate(int angle) {
			rightMotor.stop();
			rightMotor.rotate(angle);
		}
		
		public void setLeftMotorRotateAsync(int angle) {
			leftMotor.stop(true);
			leftMotor.rotate(angle,true);
		}
		
		public void setRightMotorRotateAsync(int angle) {
			rightMotor.stop(true);
			rightMotor.rotate(angle,true);
		}
		
		public int getTachoCountLeftMotor() {
			return leftMotor.getTachoCount();
		}
		
		public int getTachoCountRightMotor() {
			return rightMotor.getTachoCount();
		}
		
		public void travelArc(double radius, double distance) {
		  pilot.travelArc(radius * 2, distance * 2 * (-1), true);
		}
	
		public void stopRightMotor() {
			rightMotor.stop();
		}
		
		public void stopLeftMotor() {
			leftMotor.stop();
		}
		
		public void stopRightMotor(boolean async) {
			rightMotor.stop(async);
		}
		
		public void stopLeftMotor(boolean async) {
			leftMotor.stop(async);
		}
		
		double factor = 3.4;
		double factorP = 2;
		double distanceFactor = 2;
		
		public void turnLeft(int degree) {
			rightMotor.stop(true);
			leftMotor.stop(true);
			
			int left = (int)(degree * factor);
			int right = (int)(degree * factor * (-1));

			while (this.isMoving()) {}

			
			int tcR_init = rightMotor.getTachoCount();
			int tcL_init = leftMotor.getTachoCount();

					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left);
			
			/*TODO Test
			while (Math.abs(leftMotor.getTachoCount() - tcL_init) <= Math.abs(left) || Math.abs(rightMotor.getTachoCount() - tcR_init) <= Math.abs(right)) {
				//wait
			}*/
			
		}
		
		public void turnRight(int degree) {
			rightMotor.stop(true);
			leftMotor.stop(true);
			
			int right = (int)(degree * factor);
			int left = (int)(degree * factor * (-1));
			while (this.isMoving()) {}
					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left);
		}
		
		public void turnRight(int degree, boolean async) {
			turnRightPilot(degree);
			/*rightMotor.stop(true);
			leftMotor.stop(true);
			
			int right = (int)(degree * factor);
			int left = (int)(degree * factor * (-1));
			while (this.isMoving()) {} //TEST
					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left, true);*/
		}
		
		public void turnLeft(int degree, boolean async) {
			turnLeftPilot(degree);
			/*rightMotor.stop(true);
			leftMotor.stop(true);
			
			int left = (int)(degree * factor);
			int right = (int)(degree * factor * (-1));
			while (this.isMoving()) {} //TEST
					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left, true);*/
		}
		
		public void turnRightPilot(int degree) {
			pilot.rotate(degree * factorP,true);
		}
		
		public void turnLeftPilot(int degree) {
			pilot.rotate(degree * (-1) * factorP,true);
		}
		
		public void goForwardPilot(double distance) {
			pilot.travel(distance * distanceFactor * (-1), true);
		}
		
		public void goForwardByDegree(int degree) {
			rightMotor.stop(true);
			leftMotor.stop(true);
			
			int right = (int)(degree * factor * (-1));
			int left = (int)(degree * factor * (-1));
					
			int tcR_init = rightMotor.getTachoCount();
			int tcL_init = leftMotor.getTachoCount();
			
			rightMotor.rotate(right, true);
			leftMotor.rotate(left, true);
			
			while (Math.abs(leftMotor.getTachoCount() - tcL_init) <= Math.abs(degree) || Math.abs(rightMotor.getTachoCount() - tcR_init) <= Math.abs(degree)) {
				//wait
			}
		}
		
		//Medium Motor Controlls
		public void LookLeft() {
			mediumMotor.rotateTo(0);
			mediumMotor.rotateTo(0, true);
		}
		
		public void LookRight() {
			mediumMotor.rotateTo(180);
		}
		
		public void LookDown() {
			mediumMotor.rotateTo(0);
			mediumMotor.rotateTo(-89);
		}
		
		public void LookDownPaket() {
			mediumMotor.rotateTo(0);
			mediumMotor.rotateTo(-10); //Something between 10 and 15
		}
		
		//Sound
		public void PlaySoundSample() {
			Sound.playSample(new File("imperial_march.wav"), Sound.VOL_MAX);
		}
		
		
		
		// Getter und Setter
		public SensorThread getSensors() {
			return sensors;
		}

		public void setSensors(SensorThread sensors) {
			this.sensors = sensors;
		}

		public EV3Menu getMissionMenu() {
			return menu;
		}

		public void setMissionMenu(EV3Menu menu) {
			this.menu = menu;
		}

		public EV3LargeRegulatedMotor getLeftMotor() {
			return leftMotor;
		}

		public void setLeftMotor(EV3LargeRegulatedMotor leftMotor) {
			this.leftMotor = leftMotor;
		}

		public EV3LargeRegulatedMotor getRightMotor() {
			return rightMotor;
		}

		public void setRightMotor(EV3LargeRegulatedMotor rightMotor) {
			this.rightMotor = rightMotor;
		}

		public EV3MediumRegulatedMotor getMediumMotor() {
			return mediumMotor;
		}

		public void setMediumMotor(EV3MediumRegulatedMotor mediumMotor) {
			this.mediumMotor = mediumMotor;
		}
		
		public boolean isMoving() {
 			return (leftMotor.isMoving() || rightMotor.isMoving());
 		}
		
}
