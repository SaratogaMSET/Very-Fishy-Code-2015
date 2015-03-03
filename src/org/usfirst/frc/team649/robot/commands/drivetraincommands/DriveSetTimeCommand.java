package org.usfirst.frc.team649.robot.commands.drivetraincommands;

import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSetTimeCommand extends Command {

	double timeToDrive;
	Timer timer;
    public DriveSetTimeCommand(double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timeToDrive = time;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	new DriveForwardRotate(0.4, 0).start();
    	timer.start();
    }
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= timeToDrive;
    }

    // Called once after isFinished returns true
    protected void end() {
    	new DriveForwardRotate(0, 0).start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}