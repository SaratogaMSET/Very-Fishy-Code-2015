package org.usfirst.frc.team649.robot.commandgroups;

import org.usfirst.frc.team649.robot.commands.drivetraincommands.DriveSetDistanceWithPID;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnSetTimeCommand;
import org.usfirst.frc.team649.robot.commands.drivetraincommands.TurnWithPIDCommand;
import org.usfirst.frc.team649.robot.commands.intakecommands.RunRollers;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithPID;
import org.usfirst.frc.team649.robot.commands.intakecommands.SetIntakeArmPositionWithoutPID;
import org.usfirst.frc.team649.robot.commands.lift.ChangeLiftHeight;
import org.usfirst.frc.team649.robot.commands.lift.RaiseToteWithoutPID;
import org.usfirst.frc.team649.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team649.robot.subsystems.IntakePortSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ContainerAndToteAuto extends CommandGroup {
    
    public  ContainerAndToteAuto() {
       	//Pick Up first tote
    	System.out.println("");
    	addSequential(new SetIntakeArmPositionWithoutPID(IntakePortSubsystem.PIDConstants.GRABBING_STATE));    	//addParallel(new DriveSetDistanceWithPID(DrivetrainSubsystem.EncoderBasedDriving.AUTO_START_TO_TOTE, 0.1));
    	addSequential(new PickUpToteSequence());
    	
    	
    	//Get contarior out of the way and move 
    	addSequential(new RaiseToteWithoutPID(true));
    	addSequential(new RunRollers(1.0, 1.0));
    	addSequential(new DriveSetDistanceWithPID(DrivetrainSubsystem.EncoderBasedDriving.AUTO_BETWEEN_TOTES, 0.1, 0.5));
    	addSequential(new WaitCommand(1.8));
    
    	addSequential(new RunRollers(0.0, 0.0));
 
    	//turn
    	addSequential(new TurnSetTimeCommand(1, 0.0, 0.3));
    	addSequential(new DriveSetDistanceWithPID(32, 0.1, 0.5));

    }
}
