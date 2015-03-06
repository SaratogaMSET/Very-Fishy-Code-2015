package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.RobotMap.CONTAINER_GRABBER;
import org.usfirst.frc.team649.robot.commands.WaitTilButtonPressed;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.HalEffectCompensationOffset;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreAllAndResetFromTop extends CommandGroup {

	public ScoreAllAndResetFromTop(){
		//COMMONALITIES
		
		//up first and then down to score all
		if (FishyRobot2015.chainLiftSubsystem.setpointHeight <= PIDConstants.MIN_HAL_EFFECT_COMPENSATION_HEIGHT){
			addSequential(new HalEffectCompensationOffset(true));
		}
		
		
		//IF CONTAINER MODE
		if (FishyRobot2015.containerState){
			//down a bit, then wait for driver to back out and press trigger again
			
			//2 inches for clearance
			if (FishyRobot2015.chainLiftSubsystem.setpointHeight >= 2 + Math.abs(PIDConstants.CONTAINER_RELEASE_HEIGHT)){
				double chainOffset = FishyRobot2015.chainLiftSubsystem.getNumTotes() * PIDConstants.VARIABLE_TOTE_SPACE_INCREMENT + PIDConstants.CONTAINER_RELEASE_HEIGHT;
				addSequential(new ChangeLiftHeight(chainOffset));
			}
			
			addSequential(new WaitTilButtonPressed(FishyRobot2015.oi.operator.scoreAllSafteyButton));
			addSequential(new WaitTilButtonPressed(FishyRobot2015.oi.operator.scoreAllButton));
			addSequential(new ChangeLiftHeight(PIDConstants.CONTAINER_RESET_OFFSET));
		}
		
		//COMMONALITIES
		addSequential(new RunTilResetLimit());
		//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
		addSequential(new ResetEncoders());
		//^^automatically makes the firstStageOfScore variable true again
	}
}
