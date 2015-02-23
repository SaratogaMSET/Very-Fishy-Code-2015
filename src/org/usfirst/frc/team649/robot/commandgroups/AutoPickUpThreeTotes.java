package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.GyroBasedDriving;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPickUpThreeTotes extends CommandGroup {

	public AutoPickUpThreeTotes(){ //TODO determine direction of turn in constructor based on boolean input
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_TOTE));
		addSequential(new PickUpToteSequence());
		
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES));
		addSequential(new PickUpToteSequence());
		
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES));
		addSequential(new PickUpToteSequence());
		
		addSequential(new TurnWithPIDCommand(GyroBasedDriving.AUTO_GRYO_TURN_ANGLE)); 
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE));
	}

}
