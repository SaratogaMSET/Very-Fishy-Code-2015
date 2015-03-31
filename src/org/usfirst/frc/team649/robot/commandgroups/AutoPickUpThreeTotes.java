package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedTurning;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class AutoPickUpThreeTotes extends CommandGroup {

	public AutoPickUpThreeTotes(){ //TODO determine direction of turn in constructor based on boolean input
		
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_TOTE, 0.2, 0.45)); //TODO CHANGE TO PARALLEL ONCE BUMPERS ARE IN
		addSequential(new PickUpToteSequence());
		
		//wait til intakes are done
		addSequential(new WaitForChildren());
		
		
		//addSequential(new OpenArmsAndRaiseTote(true));
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES, 0.1, 0.5));
		//addSequential(new WaitCommand(2.0));
		
//		addSequential(new TurnWithPIDCommand(GyroBasedDriving.AUTO_GRYO_TURN_ANGLE)); 
//		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE));
	}

}
