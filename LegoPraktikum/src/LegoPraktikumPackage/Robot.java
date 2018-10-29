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

import mission.LineFollower;
import mission.Mission;
import mission.MissionMenu;
import sensor.SingleValueSensorWrapper;

public class Robot {
	
	// Date need to be measured and adjusted
		private final double WHEEL_DIAMETER = 3.2d; // unit:cm
		private final double TRACK_WIDTH = 11d; // unit: cm
		private final Port LEFT_MOTOR = MotorPort.A;
		private final Port MEDIUM_MOTOR = MotorPort.C;
		private final Port RIGHT_MOTOR = MotorPort.D;

		private static final Port COLOR_SENSOR = SensorPort.S1;
		private static final Port TOUCH_SENSOR_1 = SensorPort.S2;
		private static final Port TOUCH_SENSOR_2 = SensorPort.S3;
		private static final Port ULTRASONIC_SENSOR = SensorPort.S4;

		//private SensorThread sensors;
		private final EV3ColorSensor colorS = new EV3ColorSensor(COLOR_SENSOR);
		private final EV3TouchSensor touchS1 = new EV3TouchSensor(TOUCH_SENSOR_1);
		private final EV3TouchSensor touchS2 = new EV3TouchSensor(TOUCH_SENSOR_2);
		private final EV3UltrasonicSensor ultraS = new EV3UltrasonicSensor(ULTRASONIC_SENSOR);
		
		//private MissionMenu missionMenu;

		private EV3LargeRegulatedMotor leftMotor;
		private EV3LargeRegulatedMotor rightMotor;
		private EV3MediumRegulatedMotor mediumMotor;

		//private Drive drive;

		public Robot() {

			this.leftMotor = new EV3LargeRegulatedMotor(LEFT_MOTOR);
			this.rightMotor = new EV3LargeRegulatedMotor(RIGHT_MOTOR);
			this.mediumMotor = new EV3MediumRegulatedMotor(MEDIUM_MOTOR);

			//SingleValueSensorWrapper color = new SingleValueSensorWrapper(colorS, "Red");
			//SingleValueSensorWrapper touch1 = new SingleValueSensorWrapper(touchS1, "Touch");
			//SingleValueSensorWrapper touch2 = new SingleValueSensorWrapper(touchS2, "Touch");
			//SingleValueSensorWrapper distance = new SingleValueSensorWrapper(ultraS, "Distance");

			//this.sensors = new SensorThread(color, touch1, touch2, distance);
			//this.missionMenu = new MissionMenu();
			//this.drive = new Drive(this);

			//new Thread(this.sensors).start();
		}
		
		public void mainLoop() throws InterruptedException {
			//// new Thread(this.sensors).start();
			//Button.LEDPattern(1); // green light
			//this.missionMenu.startGUI(this); // show menu on the brick's screen
			//System.exit(0);
		}
		
		public void changeSettingsForLabyrinth() {
			//this.sensors.setsColor(new SingleValueSensorWrapper(colorS, 2));
//			//this.colorS.setCurrentMode("Color ID");
		}
		
		public void changeSettingsForLineFollower() {
			//this.sensors.setsColor(new SingleValueSensorWrapper(colorS, "Red"));
		}
		
		public int getColorID() {
			//return this.colorS.getColorID();
		}
		
		
		// Getter und Setter
		public SensorThread getSensors() {
			return sensors;
		}

		public void setSensors(SensorThread sensors) {
			this.sensors = sensors;
		}

		public MissionMenu getMissionMenu() {
			return missionMenu;
		}

		public void setMissionMenu(MissionMenu missionMenu) {
			this.missionMenu = missionMenu;
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
		
}
