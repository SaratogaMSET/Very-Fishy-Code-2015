
package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ChangeLiftHeight extends Command {

//	PIDController liftPID;
	double deltaHeight;
	boolean up;
	double speed;
	
    public ChangeLiftHeight(double height) {
    	//requires(FishyRobot2015.chainLiftSubsystem);
//    	liftPID =  FishyRobot2015.chainLiftSubsystem.getPIDController();
    	deltaHeight = height;
    	up = deltaHeight > 0 ? true : false;
    //	FishyRobot2015.chainLiftSubsystem.setSetPointHeight(deltaHeight);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	liftPID.enable();
//    	liftPID.setSetpoint(FishyRobot2015.chainLiftSubsystem.setpointHeight);
    	SmartDashboard.putNumber("CHANGED HEIGHT", FishyRobot2015.chainLiftSubsystem.setpointHeight);
    	SmartDashboard.putNumber("DELTA", deltaHeight);
    	FishyRobot2015.chainLiftSubsystem.setpointHeight += deltaHeight;
    	speed = up ? PIDConstants.CONSTANT_POWER_UP_VALUE : PIDConstants.CONSTANT_POWER_DOWN_VALUE;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putString("ACTIVE COMMAND", "changeLiftHeight: " + deltaHeight + ", Setpoint: " + FishyRobot2015.chainLiftSubsystem.setpointHeight);
    	FishyRobot2015.chainLiftSubsystem.setPowerSafe(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean done = up ? FishyRobot2015.chainLiftSubsystem.getHeight() >= FishyRobot2015.chainLiftSubsystem.setpointHeight : FishyRobot2015.chainLiftSubsystem.getHeight() <= FishyRobot2015.chainLiftSubsystem.setpointHeight;
		return done || (up && (FishyRobot2015.chainLiftSubsystem.isMaxLimitPressed() ||  FishyRobot2015.chainLiftSubsystem.isPastTop)) || FishyRobot2015.oi.driver.isManualOverride() || (!up && (FishyRobot2015.chainLiftSubsystem.isResetLimitPressed() ||  FishyRobot2015.chainLiftSubsystem.isPastBottom));
    }

    // Called once after isFinished returns true
    protected void end() {
    	FishyRobot2015.chainLiftSubsystem.isAtBase = false;
    	FishyRobot2015.chainLiftSubsystem.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	FishyRobot2015.chainLiftSubsystem.setPower(0);
    }
}
