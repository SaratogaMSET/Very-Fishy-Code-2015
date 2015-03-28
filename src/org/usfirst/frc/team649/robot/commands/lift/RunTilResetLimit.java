package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunTilResetLimit extends Command {

    public RunTilResetLimit() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(FishyRobot2015.chainLiftSubsystem);
    	//reset to platform always if not already
    	FishyRobot2015.chainLiftSubsystem.platformOrStepOffset = true;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(PIDConstants.RESET_TIME_OUT);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 FishyRobot2015.chainLiftSubsystem.setPower(ChainLiftSubsystem.PIDConstants.UNLOAD_TOTES_MOTOR_POWER);
    	 
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//when its reached the bottom
    	
        return isTimedOut() || FishyRobot2015.chainLiftSubsystem.isResetLimitPressed() || FishyRobot2015.oi.driver.isManualOverride();
    }

    // Called once after isFinished returns true
    protected void end() {
    	 FishyRobot2015.chainLiftSubsystem.resetEncodersAndVariables();
    	 FishyRobot2015.chainLiftSubsystem.setPower(0);
    	 FishyRobot2015.chainLiftSubsystem.isAtBase = true;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
