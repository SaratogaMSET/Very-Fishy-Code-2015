package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.HalEffectCompensationOffset;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreAllAndResetFromTop extends CommandGroup {

	public ScoreAllAndResetFromTop(){
		//up first and then down to score all
		addSequential(new HalEffectCompensationOffset(true));
		addSequential(new RunTilResetLimit());
		//addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.ENCODER_RESET_OFFSET));
		addSequential(new ResetEncoders());
	}
}
