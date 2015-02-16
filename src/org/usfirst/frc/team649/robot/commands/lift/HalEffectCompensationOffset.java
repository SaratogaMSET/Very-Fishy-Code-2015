package org.usfirst.frc.team649.robot.commands.lift;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.Command;

public class HalEffectCompensationOffset extends Command {
	boolean upOrDown;

	//with direction that needs to be traveled
	public HalEffectCompensationOffset(boolean up){
		upOrDown = up;
	}
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		
		setTimeout(PIDConstants.HAL_COMPENSATION_TIME_OUT);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		FishyRobot2015.chainLiftSubsystem.setPower(upOrDown ? 0.5 : -0.5);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return this.isTimedOut();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		FishyRobot2015.chainLiftSubsystem.setPower(0);
	}
	
	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}

}
