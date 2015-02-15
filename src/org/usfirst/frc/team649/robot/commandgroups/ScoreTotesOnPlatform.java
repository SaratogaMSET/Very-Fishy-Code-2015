package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.grabbercommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.commands.lift.ChangeOffsetHeight;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreTotesOnPlatform extends CommandGroup {
    
    public  ScoreTotesOnPlatform() {
    	//change to platform offset if not already there
    	if (!FishyRobot2015.chainLiftSubsystem.platformOrStepOffset){
    		addSequential(new ChangeOffsetHeight(ChainLiftSubsystem.PIDConstants.PLATFORM_HEIGHT)); //automatically updates the internal state variable
    	}
    	addParallel(new SetIntakeArmPosition(IntakeLeftSubsystem.PIDConstants.RELEASING_STATE));
    	addSequential(new ScoreAllAndResetFromTop());
    }
}
