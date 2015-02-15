package org.usfirst.frc.team649.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static final class JOYSTICKS{
		public static int JOYSTICK_OPERATOR = 2;
		public static int JOYSTICK_DRIVER_LEFT = 0;
		public static int JOYSTICK_DRIVER_RIGHT = 1;
		public static int JOYSTICK_MANUAL = 3;
	}
	
	public static final class DRIVE_TRAIN {
		
		public static final int[] MOTORS = new int[] {7,6,5,4}; //changed
        public static final int[] ENCODERS = new int[]{0, 1, 4, 5};
        public static final int GRYO = 0;

	}
	
	public static final class CHAIN_LIFT {
		public static final int[] MOTORS = new int[] {17,18}; //changed
        public static final int[] ENCODERS = new int[]{2,3,8,9};
		public static final int RESET_LIM_SWITCH = 10;
		public static final int MAX_LIM_SWITCH = 14;


	}
	
	public static final class LEFT_GRABBER {
		public static final int ROLLER_MOTOR = 2;
		public static final int ARM_MOTOR = 3;
		public static final int POT = 1;
		//public static final int LIMIT_SWITCH = 11;
		public static final int TOTE_LIMIT_SWITCH = 6;
		public static final int ARM_LIMIT_SWITCH = 19;
	}
	
	public static final class RIGHT_GRABBER {
		public static final int ROLLER_MOTOR = 12;
		public static final int ARM_MOTOR = 13;
		public static final int POT = 2;

		public static final int TOTE_LIMIT_SWITCH = 11;
		public static final int ARM_LIMIT_SWITCH = 20;


	}
	
	public static final class AUTO_WINCH {
		public static final int MOTOR = 14;
		public static final int LIM = 16;
		public static final int SERVO = 15;
		
	}
	
	public static final class CONTAINER_GRABBER {
		public static final int PISTON_1_FORWARD_CHANNEL = 0;
		public static final int PISTON_1_REVERSE_CHANNEL = 1;
		public static final int PISTON_2_FORWARD_CHANNEL = 2;
		public static final int PISTON_2_REVERSE_CHANNEL = 3;
	}
}
