package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreTotesOnPlatform extends CommandGroup {
    
    public  ScoreTotesOnPlatform() {
    	addParallel(new SetIntakeArmPosition(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
    	addSequential(new ScoreAllAndResetFromTop());
    }
}
