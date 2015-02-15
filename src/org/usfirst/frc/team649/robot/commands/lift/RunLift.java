package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunLift extends Command {

	double power;
    public RunLift(double setPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	power = setPower;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	FishyRobot2015.chainLiftSubsystem.setPower(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
