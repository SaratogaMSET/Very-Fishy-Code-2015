package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoWithContainerPickUp extends CommandGroup {
	
	DriveSetDistanceWithPID driveCommand1, driveCommand2;
	
	public AutoWithContainerPickUp(){
		//facing robot
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.RELEASING_STATE));
		addSequential(new ResetLift());
		
		//slow forward drive
		//addSequential(new DriveSetDistanceWithPID(EncoderBasedDriving.AUTO_START_TO_CONTAINER, 0.1, 0.32));
		addSequential(new WaitCommand(0.25));
		//raise up two	
		addSequential(new ChangeLiftHeight(ChainLiftSubsystem.PIDConstants.TOTE_PICK_UP_HEIGHT - 1.0));

		//SmartDashboard.p
		//grab it
		addParallel(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));
		addSequential(new RunRollers(IntakePortSubsystem.INTAKE_ROLLER_SPEED, IntakeStarboardSubsystem.INTAKE_ROLLER_SPEED));
		addSequential(new WaitCommand(1));
		addSequential(new RunRollers(0,0));
		
		//drive back into autozone
//		addSequential(new TurnWithPIDCommand(45));
//		addSequential(new DriveSetDistanceWithPID(-EncoderBasedDriving.AUTO_TOTE_TO_AUTO_ZONE, -0.35, -0.1)); //BACKWARDS
		//addSequential(new OpenArmsAndRaiseTote(true))
	}
}
