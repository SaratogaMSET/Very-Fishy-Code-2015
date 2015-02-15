package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.containergrabbercommands.ClampContainerGrabber;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.GyroBasedDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoContainerOnly extends CommandGroup {
    
    public  AutoContainerOnly() {
    	addSequential(new ClampContainerGrabber(false));
    	addSequential(new ScoreAllAndResetFromTop());
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_CONTAINER));
    	addSequential(new WaitCommand(.3));
    	addSequential(new PickUpContainer());
    	addSequential(new TurnWithPIDCommand(GyroBasedDriving.AUTO_GRYO_TURN_ANGLE));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_CONTAINER_TO_AUTO_ZONE));
    	//keep holding tote
    	
    }
}
