package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.GyroBasedDriving;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithPIDCommand extends Command {

	public PIDController pid;
	public Gyro gryo;
	public double setAngle;
	
    public TurnWithPIDCommand(double angle) {
    	setAngle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.pid = FishyRobot2015.drivetrainSubsystem.getGyroPIDControler();
    	
		pid.setPID(DrivetrainSubsystem.GyroBasedDriving.AUTO_P,
				DrivetrainSubsystem.GyroBasedDriving.AUTO_I,
				DrivetrainSubsystem.GyroBasedDriving.AUTO_D);
		pid.setPercentTolerance(10.0);
		FishyRobot2015.drivetrainSubsystem.resetGyro();
		pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		pid.setSetpoint(setAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//TODO check if this works lol
        return pid.onTarget();
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
