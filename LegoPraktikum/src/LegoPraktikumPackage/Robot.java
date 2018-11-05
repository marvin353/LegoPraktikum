package LegoPraktikumPackage;

import lejos.hardware.Button;
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
		private static final Port TOUCH_SENSOR_1 = SensorPort.S3;
		private static final Port TOUCH_SENSOR_2 = SensorPort.S2;
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

			this.sensors = new SensorThread(color, touch1, touch2, distance);
			new Thread(this.sensors).start();
			
			this.menu = new EV3Menu();
			//this.drive = new Drive(this);
			
			//changeSettingsForLineFollower();
			//this.linieFahren = new LinieFahren(this);
			//linieFahren.run();
			
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
		
		public int getColorID() {
			//return this.colorS.getColorID();
			return 0;
		}
		
		
		
		
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
			leftMotor.stop();
			leftMotor.rotate(angle,true);
		}
		
		public void setRightMotorRotateAsync(int angle) {
			rightMotor.stop();
			rightMotor.rotate(angle,true);
		}
		
		public int getTachoCountLeftMotor() {
			return leftMotor.getTachoCount();
		}
		
		public int getTachoCountRightMotor() {
			return rightMotor.getTachoCount();
		}
	
		public void stopRightMotor() {
			rightMotor.stop();
		}
		
		public void stopLeftMotor() {
			leftMotor.stop();
		}
		
		public void turnLeft(int degree) {
			double factor = 3.4;
			rightMotor.stop(true);
			leftMotor.stop(true);
			
			int left = (int)(degree * factor);
			int right = (int)(degree * factor * (-1));
					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left, true);
		}
		
		public void turnRight(int degree) {
			double factor = 3.4;
			rightMotor.stop(true);
			leftMotor.stop(true);
			
			int right = (int)(degree * factor);
			int left = (int)(degree * factor * (-1));
					
			rightMotor.rotate(right, true);
			leftMotor.rotate(left, true);
		}
		
		//Sound
		
		
		
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

		/*public double getTRACK_WIDTH() {
			return TRACK_WIDTH;
		}*/

		/*public Drive getDrive() {
			return drive;
		}*/

		/*public void setDrive(Drive drive) {
			this.drive = drive;
		}*/

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
