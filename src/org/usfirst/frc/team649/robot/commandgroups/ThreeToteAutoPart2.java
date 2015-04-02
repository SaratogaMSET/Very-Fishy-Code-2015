package org.usfirst.frc.team649.robot.commandgroups;


import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ThreeToteAutoPart2 extends CommandGroup {
    
    public  ThreeToteAutoPart2() {
    	//pick up second tote
 //   	addSequential(new WaitCommand(3.0));
    	addSequential(new PickUpToteSequence());
	//	addParallel(new RaiseToteWithoutPID(false));
    }
}
