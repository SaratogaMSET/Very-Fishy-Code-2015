package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveForwardRotate;
import org.usfirst.frc.team649.robot.commands.lift.RunLift;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Debug extends CommandGroup {
    
	//TODO: Needs to work and shit... also actually run all the motors also print sensors
    public  Debug() {
    	//for raw motor
    	addSequential(new DriveForwardRotate(0.4, 0));
    	addSequential(new WaitCommand(2));
    	addSequential(new DriveForwardRotate(0,0));
    	
    	//for chain
    	addSequential(new RunLift(0.5));
    	addSequential(new WaitCommand(2));
    	addSequential(new RunLift(0));
    }
}
