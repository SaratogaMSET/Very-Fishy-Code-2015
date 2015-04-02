package org.usfirst.frc.team649.robot.commandgroups;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ThreeToteAutoFull extends CommandGroup {
    
    public  ThreeToteAutoFull() {
    	addSequential(new ThreeToteAutoPart1());
  //  	addSequential(new WaitCommand(3.0));
    	addSequential(new ThreeToteAutoPart2());
    }
}
