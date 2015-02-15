package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ChangeOffsetHeight extends Command {

	PIDController liftPID;
    public ChangeOffsetHeight(boolean platformHeight) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	liftPID =  FishyRobot2015.chainLiftSubsystem.getPIDController();
    	if(platformHeight) {
    		 FishyRobot2015.chainLiftSubsystem.offsetHeight = ChainLiftSubsystem.PIDConstants.PLATFORM_DRIVE_OFFSET;
    	}
    	else
    		 FishyRobot2015.chainLiftSubsystem.offsetHeight = ChainLiftSubsystem.PIDConstants.STEP_OFFSET;
    	

		FishyRobot2015.chainLiftSubsystem.platformOrStepOffset = platformHeight; //on platform or offset
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	liftPID.enable();
    	liftPID.setSetpoint( FishyRobot2015.chainLiftSubsystem.setpointHeight +  FishyRobot2015.chainLiftSubsystem.offsetHeight);
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
