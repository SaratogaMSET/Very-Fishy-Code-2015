package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSetDistanceWithPID extends Command {

	public static final int ON_TARGET_TIME = 250;
	public final double distance;
	public PIDController pid;
	//private long onTargetStartTime;
	public double minDriveSpeed;
	public double maxDriveSpeed;

	/**
	 * Construct a DriveSetDistanceCommand. Immutable, but can safely be reused
	 * for multiple executions of the same speed/distance.
	 *
	 * @param speed
	 *            The speed to drive at. Always positive.
	 * @param distance
	 *            The distance in inches to drive. Negative to drive backwards.
	 */
	public DriveSetDistanceWithPID(double distance) {
		this.distance = distance;
		if(distance < 0) {
			this.maxDriveSpeed = -0.15;
			this.minDriveSpeed = -0.5;
		} else if( distance > 0){
		this.minDriveSpeed = 0.15;
		this.maxDriveSpeed = 0.5;
		} else {
			this.maxDriveSpeed = 0.0;
			this.minDriveSpeed = 0.0;
		}

		this.pid = FishyRobot2015.drivetrainSubsystem.encoderDrivePID;
	}
	
	public DriveSetDistanceWithPID(double distance, double minDriveSpeed, double maxDriveSpeed){
		this.distance = distance;
		this.minDriveSpeed = minDriveSpeed;
		this.maxDriveSpeed = maxDriveSpeed;
		
		this.pid = FishyRobot2015.drivetrainSubsystem.encoderDrivePID;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Display.printToOutputStream("starting drive PID: " +
		// DriverStation.getInstance().getMatchTime() + ", dist: " + distance);
		EncoderBasedDriving.MIN_MOTOR_POWER = minDriveSpeed;
		EncoderBasedDriving.MAX_MOTOR_POWER = maxDriveSpeed;
		pid.setPID(DrivetrainSubsystem.EncoderBasedDriving.AUTO_P,
				DrivetrainSubsystem.EncoderBasedDriving.AUTO_I,
				DrivetrainSubsystem.EncoderBasedDriving.AUTO_D);
		SmartDashboard.putNumber("Setpoint Drivetrain", distance);
		pid.setSetpoint(distance);
		this.pid = FishyRobot2015.drivetrainSubsystem.encoderDrivePID;
		
		FishyRobot2015.drivetrainSubsystem.resetEncoders();
		// drivetrainSubsystem.startEncoders();
		pid.enable();
		//onTargetStartTime = -1;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// if (pid.onTarget()) {
		// Display.printToOutputStream("On Target");
		// if (onTargetStartTime == -1) {
		// Display.printToOutputStream("Set to curr time");
		// onTargetStartTime = System.currentTimeMillis();
		// } else if (System.currentTimeMillis() - onTargetStartTime >
		// ON_TARGET_TIME) {
		// return true;
		// }
		// } else {
		// if (onTargetStartTime != -1) {
		// Display.printToOutputStream("Off target");
		// }
		// onTargetStartTime = -1;
		// }
		//
		// return false;
		return (Math.abs(FishyRobot2015.drivetrainSubsystem.getDistance()) >= Math.abs(distance)) || FishyRobot2015.oi.driver.isManualOverride();
	//	return pid.onTarget() || FishyRobot2015.oi.driver.isManualOverride();
	}

	// Called once after isFinished returns true
	protected void end() {
		// Display.printToOutputStream("finished drive PID: " +
		// DriverStation.getInstance().getMatchTime() + ", dist: " +
		// driveTrainSubsystem.getDistance());
		pid.disable();
		FishyRobot2015.drivetrainSubsystem.driveFwdRot(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
