package org.usfirst.frc.team649.robot.commands.autowinchcommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.AutoWinchSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WinchTotesIn extends Command {

    public WinchTotesIn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	FishyRobot2015.autoWinchSubsystem.setPower(AutoWinchSubsystem.WINCH_DRIVE_POWER);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return FishyRobot2015.autoWinchSubsystem.isWinchComplete();
    }

    // Called once after isFinished returns true
    protected void end() {
    	FishyRobot2015.autoWinchSubsystem.setPower(AutoWinchSubsystem.WINCH_OFF_POWER);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
