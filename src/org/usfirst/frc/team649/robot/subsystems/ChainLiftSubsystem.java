package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ChainLiftSubsystem extends PIDSubsystem{
    
	// Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor[] motors;
	public Encoder[] encoders;
	public PIDController pid;

	DigitalInput limitMaxLeft;
	DigitalInput limitMaxRight;
	public DigitalInput limitResetRight;
	public DigitalInput limitResetLeft;
	
	public double setpointHeight;
	public double offsetHeight;
	
	//true for platform, false for step
	public boolean platformOrStepOffset;
	public boolean isAtBase;
	public boolean isPastTop;
	public boolean isPastBottom;
	public boolean firstStageOfScore;
	
	public DigitalInput toteLimit;
	
	public static class PIDConstants {
		//PID
		//OLD VALUES P = 0.3, I=0.0 D=0.0
		public static final double P_VALUE = 0.0200;
		public static final double I_VALUE = 0.01;
		public static final double D_VALUE = 0.01;
		public static final double ENCODER_DISTANCE_PER_PULSE = (((22.0*(3.0/8.0)) * (12.0 / 60.0) * (18.0 / 42.0) * (18 / 42.0)) / 48.0);
		public static final double ABS_TOLERANCE = 1;
		public static final double CONSTANT_POWER_UP_VALUE = 0.6;
		public static final double CONSTANT_POWER_DOWN_VALUE = -0.8;

		
		//In inches
		public static final double STORE_TO_STEP_LEVEL_DIFFERENCE = 5.0;
		
		//MUST be 16 (hook separation)
		public static final double TOTE_PICK_UP_HEIGHT = 15.5;
		
		//pick up from base
		public static final double CONTAINER_PICK_UP_HEIGHT = 18;
		public static final double CONTAINER_RELEASE_HEIGHT = -5;
		public static final double VARIABLE_TOTE_SPACE_INCREMENT = -3;
		
		//under this height, the hal effect compensation is put in place when we reset
		//so that we dont go up first if we score from about this height
		public static final double MIN_HAL_EFFECT_COMPENSATION_HEIGHT = 3;
		
//		public static final double CONTAINER_REGRIP_LOWER_HEIGHT = -6;
		
		//TIMEOUTS
		public static final double HAL_COMPENSATION_TIME_OUT = 0.1; //in seconds
		public static final double RESET_TIME_OUT = 15;
		
		public static final boolean UP = true;
		public static final boolean DOWN = false;
		//Other
		public static final double UNLOAD_TOTES_MOTOR_POWER = -.8;
	    public static final double CURRENT_CAP = 10;
	    public static final double MAX_ENCODER_HEIGHT = 65;
	    public static final double MAX_LIFT_ENCODER_SPEED = 3;
	    public static final double CONTAINER_RESET_OFFSET = -2; //past the limit switch
	    
	    
	    public static final double ENCODER_RESET_OFFSET = 3.0;

	}

	public ChainLiftSubsystem() {
		super("Lift PID", PIDConstants.P_VALUE, PIDConstants.I_VALUE, PIDConstants.D_VALUE);
		motors = new Victor[RobotMap.CHAIN_LIFT.MOTORS.length];
		for (int i = 0; i < RobotMap.CHAIN_LIFT.MOTORS.length; i++) {
            motors[i] = new Victor(RobotMap.CHAIN_LIFT.MOTORS[i]);
        }
    
    	
    	encoders =  new Encoder[2];
    	encoders[0] = new Encoder(RobotMap.CHAIN_LIFT.ENCODERS[0], RobotMap.CHAIN_LIFT.ENCODERS[1], true, EncodingType.k2X);
    	encoders[0].setDistancePerPulse(PIDConstants.ENCODER_DISTANCE_PER_PULSE);

    	encoders[1] = new Encoder(RobotMap.CHAIN_LIFT.ENCODERS[2], RobotMap.CHAIN_LIFT.ENCODERS[3], false, EncodingType.k2X);
    	encoders[1].setDistancePerPulse(PIDConstants.ENCODER_DISTANCE_PER_PULSE);
        
        limitMaxLeft = new DigitalInput(RobotMap.CHAIN_LIFT.MAX_LIM_SWITCH_LEFT);
        limitMaxRight = new DigitalInput(RobotMap.CHAIN_LIFT.MAX_LIMIT_SWITCH_RIGHT);
        limitResetRight = new DigitalInput(RobotMap.CHAIN_LIFT.RESET_LIM_SWITCH_RIGHT);
        limitResetLeft = new DigitalInput(RobotMap.CHAIN_LIFT.RESET_LIM_SWITCH_LEFT);
        
        isAtBase = false; //TODO we hopefully call reset at the beginning of the program
        isPastTop = false;
        isPastBottom = false;
        firstStageOfScore = true;
       // ultra = new Ul
		pid = this.getPIDController();
    	pid.setAbsoluteTolerance(PIDConstants.ABS_TOLERANCE);
	}
	
    public void setPower(double power) {
        motors[0].set(power);
        motors[1].set(-power);
    }
    
    public void setPowerSafe(double power) {
    	double avgCurrent = ((FishyRobot2015.pdp.getCurrent(13) + FishyRobot2015.pdp.getCurrent(12)) / 2); 
    	if(avgCurrent > PIDConstants.CURRENT_CAP && Math.abs(this.getVelocity()) < 0.2 ) {
    		this.setPower(0);
    	} else {
    		this.setPower(power);
    	}
    }
    
    //Ryan Lewis Sensors
    public boolean isMaxLimitPressed() {
    	return !limitMaxLeft.get() || !limitMaxRight.get();
    }
    
    public boolean isResetLimitPressed() {
    	return !limitResetRight.get() || !limitResetLeft.get();
    }
    
    public double getHeight() {
    	//returns the highest encoder value
    	double encDist1 = encoders[0].getDistance(), encDist2 = encoders[1].getDistance(); // * PIDConstants.ENCODER_DISTANCE_PER_PULSE;
    	return Math.abs(encDist1)>Math.abs(encDist2) ? encDist1: encDist2;
    }
    
    public double getVelocity() {
    	double enc1Speed = encoders[0].getRate();
    	double enc2Speed = encoders[1].getRate();
    	return Math.abs(enc1Speed) > Math.abs(enc2Speed) ? enc1Speed : enc2Speed;
    }

    public void resetEncoders() {
        for (int x = 0; x < encoders.length; x++) {
            encoders[x].reset();
        }
        setpointHeight = 0;
        isPastTop = false;
        isPastBottom = true;
        firstStageOfScore = true;
    }
    
    //again based on height
    public int getNumTotes(){        //NEW
    	int baseCheck = 7;
    	//int contOffset = FishyRobot2015.containerState ? 1 : 0;
    	if (setpointHeight < baseCheck){
    		return 0; //TODO CHECK THAT A NEGATIVE DOESNT SCREW UP EVERYTHING
    	}
    	else if (setpointHeight < baseCheck + 16){
    		return 1; //- contOffset;
    	}
    	else if (setpointHeight < baseCheck + 32){
    		return 2;//- contOffset;
    	}
    	else if (setpointHeight < baseCheck + 48){
    		return 3;//- contOffset;
    	}
    	else if (setpointHeight < baseCheck + 64){
    		return 4;//- contOffset;
    	}
    	else{
    		return 5;//- contOffset;
    	}
    }
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return this.getHeight();
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		//if(FishyRobot2015.pdp.getCurrent(channel))
		double avgCurrent = ((FishyRobot2015.pdp.getCurrent(13) + FishyRobot2015.pdp.getCurrent(12)) / 2); 
    	if(avgCurrent > PIDConstants.CURRENT_CAP && Math.abs(this.getVelocity()) < 0.2 ) {
    		this.setPower(0);
    	} else{
		if(output > 0.0) {
    		this.setPower(0.6);
		} else if(output < 0.0) {
			this.setPower(-0.6);
		} else {
			this.setPower(0.0);
		}
    	}
	}
}

