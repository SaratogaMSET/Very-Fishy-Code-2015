package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeLiftHeight extends Command {

	PIDController liftPID;
	double deltaHeight;
	
    public ChangeLiftHeight(double height) {
    	liftPID =  FishyRobot2015.chainLiftSubsystem.getPIDController();
    	deltaHeight = height;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	liftPID.enable();
    	liftPID.setSetpoint(FishyRobot2015.chainLiftSubsystem.setpointHeight + deltaHeight);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return ((FishyRobot2015.chainLiftSubsystem.setpointHeight + FishyRobot2015.chainLiftSubsystem.offsetHeight) ==  FishyRobot2015.chainLiftSubsystem.getHeight() ||  FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed() ||  FishyRobot2015.chainLiftSubsystem.isResetLimitPressed());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
