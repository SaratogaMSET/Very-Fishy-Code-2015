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

	//0 == grab
    public SetIntakeArmPositionWithoutPID(int st) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		 state = st;
		 leftDone = false;
		 rightDone = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(state == IntakePortSubsystem.PIDConstants.GRABBING_STATE) {
    		SmartDashboard.putNumber("Setpoint Left", IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING);
    		SmartDashboard.putNumber("Setpoint Right", IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING);
    	}else if(state ==IntakePortSubsystem.PIDConstants.RELEASING_STATE) {
    		SmartDashboard.putNumber("Setpoint Left", IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE);
    		SmartDashboard.putNumber("Setpoint Right", IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE);
    	}  else if (state == PIDConstants.STORE_STATE) {
    		SmartDashboard.putNumber("Setpoint Left", IntakePortSubsystem.PIDConstants.ARM_POS_STORING);
    		SmartDashboard.putNumber("Setpoint Right", IntakeStarboardSubsystem.PIDConstants.ARM_POS_STORING);

    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(state == IntakePortSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() >= IntakePortSubsystem.PIDConstants.ARM_POS_GRABBING) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(-0.2);
    	} else if(state == IntakePortSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeLeftSubsystem.getPot() <= IntakePortSubsystem.PIDConstants.ARM_POS_RELEASE) {
    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.3);
    	} else {
    		leftDone = true;
    		FishyRobot2015.intakeLeftSubsystem.arm.set(0.0);
    	}
    	
    	if(state == IntakeStarboardSubsystem.PIDConstants.GRABBING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() >= IntakeStarboardSubsystem.PIDConstants.ARM_POS_GRABBING) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(0.2);
    	} else if(state == IntakeStarboardSubsystem.PIDConstants.RELEASING_STATE && FishyRobot2015.intakeRightSubsystem.getPot() <= IntakeStarboardSubsystem.PIDConstants.ARM_POS_RELEASE) {
    		FishyRobot2015.intakeRightSubsystem.arm.set(-0.3);
    	} else {
    		rightDone = true;
    		FishyRobot2015.intakeRightSubsystem.arm.set(0.0);
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
