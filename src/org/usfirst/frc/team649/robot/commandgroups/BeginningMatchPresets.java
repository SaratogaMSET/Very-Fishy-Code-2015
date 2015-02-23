package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

//added to the beginnings of all autonomous commands and NOT at the beginning of teleOp, thats manual
public class BeginningMatchPresets extends CommandGroup {
	public BeginningMatchPresets(){
    	addSequential(new ScoreAllAndResetFromTop());
    	addSequential(new SetIntakeArmPosition(PIDConstants.RELEASING_STATE));
	}
}
