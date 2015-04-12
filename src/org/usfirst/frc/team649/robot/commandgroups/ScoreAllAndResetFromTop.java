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

	public ScoreAllAndResetFromTop(int numTotes){
		//COMMONALITIES
		
		//up first and then down to score all
		if (FishyRobot2015.chainLiftSubsystem.setpointHeight <= PIDConstants.MIN_HAL_EFFECT_COMPENSATION_HEIGHT){
			addSequential(new HalEffectCompensationOffset(true));
		}
		//down a bit, then wait for driver to back out and press trigger again
		
		//2 inches for clearance
		if (FishyRobot2015.chainLiftSubsystem.setpointHeight >= 2 + Math.abs(PIDConstants.VARIABLE_TOTE_SPACE_INCREMENT)){
			//double chainOffset = ;//(FishyRobot2015.chainLiftSubsystem.getNumTotes()) * PIDConstants.VARIABLE_TOTE_SPACE_INCREMENT + PIDConstants.CONTAINER_RELEASE_HEIGHT;
			double deltaHeight = numTotes * PIDConstants.VARIABLE_TOTE_SPACE_INCREMENT + 1.8;
			if(numTotes >= 5) {
				deltaHeight -=  15.0;
			}
			addSequential(new ChangeLiftHeight(deltaHeight)); //9.5 for right to the top of the 2nd hook tote
			
		}
		
		//DRIVER BACKS OUT, MANUAL OVERRIDE IF NOT GOOD
		addSequential(new WaitTilButtonPressed(FishyRobot2015.oi.operator.scoreAllButton, FishyRobot2015.oi.operator.scoreAllSafteyButton));
		
		addSequential(new RunTilResetLimit());
		//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
		addSequential(new ResetEncoders());
		//^^automatically makes the firstStageOfScore variable true again
		
		addSequential(new ChangeLiftHeight(PIDConstants.CONTAINER_RESET_OFFSET));
		
	}
}
