package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.lift.FinishRaiseTote;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteToIntermediateLevel;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class FullLowerTote extends CommandGroup {
	public FullLowerTote(){
		addSequential(new FinishRaiseTote(false));
		addSequential(new RaiseToteToIntermediateLevel(false));
	}
}
