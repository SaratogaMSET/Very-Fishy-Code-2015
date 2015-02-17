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

public class IntakeLeftSubsystem extends PIDSubsystem {

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

		public static final double P_CLOSE = 0.1;
		public static final double I_CLOSE = 0.0;
		public static final double D_CLOSE = 0.0;
		
		public static final double P_OPEN = 0.1;
		public static final double I_OPEN = 0.0;
		public static final double D_OPEN = 0.0;
		
		public static final double ABS_TOLERANCE = .01;
		
		public static final double CONVERSION_DEGREES_TO_POT = 1.0/270;
		
		public static final double ARM_POS_RELEASE = 1.34; //200 * CONVERSION_DEGREES_TO_POT;
		//for pulling in totes
		public static final double ARM_POS_GRABBING = 1.0; //225 * CONVERSION_DEGREES_TO_POT;
		//for both arms completely back
		public static final double ARM_POS_STORING = 4.2; // * CONVERSION_DEGREES_TO_POT; //228
		
		public static final int GRABBING_STATE = 0;
		public static final int RELEASING_STATE = 1;
		public static final int STORE_STATE = 2;
	}
	
	public IntakeLeftSubsystem(){
		super("Grabber Left Subsystem", PIDConstants.P_CLOSE, PIDConstants.I_CLOSE, PIDConstants.D_CLOSE);
    	
    	pid =  this.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
    	
    	//potentiometer
    	pot = new AnalogInput(RobotMap.LEFT_GRABBER.POT);
    	
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
		if (isArmLimitPressed()){
			arm.set(0);
		}
		else{
			arm.set(output);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
