package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ResetEncoders;
import org.usfirst.frc.team649.robot.commands.lift.RunTilResetLimit;
import org.usfirst.frc.team649.robot.subsystems.ChainLiftSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakeStarboardSubsystem;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem.EncoderBasedDriving;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class AutoWithContainerPickUp extends CommandGroup {
	
	public AutoWithContainerPickUp(){
		//facing robot
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
		addSequential(new RunTilResetLimit());
		addSequential(new ResetEncoders());
		
		//slow forward drive
		DriveSetDistanceWithPID driveCommand = new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_CONTAINER);
		driveCommand.pid.setOutputRange(0.05, 0.20); //TODO TUNE SPEED
		
		addSequential(driveCommand);
		//raise up two
		addSequential(new ChangeLiftHeight(2 * ChainLiftSubsystem.PIDConstants.TOTE_PICK_UP_HEIGHT));
		//grab it
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED, IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new WaitCommand(0.3));
		addSequential(new RunRollers(0,0));
		
		driveCommand = new DriveSetDistanceWithPID(-EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE); //BACKWARDS
		driveCommand.pid.setOutputRange(-0.1, -0.4); //TODO TUNE SPEED
		addSequential(driveCommand);
		
	}
}
