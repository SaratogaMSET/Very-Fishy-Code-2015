package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

//added to the beginnings of all autonomous commands and NOT at the beginning of teleOp, thats manual
public class BeginningMatchPresets extends CommandGroup {
	public BeginningMatchPresets(int numTotes){
    	addSequential(new ScoreAllAndResetFromTop(numTotes));
    	addSequential(new SetIntakeArmPositionWithPID(PIDConstants.RELEASING_STATE));
	}
}
