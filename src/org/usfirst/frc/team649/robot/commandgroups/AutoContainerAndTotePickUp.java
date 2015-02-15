package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.containergrabbercommands.ClampContainerGrabber;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.lift.FinishRaiseTote;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.GyroBasedDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoContainerAndTotePickUp extends CommandGroup {
    
	 //part of full sequence
    //pick up container and tote only, separation need bc of parallel v sequential issues...fix this TODO
    public AutoContainerAndTotePickUp() {
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_CONTAINER));
    	addSequential(new WaitCommand(.3));
    	addSequential(new FullContainerAndFirstToteSequence(true));
    	//go forward and pick up the tote
 
    	//turn and go to the zone
    	addSequential(new WaitCommand(.4));
    	addSequential(new TurnWithPIDCommand(GyroBasedDriving.AUTO_GRYO_TURN_ANGLE));
    	addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_CONTAINER_TO_AUTO_ZONE));
    	//drop the tote, but keep holding container up
    	//Say what? We have the Tote at this point?
    	addSequential(new FinishRaiseTote(ChainLiftSubsystem.PIDConstants.DOWN)); //check this TODO
    }
}
