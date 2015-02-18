
package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * 
 */
public class IntakeStarboardSubsystem extends PIDSubsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public Victor roller, arm;
	public AnalogInput pot;
	public PIDController pid;
	public DigitalInput toteLimit;
	public DigitalInput armLimit;
	public static final double INTAKE_ROLLER_SPEED = 0.4;
	public static final double INTAKE_ROLLER_OFF_SPEED = 0;
	public static final double PURGE_ROLLER_SPEED = -0.4;

	public static final class PIDConstants{
		public static final double P_REGULAR = -0.18;
		public static final double I_REGULAR = -0.01;
		public static final double D_REGULAR = -0.01;
		
		public static final double P_GRABBER_TO_RELEASE = -2;
		public static final double I_GRABBER_TO_RELEASE = -0.01;
		public static final double D_GRABBER_TO_RELEASE = -0.19;
		
		
		public static final double ABS_TOLERANCE = .01;

		public static final double CONVERSION_DEGREES_TO_POT = 1.0/270;
		
		public static final double ARM_POS_RELEASE = 1.50; //89.0 * CONVERSION_DEGREES_TO_POT;
		public static final double ARM_POS_GRABBING = 1.3; //* CONVERSION_DEGREES_TO_POT;
		public static final double ARM_POS_STORING = 3.1; // * CONVERSION_DEGREES_TO_POT; //261
		
		public static final int GRABBING_STATE = 0;
		public static final int RELEASING_STATE = 1;
		public static final int STORE_STATE = 2;
		public static final int CURRENT_STATE = 3;
		
		public static final double MAX_REASONABLE_VOLTAGE = 4.7;
		public static final double MIN_REASONABLE_VOLTAGE = 1.2;
	}	
	
    public IntakeStarboardSubsystem(){
    	super("Grabber Right Subsystem", PIDConstants.P_REGULAR, PIDConstants.I_REGULAR, PIDConstants.D_REGULAR);
    	
    	pid = this.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
    	
    	//potentiometer
    	pot = new AnalogInput(RobotMap.RIGHT_GRABBER.POT);
    	
    	//motors

    	roller = new Victor(RobotMap.RIGHT_GRABBER.ROLLER_MOTOR);
    	arm = new Victor(RobotMap.RIGHT_GRABBER.ARM_MOTOR);
    	
    	toteLimit = new DigitalInput(RobotMap.RIGHT_GRABBER.TOTE_LIMIT_SWITCH);
    	armLimit = new DigitalInput(RobotMap.RIGHT_GRABBER.ARM_LIMIT_SWITCH);
    }
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	
	public double getPot(){
		return pot.getVoltage();
	}
	
	public boolean isToteLimitPressed(){
		return !toteLimit.get();
	}
    
	public boolean isArmLimitPressed() {
		return armLimit.get();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getPot();
	}
	
	public boolean withinBounds(){
		return getPot() < PIDConstants.MAX_REASONABLE_VOLTAGE && getPot() > PIDConstants.MIN_REASONABLE_VOLTAGE;
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
}

