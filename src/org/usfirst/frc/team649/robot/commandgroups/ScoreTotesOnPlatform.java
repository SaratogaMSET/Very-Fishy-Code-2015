package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPIDThreeState;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreTotesOnPlatform extends CommandGroup {
    
    public  ScoreTotesOnPlatform(int numTotes) {
    	addSequential(new SetIntakeArmPositionWithoutPIDThreeState(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
//    	addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
    	addSequential(new ScoreAllAndResetFromTop(numTotes));
   // 	addSequential(new ResetLift());
    }
}
