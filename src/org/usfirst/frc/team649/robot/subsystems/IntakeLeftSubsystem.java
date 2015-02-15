package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class IntakeLeftSubsystem extends PIDSubsystem {

	public Victor roller, arm;
	public Potentiometer pot;
	public PIDController pid;
	public DigitalInput totesLimit;
	public DigitalInput armLimit;
	public static final double INTAKE_ROLLER_SPEED = 0.4;
	public static final double INTAKE_ROLLER_OFF_SPEED = 0;
	public static final double PURGE_ROLLER_SPEED = -0.4;
	
	//CONSTANTS

	public static final class PIDConstants{
		public static final double P = 0.0;
		public static final double I = 0.0;
		public static final double D = 0.0;
		public static final double ABS_TOLERANCE = 0;
		
		public static final double ARM_POS_RELEASE = 1000000.0;
		//for pulling in totes
		public static final double ARM_POS_GRABBING = 0.0;
		//for both arms completely back
		public static final double ARM_POS_STORING = 6000000.0;
		
		public static final int GRABBING_STATE = 0;
		public static final int RELEASING_STATE = 1;
		public static final int STORE_STATE = 2;
	}
	
	public IntakeLeftSubsystem(){
		super("Grabber Left Subsystem", PIDConstants.P, PIDConstants.I, PIDConstants.D);
    	/*
    	 * to do this you have to call the class before its done being built...
    	 * need a method to assign pids after
    	pid =  FishyRobot2015.intakeLeftSubsystem.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
    	*/
    	//potentiometer
    	pot = new AnalogPotentiometer(RobotMap.LEFT_GRABBER.POT);
    	
    	//motors
    	roller = new Victor(RobotMap.LEFT_GRABBER.ROLLER_MOTOR);
    	arm = new Victor(RobotMap.LEFT_GRABBER.ARM_MOTOR);
    	
    	totesLimit = new DigitalInput(RobotMap.LEFT_GRABBER.TOTE_LIMIT_SWITCH);
    	armLimit = new DigitalInput(RobotMap.LEFT_GRABBER.ARM_LIMIT_SWITCH);
    	
    }
	
	public double getPot(){
		return pot.get();
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
		arm.set(output);

	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
