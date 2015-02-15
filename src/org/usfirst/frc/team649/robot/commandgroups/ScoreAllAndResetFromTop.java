package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.lift.HalEffectCompensationOffset;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreAllAndResetFromTop extends CommandGroup {

	public ScoreAllAndResetFromTop(){
		//up first and then down to score all
		addSequential(new HalEffectCompensationOffset(true));
		addSequential(new RunTilResetLimit());
	}
}
