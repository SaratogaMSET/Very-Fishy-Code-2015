package org.usfirst.frc.team649.robot.subsystems;


import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DrivetrainSubsystem extends PIDSubsystem implements PIDSource, PIDOutput {
    
    public Talon [] motors;
    public Encoder[] encoders;
    public PIDController encoderDrivePID;
    public PIDController encoderTurnPID;
    public Gyro gyro;
    
    public static class EncoderBasedDriving {
    	private static final double ENCODER_DISTANCE_PER_PULSE = 16.25 / 128; //-6.25 * Math.PI / 128;
        public static double MAX_MOTOR_POWER = 0.5;
        public static double MIN_MOTOR_POWER = 0.15;
        
        //autonomous drive constants
        
        //winch
        public static final double AUTO_WINCH_DRIVE_DISTANCE = -14 * 12;
    	public static final double UNHOOK_BACKWARDS_DISTANCE = -100;
    	
    	//container pickup NEW, FACING DRIVERS STATION
    	public static final double AUTO_START_TO_CONTAINER = 15;
        
    	//pick up
    	public static final double AUTO_START_TO_TOTE = 0;
    	public static final double AUTO_BETWEEN_TOTES = 84;
    	public static final double AUTO_TOTE_TO_AUTO_ZONE = 63;
    	
    	
        //others
    	public static final double AUTO_P = 0.4;
    	public static final double AUTO_I = 0.0;
    	public static final double AUTO_D = 0.0;
    	public static final double ABS_TOLERANCE = 0.3;
    	public static final double OUTPUT_RANGE = 0.0;
    }
    
    public static class EncoderBasedTurning {
    	//public static final double AUTO_GRYO_TURN_ANGLE = 90;
        public static final double AUTO_P = 0.025;
    	public static final double AUTO_I = 0.0;
    	public static final double AUTO_D = 0.0001;
    	public static final double ABS_TOLERANCE = 1;
    	public static final double COMPENSATED_INCHES_PER_REV = 16.25;
    	
    	//public static final double GYRO_TOLERANCE = 2;

    }
    public static class RampingConstants {
    	public static final double ACCELERATION_LIMIT = .34;
    	public static final double DECELERATION_LIMIT = .34;
    	public static final double RAMP_UP_CONSTANT = .20;
    	public static final double RAMP_DOWN_CONSTANT = 1;

    }
    
    public DrivetrainSubsystem() {
    	super("Drivetrain", EncoderBasedDriving.AUTO_P, EncoderBasedDriving.AUTO_I, EncoderBasedDriving.AUTO_D);
    	motors = new Talon[RobotMap.DRIVE_TRAIN.MOTORS.length];
    	for (int i = 0; i < RobotMap.DRIVE_TRAIN.MOTORS.length; i++) {
            motors[i] = new Talon(RobotMap.DRIVE_TRAIN.MOTORS[i]);
        }
    	encoderDrivePID = this.getPIDController();
    	encoderDrivePID.setAbsoluteTolerance(EncoderBasedDriving.ABS_TOLERANCE);
    	//encoderDrivePID.setOutputRange(-EncoderBasedDriving.MAX_MOTOR_POWER, EncoderBasedDriving.MAX_MOTOR_POWER);
    	encoders = new Encoder[RobotMap.DRIVE_TRAIN.ENCODERS.length / 2];
        encoders[0] = new Encoder(RobotMap.DRIVE_TRAIN.ENCODERS[0], RobotMap.DRIVE_TRAIN.ENCODERS[1], true);
        encoders[1] = new Encoder(RobotMap.DRIVE_TRAIN.ENCODERS[2], RobotMap.DRIVE_TRAIN.ENCODERS[3]);
        encoders[0].setDistancePerPulse(EncoderBasedDriving.ENCODER_DISTANCE_PER_PULSE);
        encoders[1].setDistancePerPulse(EncoderBasedDriving.ENCODER_DISTANCE_PER_PULSE);
        
        gyro = new Gyro(RobotMap.DRIVE_TRAIN.GRYO);
        resetGyro();
        encoderTurnPID = new PIDController(EncoderBasedTurning.AUTO_P, EncoderBasedTurning.AUTO_I, EncoderBasedTurning.AUTO_D, this, this);
        encoderTurnPID.setAbsoluteTolerance(EncoderBasedTurning.ABS_TOLERANCE);
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    

    public void driveFwdRot(double fwd, double rot) {
        double left = fwd + rot, right = fwd - rot;
        double max = Math.max(1, Math.max(Math.abs(left), Math.abs(right)));
        left /= max;
        right /= max;
        rawDrive(left, right);
    }

    public void rawDrive(double left, double right) {
        int i = 0;
        left /= 1.0;//left > 0 ? Math.pow(left, 2)/2 : -Math.pow(left, 2)/2;
        right /= 1.0;//right > 0 ? Math.pow(right, 2)/2 : -Math.pow(right, 2)/2;
        for (; i < motors.length / 2; i++) {
            motors[i].set(left);
        }

        for (; i < motors.length; i++) {
            motors[i].set(-right);
        }
    }
    
    public double getVelocity() {
        int numEncoders = encoders.length;
        double totalRate = 0;
        for (int i = 0; i < numEncoders; i++) {
            totalRate += encoders[i].getRate();
        }
        final double rate = totalRate / numEncoders;
        return rate;
    }
    
    public double getDistance() {
//        int numEncoders = encoders.length;
//        double totalVal = 0;
//        for (int i = 0; i < numEncoders; i++) {
//            totalVal += encoders[i].getDistance();
//        }
//        return totalVal / numEncoders;
        
        double encDist1 = encoders[0].getDistance(), encDist2 = encoders[1].getDistance(); // * PIDConstants.ENCODER_DISTANCE_PER_PULSE;
    	return Math.abs(encDist1)>Math.abs(encDist2) ? encDist1: encDist2;
    }

    
    
    public void resetEncoders() {
        for (int x = 0; x < encoders.length; x++) {
            encoders[x].reset();
        }
    }
    
	protected double returnPIDInput() {
		return this.getDistance();
	}

	protected void usePIDOutput(double output) {
		if (output > EncoderBasedDriving.MAX_MOTOR_POWER){
        	output = EncoderBasedDriving.MAX_MOTOR_POWER;
        }
        else if (output < EncoderBasedDriving.MIN_MOTOR_POWER){
        	output = EncoderBasedDriving.MIN_MOTOR_POWER;
        }
        driveFwdRot(output, 0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    // GYRO
    public void resetGyro() {
    	gyro.reset();
    }
    
    public PIDController getGyroPIDControler() {
    	return encoderTurnPID;
    }
	@Override
	public void pidWrite(double output) {
        if (output > EncoderBasedDriving.MAX_MOTOR_POWER){
        	output = EncoderBasedDriving.MAX_MOTOR_POWER;
        }
        else if (output < EncoderBasedDriving.MIN_MOTOR_POWER){
        	output = EncoderBasedDriving.MIN_MOTOR_POWER;
        }
        
        driveFwdRot(0, output);
	}
	@Override
	public double pidGet() {
		return this.encoders[0].getDistance();
	}
}

