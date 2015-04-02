package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PickUpContainer extends Command {

	
	private PIDController liftPID;
	private boolean upOrDown;
	
	public double heightChangeReference;

	public PickUpContainer(boolean up){
		upOrDown = up;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(FishyRobot2015.chainLiftSubsystem);
		heightChangeReference = PIDConstants.CONTAINER_PICK_UP_HEIGHT;
		
		//if not at bottom, past top, or past bottom, or manual override pressed, dont move it
		if (!FishyRobot2015.containerState || !FishyRobot2015.chainLiftSubsystem.isAtBase || FishyRobot2015.chainLiftSubsystem.isPastTop && upOrDown || FishyRobot2015.chainLiftSubsystem.isPastBottom && !upOrDown){
			heightChangeReference = 0;
		}
		liftPID =  FishyRobot2015.chainLiftSubsystem.getPIDController();
		if (upOrDown) {
			 FishyRobot2015.chainLiftSubsystem.setpointHeight += heightChangeReference;
		} else {
			 FishyRobot2015.chainLiftSubsystem.setpointHeight -= heightChangeReference;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		liftPID.enable();
		//SmartDashboard.putString("key", "we are enabled");
		liftPID.setSetpoint(FishyRobot2015.chainLiftSubsystem.setpointHeight +  FishyRobot2015.chainLiftSubsystem.offsetHeight);
		SmartDashboard.putNumber("Goal", FishyRobot2015.chainLiftSubsystem.setpointHeight + FishyRobot2015.chainLiftSubsystem.offsetHeight);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return liftPID.onTarget() ||  FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed() && upOrDown ||  (FishyRobot2015.chainLiftSubsystem.isResetLimitPressed() && !upOrDown) || FishyRobot2015.oi.driver.isManualOverride();
	}

	// Called once after isFinished returns true
	protected void end() {
		if (upOrDown){
			//not at base if going up
			if (heightChangeReference != 0){
				FishyRobot2015.chainLiftSubsystem.isAtBase = false;
			}
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
		liftPID.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
		
	}
}
