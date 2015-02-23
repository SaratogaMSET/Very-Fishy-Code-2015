package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap.CONTAINER_GRABBER;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.HalEffectCompensationOffset;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreAllAndResetFromTop extends CommandGroup {

	public ScoreAllAndResetFromTop(){
		//up first and then down to score all
		if (FishyRobot2015.chainLiftSubsystem.setpointHeight <= PIDConstants.MIN_HAL_EFFECT_COMPENSATION_HEIGHT){
			addSequential(new HalEffectCompensationOffset(true));
		}
		
		//down a bit, then wait for driver to back out and press trigger again
		
		//2 inches for clearance
		if (FishyRobot2015.chainLiftSubsystem.setpointHeight >= 2 + Math.abs(PIDConstants.CONTAINER_RELEASE_HEIGHT)){
			addSequential(new ChangeLiftHeight(PIDConstants.CONTAINER_RELEASE_HEIGHT));
		}
		
		while (!FishyRobot2015.oi.operator.scoreAllButton.get()){
			//wait til score all is pressed again, until we have drive back, be careful with this
		}
		
		if (!FishyRobot2015.chainLiftSubsystem.firstStageOfScore){ //TODO DEBUG
			addSequential(new RunTilResetLimit());
		
			//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
			addSequential(new ResetEncoders());
			//^^automatically makes the firstStageOfScore variable true again
		}
	}
}
