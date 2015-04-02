package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.FishyRobot2015;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem.PIDConstants;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedTurning;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class AutoPickUpOneTote extends CommandGroup {

	public AutoPickUpOneTote(){ //TODO determine direction of turn in constructor based on boolean input
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
		addSequential(new RunTilResetLimit());
		addSequential(new ResetEncoders());
		addSequential(new ChangeLiftHeight(PIDConstants.TOTE_PICK_UP_HEIGHT + PIDConstants.HEIGHT_OFFSET_FIRST_PICK_UP - 2));
		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_TOTE, 0.2, 0.45)); //TODO CHANGE TO PARALLEL ONCE BUMPERS ARE IN
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED, IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
		addSequential(new WaitCommand(0.6));
		addSequential(new RunRollers(0.3, -0.3));
		addSequential(new WaitCommand(0.5));
//		addSequential(new RunRollers(-0.3, -0.3));
//		addSequential(new WaitCommand(0.3));
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED, IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new WaitCommand(1));
		addSequential(new RunRollers(0, 0));
		
		addSequential(new OpenArmsAndRaiseTote(true));	
		addSequential(new TurnWithPIDCommand(-45, -0.7, -0.3));
		addSequential(new DriveSetDistanceWithPID(-EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE, -0.3, -0.1));
		//wait til intakes are done
		//addSequential(new WaitForChildren());
		
		
		
		//addSequential(new OpenArmsAndRaiseTote(true));
		//addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_BETWEEN_TOTES, 0.1, 0.5));
		//addSequential(new WaitCommand(2.0));
		
//		addSequential(new TurnWithPIDCommand(GyroBasedDriving.AUTO_GRYO_TURN_ANGLE)); 
//		addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE));
	}

}
