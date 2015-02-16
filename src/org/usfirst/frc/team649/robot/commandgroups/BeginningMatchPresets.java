package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.containergrabbercommands.ClampContainerGrabber;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPosition;
import org.usfirst.frc.team649.robot.subsystems.IntakeLeftSubsystem.PIDConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

//added to the beginnings of all autonomous commands and NOT at the beginning of teleOp, thats manual
public class BeginningMatchPresets extends CommandGroup {
	public BeginningMatchPresets(){
    	addSequential(new ClampContainerGrabber(false));
    	addSequential(new ScoreAllAndResetFromTop());
    	addSequential(new SetIntakeArmPosition(PIDConstants.RELEASING_STATE));
	}
}
