package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.WaitTilButtonPressed;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitForChildren;

/**
 *
 */
public class TurnAndPickUpToteAuto extends CommandGroup {
    
    public  TurnAndPickUpToteAuto() {
    	addSequential(new DriveSetDistanceWithPID(24, 0.1, 0.5));
    	addParallel(new TurnWithPIDCommand(45, 0.1, 0.5));
    	addSequential(new RunRollers(-0.7, -0.7));
    	
    	addSequential(new WaitForChildren());
    	addSequential(new RunRollers(0.0, 0.0));
    	addParallel(new DriveSetDistanceWithPID(24, 0.1, 0.5));
    	addSequential(new PickUpToteSequence());
    	
    	addSequential(new WaitForChildren());
    	addSequential(new TurnWithPIDCommand(90, 0.1, 0.5));
    	addSequential(new DriveSetDistanceWithPID(74, 0.1, 0.5));
    }
}
