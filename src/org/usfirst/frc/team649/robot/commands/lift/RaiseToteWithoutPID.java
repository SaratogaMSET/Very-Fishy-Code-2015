package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RaiseToteWithoutPID extends Command {

	private boolean upOrDown;
	private boolean done;
	public double heightChangeReference;

    public RaiseToteWithoutPID(boolean up) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		upOrDown = up;
		done = false;
    	requires(FishyRobot2015.chainLiftSubsystem);
		heightChangeReference = PIDConstants.TOTE_PICK_UP_HEIGHT;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (FishyRobot2015.chainLiftSubsystem.isPastTop && upOrDown || FishyRobot2015.chainLiftSubsystem.isPastBottom && !upOrDown){
			heightChangeReference = 0;
			done = true;
		} else{
			if (upOrDown) {
				if(FishyRobot2015.chainLiftSubsystem.isAtBase || FishyRobot2015.chainLiftSubsystem.isPastBottom) {
					//offset initial
					heightChangeReference += PIDConstants.HEIGHT_OFFSET_FIRST_PICK_UP;
				}
				 FishyRobot2015.chainLiftSubsystem.setpointHeight += heightChangeReference;
			} else {
				 FishyRobot2015.chainLiftSubsystem.setpointHeight -= heightChangeReference;
			}
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(upOrDown && (FishyRobot2015.chainLiftSubsystem.getHeight() < FishyRobot2015.chainLiftSubsystem.setpointHeight)) {
    		FishyRobot2015.chainLiftSubsystem.setPowerSafe(ChainLiftSubsystem.PIDConstants.CONSTANT_POWER_UP_VALUE);
    	} else if(!upOrDown && (FishyRobot2015.chainLiftSubsystem.getHeight() > FishyRobot2015.chainLiftSubsystem.setpointHeight)) {
    		FishyRobot2015.chainLiftSubsystem.setPowerSafe(ChainLiftSubsystem.PIDConstants.CONSTANT_POWER_DOWN_VALUE);
    	} else {
    		done = true;
    		FishyRobot2015.chainLiftSubsystem.setPowerSafe(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done || (FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed() && upOrDown) ||  (FishyRobot2015.chainLiftSubsystem.isResetLimitPressed() && !upOrDown) || FishyRobot2015.oi.driver.isManualOverride();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (upOrDown){
			FishyRobot2015.chainLiftSubsystem.isAtBase = false;
			if (FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed()){
				FishyRobot2015.chainLiftSubsystem.isPastTop = true;
			}
			FishyRobot2015.chainLiftSubsystem.isPastBottom = false;
		}
		else{
			FishyRobot2015.chainLiftSubsystem.isPastTop = false;
			if (FishyRobot2015.chainLiftSubsystem.isResetLimitPressed()){
				FishyRobot2015.chainLiftSubsystem.isPastBottom = true;
			}
			SmartDashboard.putBoolean("Reached", true);
		}
		FishyRobot2015.chainLiftSubsystem.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
