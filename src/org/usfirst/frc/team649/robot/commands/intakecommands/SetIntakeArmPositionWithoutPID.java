package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIntakeArmPositionWithoutPID extends Command {

	boolean close;
	boolean leftDone;
	boolean rightDone;

    public SetIntakeArmPositionWithoutPID(boolean in) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		 in = close;
		 leftDone = false;
		 rightDone = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(close && FishyRobot2015.intakeLeftSubsystem.getPot() <= IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(-0.1);
    	} else if(!close && FishyRobot2015.intakeLeftSubsystem.getPot() >= IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.1);
    	} else {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.0);
    		leftDone = true;
    	}
    	
    	if(close && FishyRobot2015.intakeRightSubsystem.getPot() <= IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(-0.1);
    	} else if(!close && FishyRobot2015.intakeRightSubsystem.getPot() >= IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(0.1);
    	} else {
    		FishyRobot2015.intakeRightSubsystem.arm.set(0.0);
    		rightDone = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (leftDone && rightDone || FishyRobot2015.oi.driver.isManualOverride());
    }

    // Called once after isFinished returns true
    protected void end() {
		FishyRobot2015.intakeRightSubsystem.arm.set(0.0);
		FishyRobot2015.intakeLeftSubsystem.arm.set(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
