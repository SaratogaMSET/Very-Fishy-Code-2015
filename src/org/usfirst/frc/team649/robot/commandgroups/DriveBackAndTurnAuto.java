package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveBackAndTurnAuto extends CommandGroup {
    
    public  DriveBackAndTurnAuto() {
    	addSequential(new DriveSetDistanceWithPID(-63, -0.5, -0.1));
    //	addSequential(new TurnWithPIDCommand(45, 0.1));
    }
}
