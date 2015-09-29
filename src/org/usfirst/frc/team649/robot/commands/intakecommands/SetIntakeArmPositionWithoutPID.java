package org.usfirst.frc.team649.robot.commands.intakecommands;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 *
 */

public class SetIntakeArmPositionWithoutPID extends Command {

	int state;
	boolean leftDone;
	boolean rightDone;
	
	double leftGrab, rightGrab, leftRelease, rightRelease;

	//0 == grab
    public SetIntakeArmPositionWithoutPID(int st) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		 state = st;
		 leftDone = false;
		 rightDone = false;
		 
		 leftGrab = 3.0;
		 leftRelease = 3.19;
		 rightGrab = 1.68;
		 rightRelease = 1.89;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(state == IntakePortSubsystem.PIDConstants.GRABBING_STATE) {
    		SmartDashboard.putNumber("Setpoint Left", IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING);
    		SmartDashboard.putNumber("Setpoint Right", IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING);
    	}
    	else if(state ==IntakePortSubsystem.PIDConstants.RELEASING_STATE) {
    		SmartDashboard.putNumber("Setpoint Left", IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE);
    		SmartDashboard.putNumber("Setpoint Right", IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE);
    	}
    	else {
    		SmartDashboard.putNumber("Setpoint Left", FishyRobot2015.intakeLeftSubsystem.getPot());
    		SmartDashboard.putNumber("Setpoint Right", FishyRobot2015.intakeRightSubsystem.getPot());
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(state == IntakePortSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() >= IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING) {
//    		FishyRobot2015.intakeLeftSubsystem.arm.set(IntakePortSubsystem.PIDConstants.ARMS_IN_POWER);
//    	} else if(state == IntakePortSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() <= IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE) {
//    		FishyRobot2015.intakeLeftSubsystem.arm.set(IntakePortSubsystem.PIDConstants.ARMS_OUT_POWER);
//    	} else {
//    		leftDone = true;
//    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.0);
//    	}
//    	
//    	if(state == IntakeStarboardSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() >= IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING) {
//    		FishyRobot2015.intakeRightSubsystem.arm.set(IntakeStarboardSubsystem.PIDConstants.ARMS_IN_POWER);
//    	} else if(state == IntakeStarboardSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() <= IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE) {
//    		FishyRobot2015.intakeRightSubsystem.arm.set(IntakeStarboardSubsystem.PIDConstants.ARMS_OUT_POWER);
//    	} else {
//    		rightDone = true;
//    		FishyRobot2015.intakeRightSubsystem.arm.set(0.0);
//    	}
    	
    	if(state == IntakePortSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() >= leftGrab) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(IntakePortSubsystem.PIDConstants.ARMS_IN_POWER);
    	} else if(state == IntakePortSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() <= leftRelease) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(IntakePortSubsystem.PIDConstants.ARMS_OUT_POWER);
    	} else {
    		leftDone = true;
    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.0);
    	}
    	
    	if(state == IntakeStarboardSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() >= rightGrab) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(IntakeStarboardSubsystem.PIDConstants.ARMS_IN_POWER);
    	} else if(state == IntakeStarboardSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() <= rightRelease) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(IntakeStarboardSubsystem.PIDConstants.ARMS_OUT_POWER);
    	} else {
    		rightDone = true;
    		FishyRobot2015.intakeRightSubsystem.arm.set(0.0);
    	}

		SmartDashboard.putString("ACTIVE COMMAND", "Setting 2-STATE ARM POS: " + state);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (leftDone && rightDone) || FishyRobot2015.oi.driver.isManualOverride() || !FishyRobot2015.intakeLeftSubsystem.withinBounds() || !FishyRobot2015.intakeRightSubsystem.withinBounds();
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
