package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class IntakePortSubsystem extends PIDSubsystem {

	public Victor roller, arm;
	public AnalogInput pot;
	public PIDController pid;
	public DigitalInput totesLimit;
	public DigitalInput armLimit;
	public static final double INTAKE_ROLLER_SPEED = 0.4;
	public static final double INTAKE_ROLLER_OFF_SPEED = 0;
	public static final double PURGE_ROLLER_SPEED = -0.4;
	
	//CONSTANTS

	public static final class PIDConstants{

		public static final double P_REGULAR = 0.10;
		public static final double I_REGULAR = 0.01;
		public static final double D_REGULAR = 0.02;
		
		public static final double P_GRABBER_TO_RELEASE = 2.5;
		public static final double I_GRABBER_TO_RELEASE = 0.01;
		public static final double D_GRABBER_TO_RELEASE = 0.15;
		
		public static final double ABS_TOLERANCE = .01;
		
		public static final double CONVERSION_DEGREES_TO_POT = 1.0/270;
		
		public static final double ARM_POS_RELEASE = 1.6;//NESSY: 1.43; PBOT:  //200 * CONVERSION_DEGREES_TO_POT;
		//for pulling in totes
		public static final double ARM_POS_GRABBING = 1.35;//NESSY: 1.2; PBOT: //225 * CONVERSION_DEGREES_TO_POT;
		//for both arms completely back
		public static final double ARM_POS_STORING = 1.9; // * CONVERSION_DEGREES_TO_POT; //228
		
		public static final double PID_TOLERANCE = 0.3;
		
		public static final int GRABBING_STATE = 0;
		public static final int RELEASING_STATE = 1;
		public static final int STORE_STATE = 2;
		public static final int CURRENT_STATE = 3;
		
		public static final double MAX_REASONABLE_VOLTAGE = 2.5;
		public static final double MIN_REASONABLE_VOLTAGE = .8;
		
		public static final int POT_SAMPLES_TO_AVERAGE = 3;
		public static final double ARMS_IN_POWER = -0.14;
		public static final double ARMS_OUT_POWER = 0.3;
	}
	
	public IntakePortSubsystem(){
		super("Grabber Left Subsystem", PIDConstants.P_REGULAR, PIDConstants.I_REGULAR, PIDConstants.D_REGULAR);
    	
    	pid =  this.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
    	
    	//potentiometer
    	pot = new AnalogInput(RobotMap.LEFT_GRABBER.POT);
    	pot.setAverageBits(PIDConstants.POT_SAMPLES_TO_AVERAGE);
    	
    	//motors
    	roller = new Victor(RobotMap.LEFT_GRABBER.ROLLER_MOTOR);
    	arm = new Victor(RobotMap.LEFT_GRABBER.ARM_MOTOR);
    	
    	totesLimit = new DigitalInput(RobotMap.LEFT_GRABBER.TOTE_LIMIT_SWITCH);
    	armLimit = new DigitalInput(RobotMap.LEFT_GRABBER.ARM_LIMIT_SWITCH);
    	
    }
	
	public double getPot(){
		return pot.getVoltage();
	}
	
	public boolean isToteLimitPressed(){
		return !totesLimit.get();
	}
	
	public boolean isArmLimitPressed() {
		return armLimit.get();
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getPot();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
//		if (isArmLimitPressed()){
//			arm.set(0);
//		}
//		else{
			arm.set(output);
	//	}
	}
	
	public boolean withinBounds(){
		return getPot() < PIDConstants.MAX_REASONABLE_VOLTAGE && getPot() > PIDConstants.MIN_REASONABLE_VOLTAGE;
	
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
