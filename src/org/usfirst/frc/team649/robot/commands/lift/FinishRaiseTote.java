package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FinishRaiseTote extends Command {

	private PIDController liftPID;

	public boolean upOrDown;
	
    public FinishRaiseTote(boolean up) {
    	upOrDown = up;
    	liftPID =  FishyRobot2015.chainLiftSubsystem.getPIDController();
		if (upOrDown) {
			 FishyRobot2015.chainLiftSubsystem.setpointHeight += ChainLiftSubsystem.PIDConstants.INTERMEDIATE_TO_STORE_DIFFERENCE;
		} else {
			 FishyRobot2015.chainLiftSubsystem.setpointHeight -= ChainLiftSubsystem.PIDConstants.INTERMEDIATE_TO_STORE_DIFFERENCE;
		}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	liftPID.enable();
		liftPID.setSetpoint(FishyRobot2015.chainLiftSubsystem.setpointHeight +  FishyRobot2015.chainLiftSubsystem.offsetHeight);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return((liftPID.onTarget() ||  FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed() ||  FishyRobot2015.chainLiftSubsystem.isResetLimitPressed()));
    }

    // Called once after isFinished returns true
    protected void end() {
    	//if its going up, its not at base and no longer first
    	if (upOrDown){
    		FishyRobot2015.chainLiftSubsystem.isAtBase = false;
    	}
    	liftPID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
