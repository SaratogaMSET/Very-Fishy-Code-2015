package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedTurning;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnWithPIDCommand extends Command {

	double deltaTranslationalDistance;
	double minimumPower;
	double maximumPower;
	PIDController pid;
	
    public TurnWithPIDCommand(double angle) {
    	deltaTranslationalDistance = (angle / 360.0) * (16.5 * Math.PI);
    	if(angle > 0) {
    		minimumPower = 0.3;
    		maximumPower = 0.7;
    	} else if (angle < 0) {
        	minimumPower = -0.7;
        	maximumPower = -0.3;
    	} else {
        	minimumPower = 0.0;
        	maximumPower = 0.0;
    	}
    }
    
    public TurnWithPIDCommand(double angle, double minPower, double maxPower) {
    	deltaTranslationalDistance = (angle / 360.0) * (16.5 * Math.PI);
    	minimumPower = minPower;
    	maximumPower = maxPower;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	DrivetrainSubsystem.EncoderBasedDriving.MIN_MOTOR_POWER = minimumPower;
    	DrivetrainSubsystem.EncoderBasedDriving.MAX_MOTOR_POWER = maximumPower;
    	pid = FishyRobot2015.drivetrainSubsystem.encoderTurnPID;
    	pid.setPID(DrivetrainSubsystem.EncoderBasedTurning.AUTO_P, DrivetrainSubsystem.EncoderBasedTurning.AUTO_I, DrivetrainSubsystem.EncoderBasedTurning.AUTO_D);
		FishyRobot2015.drivetrainSubsystem.resetEncoders();
    	pid.setSetpoint(deltaTranslationalDistance);
    	pid.setAbsoluteTolerance(DrivetrainSubsystem.EncoderBasedTurning.ABS_TOLERANCE);
		pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putString("ACTIVE COMMAND", "TURNING WITH PID");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return (Math.abs(FishyRobot2015.drivetrainSubsystem.pidGet()) >= Math.abs(deltaTranslationalDistance)) || FishyRobot2015.oi.driver.isManualOverride();
		}

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
		FishyRobot2015.drivetrainSubsystem.driveFwdRot(0, 0);    
	}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
