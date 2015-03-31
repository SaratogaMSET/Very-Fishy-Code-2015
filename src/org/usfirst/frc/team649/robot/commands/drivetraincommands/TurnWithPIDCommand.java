package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedTurning;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithPIDCommand extends Command {

	double deltaTranslationalDistance;
	double minimumPower;
	PIDController pid;
	
    public TurnWithPIDCommand(double angle) {
    	deltaTranslationalDistance = (angle / 360.0) * (26.0 * Math.PI);
    	minimumPower = 0.2;
    }
    
    public TurnWithPIDCommand(double angle, double minPower) {
    	deltaTranslationalDistance = (angle / 360.0) * (26.0 * Math.PI);
    	minimumPower = minPower;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	//DrivetrainSubsystem.EncoderBasedDriving.MIN_MOTOR_POWER = minimumPower;
    	pid = FishyRobot2015.drivetrainSubsystem.encoderTurnPID;
    	pid.setPID(DrivetrainSubsystem.EncoderBasedTurning.AUTO_P, DrivetrainSubsystem.EncoderBasedTurning.AUTO_I, DrivetrainSubsystem.EncoderBasedTurning.AUTO_D);
		FishyRobot2015.drivetrainSubsystem.resetEncoders();
    	pid.setSetpoint(deltaTranslationalDistance);
    	pid.setAbsoluteTolerance(DrivetrainSubsystem.EncoderBasedTurning.ABS_TOLERANCE);
		pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return (Math.abs(FishyRobot2015.drivetrainSubsystem.pidGet()) >= Math.abs(deltaTranslationalDistance)) || FishyRobot2015.oi.driver.isManualOverride();
		}

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
		FishyRobot2015.drivetrainSubsystem.driveFwdRot(0, 0);    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
